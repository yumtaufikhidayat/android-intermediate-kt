package com.taufik.androidintemediate.advancedatabase.paging.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.taufik.androidintemediate.advancedatabase.paging.data.QuoteResponseItem
import com.taufik.androidintemediate.databinding.ItemQuoteBinding

class QuoteListAdapter : PagingDataAdapter<QuoteResponseItem, QuoteListAdapter.QuoteViewHolder>(quoteDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        return QuoteViewHolder(ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.onBind(data)
        }
    }

    inner class QuoteViewHolder(private val binding: ItemQuoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: QuoteResponseItem) = with(binding) {
            tvItemQuote.text = data.en
            tvItemAuthor.text = data.author
        }
    }

    companion object {
        val quoteDiffCallback = object : DiffUtil.ItemCallback<QuoteResponseItem>() {
            override fun areItemsTheSame(
                oldItem: QuoteResponseItem,
                newItem: QuoteResponseItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: QuoteResponseItem,
                newItem: QuoteResponseItem
            ): Boolean = oldItem.id == newItem.id
        }
    }
}