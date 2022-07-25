package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.StudentEntity
import com.taufik.androidintemediate.databinding.ItemStudentBinding

class StudentListAdapter: PagedListAdapter<StudentEntity, StudentListAdapter.StudentViewHolder>(StudentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.onBind(getItem(position) as StudentEntity)
    }

    class StudentViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: StudentEntity) {
            binding.tvItemName.text = data.name
        }
    }

    class StudentDiffCallback: DiffUtil.ItemCallback<StudentEntity>() {
        override fun areItemsTheSame(oldItem: StudentEntity, newItem: StudentEntity): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: StudentEntity, newItem: StudentEntity): Boolean = oldItem.name == newItem.name
    }
}