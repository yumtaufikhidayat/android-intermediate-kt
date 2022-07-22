package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentWithCourse
import com.taufik.androidintemediate.databinding.ItemStudentBinding

class StudentWithCourseAdapter: ListAdapter<StudentWithCourse, StudentWithCourseAdapter.StudentWithCourseViewHolder>(StudentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentWithCourseViewHolder {
        return StudentWithCourseViewHolder(ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StudentWithCourseViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class StudentWithCourseViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: StudentWithCourse) = with(binding) {
            tvItemUniversity.text = data.studentAndUniversity.university?.name
            tvItemUniversity.visibility = View.VISIBLE
            tvItemName.text = data.studentAndUniversity.student.name
            val arrayCourse = arrayListOf<String>()
            data.course.forEach {
                arrayCourse.add(it.name)
            }
            tvItemCourse.text = arrayCourse.joinToString(", ")
            tvItemCourse.visibility = View.VISIBLE
        }
    }

    class StudentDiffCallback: DiffUtil.ItemCallback<StudentWithCourse>() {
        override fun areItemsTheSame(oldItem: StudentWithCourse, newItem: StudentWithCourse): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: StudentWithCourse, newItem: StudentWithCourse): Boolean = oldItem.studentAndUniversity.student.name == newItem.studentAndUniversity.student.name
    }
}