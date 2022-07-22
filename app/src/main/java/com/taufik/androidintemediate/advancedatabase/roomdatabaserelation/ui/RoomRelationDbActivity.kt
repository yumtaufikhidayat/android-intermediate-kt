package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.MyApplication
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ViewModelFactory
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui.adapter.StudentAndUniversityAdapter
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui.adapter.StudentListAdapter
import com.taufik.androidintemediate.databinding.ActivityRoomRelationDbBinding

class RoomRelationDbActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRoomRelationDbBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory((application as MyApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getStudent()
    }

    private fun getStudent() {
        val studentListAdapter = StudentListAdapter()
        binding.apply {
            with(rvStudent) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@RoomRelationDbActivity)
                adapter = studentListAdapter
            }
        }
        mainViewModel.getAllStudent().observe(this) {
            studentListAdapter.submitList(it)
        }
    }

    private fun getStudentAndUniversity() {
        val studentAndUniversityAdapter = StudentAndUniversityAdapter()
        binding.apply {
            with(rvStudent) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@RoomRelationDbActivity)
                adapter = studentAndUniversityAdapter
            }
        }
        mainViewModel.getAllStudentAndUniversity().observe(this) {
            studentAndUniversityAdapter.submitList(it)
            Log.i(TAG, "getStudentAndUniversity: $it")
        }
    }

    private fun getUniversityAndStudent() {

    }


    private fun getStudentWithCourse() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_single_table -> {
                getStudent()
                true
            }

            R.id.action_many_to_one -> {
                getStudentAndUniversity()
                true
            }

            R.id.action_one_to_many -> {
                getUniversityAndStudent()
                true
            }

            R.id.action_many_to_many -> {
                getStudentWithCourse()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val TAG = "RoomRelationDbActivity"
    }
}