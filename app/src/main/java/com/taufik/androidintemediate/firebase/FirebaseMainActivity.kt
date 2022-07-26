package com.taufik.androidintemediate.firebase

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.databinding.ActivityFirebaseMainBinding

class FirebaseMainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFirebaseMainBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initAuth()
    }

    private fun initAuth() {
        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        if (firebaseUser == null) {
            startActivity(Intent(this, FirebaseLoginActivity::class.java))
            finish()
            return
        }
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(this, FirebaseLoginActivity::class.java))
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.firebase_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sign_out_menu -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}