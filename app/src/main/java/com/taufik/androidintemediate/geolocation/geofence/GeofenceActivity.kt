package com.taufik.androidintemediate.geolocation.geofence

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.taufik.androidintemediate.databinding.ActivityGeofenceBinding

class GeofenceActivity : AppCompatActivity(), OnMapReadyCallback {

    private val binding by lazy {
        ActivityGeofenceBinding.inflate(layoutInflater)
    }

    private val centerLat = 3.572138
    private val centerLng = 98.705923
    private val geofenceRadius = 400.0
    private val runningOnQOrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    private lateinit var mMap: GoogleMap
    private lateinit var geofencingClient: GeofencingClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true

        val jalanKolam = LatLng(centerLat, centerLng)
        mMap.addMarker(MarkerOptions().position(jalanKolam).title("Jalan Kolam"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jalanKolam, 15f))
        mMap.addCircle(
            CircleOptions()
                .center(jalanKolam)
                .radius(geofenceRadius)
                .fillColor(0x22FF0000)
                .strokeColor(Color.RED)
                .strokeWidth(3f)
        )

        getMyLocation()
        addGeofence()
    }

    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        if (checkForegroundAndBackgroundLocationPermission()) {
            mMap.isMyLocationEnabled = true
        } else {
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val requestBackgroundLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getMyLocation()
            }
        }

    @TargetApi(Build.VERSION_CODES.Q)
    private val requestLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                if (runningOnQOrLater) {
                    requestBackgroundLocationPermission.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                } else {
                    getMyLocation()
                }
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    @TargetApi(Build.VERSION_CODES.Q)
    private fun checkForegroundAndBackgroundLocationPermission(): Boolean {
        val foregroundLocationApproved = checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        val backgroundPermissionApproved = if (runningOnQOrLater) {
            checkPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        } else {
            true
        }

        return foregroundLocationApproved && backgroundPermissionApproved
    }

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        intent.action = GeofenceBroadcastReceiver.ACTION_GEOFENCE_EVENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    @SuppressLint("MissingPermission")
    private fun addGeofence() {
        geofencingClient = LocationServices.getGeofencingClient(this)
        val geofence = Geofence.Builder()
            .setRequestId("jalan kolam")
            .setCircularRegion(
                centerLat,
                centerLng,
                geofenceRadius.toFloat()
            )
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_ENTER)
            .setLoiteringDelay(5000)
            .build()

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        geofencingClient.removeGeofences(geofencePendingIntent).run {
            addOnCompleteListener {
                geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent).run {
                    addOnSuccessListener {
                        showToast("Geofencing added")
                    }

                    addOnFailureListener {
                        showToast("Geofencing not added : ${it.message}")
                    }
                }
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}