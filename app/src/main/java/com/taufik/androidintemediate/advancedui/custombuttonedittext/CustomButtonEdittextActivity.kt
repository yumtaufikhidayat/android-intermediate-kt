package com.taufik.androidintemediate.advancedui.custombuttonedittext

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.taufik.androidintemediate.databinding.ActivityCustomButtonEdittextBinding

class CustomButtonEdittextActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomButtonEdittextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomButtonEdittextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setMyButtonEnabled()
        setChangedListener()
    }

    private fun setMyButtonEnabled() = with(binding) {
        val result = myEditText.text
        myButton.isEnabled = result != null && result.toString().isNotEmpty()
    }

    private fun setChangedListener() = with(binding) {
        myEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                setMyButtonEnabled()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        myButton.setOnClickListener { Toast.makeText(this@CustomButtonEdittextActivity, myEditText.text, Toast.LENGTH_SHORT).show() }
    }
}