package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.MyApplication
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ViewModelFactory
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.helper.SortType
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui.adapter.StudentAndUniversityAdapter
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui.adapter.StudentListAdapter
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui.adapter.StudentWithCourseAdapter
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui.adapter.UniversityAndStudentAdapter
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
        val universityAndStudentAdapter = UniversityAndStudentAdapter()
        binding.apply {
            with(rvStudent) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@RoomRelationDbActivity)
                adapter = universityAndStudentAdapter
            }
        }
        mainViewModel.getAllUniversityAndStudent().observe(this) {
            Log.i(TAG, "getUniversityAndStudent: $it")
            universityAndStudentAdapter.submitList(it)
        }
    }

    private fun getStudentWithCourse() {
        val studentWithCourseAdapter = StudentWithCourseAdapter()
        binding.apply {
            with(rvStudent) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@RoomRelationDbActivity)
                adapter = studentWithCourseAdapter
            }
        }
        mainViewModel.getAllStudentWithCourse().observe(this) {
            Log.i(TAG, "getStudentWithCourse: $it")
            studentWithCourseAdapter.submitList(it)
        }
    }

    private fun showSortingOptionMenu(isShow: Boolean) {
        val view = findViewById<View>(R.id.action_sort) ?: return
        view.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showSortingPopupMenu() {
        val view = findViewById<View>(R.id.action_sort) ?: return
        PopupMenu(this, view).run {
            menuInflater.inflate(R.menu.sorting_menu, menu)

            setOnMenuItemClickListener {
                mainViewModel.changeSortType(
                    when (it.itemId) {
                        R.id.action_ascending -> SortType.ASCENDING
                        R.id.action_descending -> SortType.DESCENDING
                        else -> SortType.RANDOM
                    }
                )
                true
            }
            show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_single_table -> {
                getStudent()
                showSortingOptionMenu(true)
                true
            }

            R.id.action_many_to_one -> {
                getStudentAndUniversity()
                showSortingOptionMenu(false)
                true
            }

            R.id.action_one_to_many -> {
                getUniversityAndStudent()
                showSortingOptionMenu(false)
                true
            }

            R.id.action_many_to_many -> {
                getStudentWithCourse()
                showSortingOptionMenu(false)
                true
            }

            R.id.action_sort -> {
                showSortingPopupMenu()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val TAG = "RoomRelationDbActivity"
    }
}