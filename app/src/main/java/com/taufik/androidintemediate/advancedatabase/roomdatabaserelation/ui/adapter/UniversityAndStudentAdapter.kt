package com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.taufik.androidintemediate.advancedatabase.roomdatabaserelation.database.entity.UniversityAndStudent
import com.taufik.androidintemediate.databinding.ItemStudentBinding

class UniversityAndStudentAdapter: ListAdapter<UniversityAndStudent, UniversityAndStudentAdapter.UniversityAndStudentViewHolder>(StudentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityAndStudentViewHolder {
        return UniversityAndStudentViewHolder(ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UniversityAndStudentViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class UniversityAndStudentViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: UniversityAndStudent) = with(binding) {
            val arrayName = arrayListOf<String>()
            data.student.forEach {
                arrayName.add(it.name)
            }
            tvItemName.text = arrayName.joinToString(separator = ", ")
            tvItemUniversity.text = data.university.name
            tvItemUniversity.visibility = View.VISIBLE
        }
    }

    class StudentDiffCallback: DiffUtil.ItemCallback<UniversityAndStudent>() {
        override fun areItemsTheSame(oldItem: UniversityAndStudent, newItem: UniversityAndStudent): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: UniversityAndStudent, newItem: UniversityAndStudent): Boolean = oldItem.university.name == newItem.university.name
    }
}