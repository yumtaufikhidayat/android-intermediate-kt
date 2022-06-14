package com.taufik.androidintemediate.animation.activitytransition.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taufik.androidintemediate.animation.activitytransition.model.Hero
import com.taufik.androidintemediate.animation.activitytransition.view.DetailHeroActivity.Companion.EXTRA_HERO
import com.taufik.androidintemediate.databinding.ItemRowHeroBinding

class ListHeroAdapter: ListAdapter<Hero, ListHeroAdapter.ListHeroViewHolder>(ListHeroDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHeroViewHolder {
        return ListHeroViewHolder(ItemRowHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ListHeroViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class ListHeroViewHolder(private val binding: ItemRowHeroBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: Hero) = with(binding) {
            Glide.with(itemView.context)
                .load(item.photo)
                .circleCrop()
                .into(imgProfile)

            tvName.text = item.name
            tvDesc.text = item.description

            itemView.setOnClickListener {
                it.context.startActivity(Intent(itemView.context, DetailHeroActivity::class.java).apply {
                    putExtra(EXTRA_HERO, item)
                })
            }
        }
    }

    object ListHeroDiffCallback: DiffUtil.ItemCallback<Hero>(){
        override fun areItemsTheSame(oldItem: Hero, newItem: Hero): Boolean = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: Hero, newItem: Hero): Boolean = oldItem == newItem
    }
}