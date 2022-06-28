package com.taufik.androidintemediate.media.cameraxgalleryupload

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.taufik.androidintemediate.databinding.ActivityMainCameraXBinding
import com.taufik.androidintemediate.media.cameraxgalleryupload.utils.createCustomTempFile
import com.taufik.androidintemediate.media.cameraxgalleryupload.utils.reduceFileImage
import com.taufik.androidintemediate.media.cameraxgalleryupload.utils.rotateBitmap
import com.taufik.androidintemediate.media.cameraxgalleryupload.utils.uriToFile
import com.taufik.androidintemediate.media.cameraxgalleryupload.viewmodel.CameraXViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MainCameraXActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainCameraXBinding.inflate(layoutInflater)
    }
    
    private val viewModel: CameraXViewModel by viewModels()

    private lateinit var currentPhotoPath: String
    private var getFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        checkPermissions()
        setAction()
        initObserver()
    }

    private fun checkPermissions() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun setAction() = with(binding) {
        btnCameraX.setOnClickListener { startCameraX() }
        btnCamera.setOnClickListener { startTakePhoto() }
        btnGallery.setOnClickListener { startGallery() }
        btnUpload.setOnClickListener { uploadImage() }
    }

    private fun initObserver() {
        viewModel.isUploadSuccess.observe(this) {
            showToast(it)
        }
    }

    private fun showToast(state: Boolean) {
        if (state) {
            Toast.makeText(this, "Berhasil upload", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Gagal instance retrofit", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@MainCameraXActivity,
                "com.taufik.androidintemediate.media.cameraxgallery",
                it
            )

            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent().apply {
            action = ACTION_GET_CONTENT
            type = "image/*"
        }

        val chooser = Intent.createChooser(intent, "Pilih gambar")
        launcherIntentGallery.launch(chooser)
    }

    private fun uploadImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)
            val description = "Ini adalah deskripsi gambar".toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            viewModel.apply {
                uploadImage(imageMultipart, description)
                uploadImage.observe(this@MainCameraXActivity) {
                    Toast.makeText(this@MainCameraXActivity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Silakan masukkan berkas gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        with(binding) {
            if (it.resultCode == CAMERA_X_RESULT) {
                val myFile = it.data?.getSerializableExtra(EXTRA_PICTURE) as File
                val isBackCamera = it.data?.getBooleanExtra(EXTRA_BACK_CAMERA, true) as Boolean
                getFile = myFile

                val result = rotateBitmap(
                    BitmapFactory.decodeFile(myFile.path),
                    isBackCamera
                )

                imgPreview.setImageBitmap(result)
            }
        }
    }

    private val launcherIntentCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        with(binding) {
            if (it.resultCode == RESULT_OK) {
                val myFile = File(currentPhotoPath)
                getFile = myFile

                val result = BitmapFactory.decodeFile(getFile?.path)
                imgPreview.setImageBitmap(result)
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        with(binding) {
            if (it.resultCode == RESULT_OK) {
                val selectedImage: Uri = it.data?.data as Uri
                val myFile = uriToFile(selectedImage, this@MainCameraXActivity)
                getFile = myFile
                imgPreview.setImageURI(selectedImage)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        const val EXTRA_PICTURE = "com.taufik.androidintemediate.media.camerax.EXTRA_PICTURE"
        const val EXTRA_BACK_CAMERA = "com.taufik.androidintemediate.media.camerax.EXTRA_BACK_CAMERA"

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}