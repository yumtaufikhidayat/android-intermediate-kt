package com.taufik.androidintemediate.firebase

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.taufik.androidintemediate.R
import com.taufik.androidintemediate.databinding.ItemMessageBinding

class FirebaseMessageAdapter(
    options: FirebaseRecyclerOptions<Message>,
    private val currentUserName: String?
): FirebaseRecyclerAdapter<Message, FirebaseMessageAdapter.MessageViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            ItemMessageBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Message) {
        holder.onBind(model)
    }

    inner class MessageViewHolder(private val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: Message) = with(binding) {
            tvMessage.text = item.text
            setTextColor(item.name, tvMessage)
            tvMessenger.text = item.name

            Glide.with(itemView.context)
                .load(item.photoUrl)
                .circleCrop()
                .into(ivMessenger)

            if (item.timestamp != null) {
                tvTimestamp.text = DateUtils.getRelativeTimeSpanString(item.timestamp)
            }
        }

        private fun setTextColor(userName: String?, textView: TextView) {
            if (currentUserName == userName && userName != null) {
                textView.setBackgroundResource(R.drawable.rounded_message_blue)
            } else {
                textView.setBackgroundResource(R.drawable.rounded_message_yellow)
            }
        }
    }
}