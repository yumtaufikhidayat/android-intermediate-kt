package com.taufik.androidintemediate.advancedtesting.ui.list

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.advancedtesting.data.local.entity.NewsEntity
import com.taufik.androidintemediate.advancedtesting.ui.detail.NewsDetailActivity
import com.taufik.androidintemediate.databinding.ItemNewsBinding

class NewsAdapter: ListAdapter<NewsEntity, NewsAdapter.NewsViewHolder>(newsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(news: NewsEntity) = with(binding) {
            tvItemTitle.text = news.title
            tvItemPublishedDate.text = news.publishedAt
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .apply {
                    RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error)
                }
                .into(imgPoster)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NewsDetailActivity::class.java).apply {
                    putExtra(NewsDetailActivity.NEWS_DATA, news)
                }
                it.context.startActivity(intent)
            }
        }
    }

    companion object {
        val newsDiffCallback: DiffUtil.ItemCallback<NewsEntity> =
            object : DiffUtil.ItemCallback<NewsEntity>() {
                override fun areItemsTheSame(oldUser: NewsEntity, newUser: NewsEntity): Boolean {
                    return oldUser.title == newUser.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldUser: NewsEntity, newUser: NewsEntity): Boolean {
                    return oldUser == newUser
                }
            }
    }
}