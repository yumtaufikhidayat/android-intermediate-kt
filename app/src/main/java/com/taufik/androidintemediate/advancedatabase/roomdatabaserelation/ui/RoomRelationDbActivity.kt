package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.MyApplication
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ViewModelFactory
import com.taufik.androidintemediate.databinding.ActivityRoomRelationDbBinding

class RoomRelationDbActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRoomRelationDbBinding.inflate(layoutInflater)
    }

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory((application as MyApplication).repository)
    }

    private lateinit var studentListAdapter: StudentListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initAdapter()
        getStudent()
    }

    private fun initAdapter() {
        studentListAdapter = StudentListAdapter()
        binding.apply {
            with(rvStudent) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@RoomRelationDbActivity)
                adapter = studentListAdapter
            }
        }
    }

    private fun getStudent() {
        mainViewModel.getAllStudent().observe(this) {
            studentListAdapter.submitList(it)
        }
    }

    private fun getStudentAndUniversity() {

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
}