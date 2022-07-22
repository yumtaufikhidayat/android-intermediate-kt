package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentAndUniversity
import com.taufik.androidintemediate.databinding.ItemStudentBinding

class StudentAndUniversityAdapter: ListAdapter<StudentAndUniversity, StudentAndUniversityAdapter.StudentAndUniversityViewHolder>(StudentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAndUniversityViewHolder {
        return StudentAndUniversityViewHolder(ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StudentAndUniversityViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class StudentAndUniversityViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: StudentAndUniversity) = with(binding) {
            tvItemName.text = data.student.name
            tvItemUniversity.text = data.university?.name
            tvItemUniversity.visibility = View.VISIBLE
        }
    }

    class StudentDiffCallback: DiffUtil.ItemCallback<StudentAndUniversity>() {
        override fun areItemsTheSame(oldItem: StudentAndUniversity, newItem: StudentAndUniversity): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: StudentAndUniversity, newItem: StudentAndUniversity): Boolean = oldItem.student.name == newItem.student.name
    }
}