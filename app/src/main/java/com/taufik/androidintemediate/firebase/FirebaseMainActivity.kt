package com.taufik.androidintemediate.firebase

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.databinding.ActivityFirebaseMainBinding
import java.util.*

class FirebaseMainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFirebaseMainBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var messageAdapter: FirebaseMessageAdapter

    private lateinit var messagesRef: DatabaseReference
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initAuth()
        initDatabase()
        setChat()
    }

    private fun initAuth() {
        auth = Firebase.auth
        firebaseUser = auth.currentUser

        if (firebaseUser == null) {
            startActivity(Intent(this, FirebaseLoginActivity::class.java))
            finish()
            return
        }
    }

    private fun initDatabase() = with(binding) {
        db = FirebaseDatabase.getInstance()

        messagesRef = db.reference.child(MESSAGES_CHILD)

        btnSend.setOnClickListener {
            val friendlyMessage = Message(
                etMessage.text.toString(),
                firebaseUser?.displayName.toString(),
                firebaseUser?.photoUrl.toString(),
                Date().time
            )
            messagesRef.push().setValue(friendlyMessage) { error, _ ->
                if (error != null) {
                    Toast.makeText(
                        this@FirebaseMainActivity,
                        getString(R.string.send_error) + error.message,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@FirebaseMainActivity,
                        getString(R.string.send_success),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            etMessage.setText("")
        }
    }

    private fun setChat() = with(binding) {
        val manager = LinearLayoutManager(this@FirebaseMainActivity)
        manager.stackFromEnd = true
        rvMessage.layoutManager = manager

        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messagesRef, Message::class.java)
            .build()

        messageAdapter = FirebaseMessageAdapter(options, firebaseUser?.displayName)
        rvMessage.adapter = messageAdapter
    }

    override fun onResume() {
        super.onResume()
        messageAdapter.startListening()
    }

    override fun onPause() {
        messageAdapter.stopListening()
        super.onPause()
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

    companion object {
        const val MESSAGES_CHILD = "messages"
    }
}