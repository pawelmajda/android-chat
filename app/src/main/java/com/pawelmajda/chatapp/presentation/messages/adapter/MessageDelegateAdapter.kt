package com.pawelmajda.chatapp.presentation.messages.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pawelmajda.chatapp.R
import com.pawelmajda.chatapp.domain.model.Message
import com.pawelmajda.chatapp.utils.ViewType
import com.pawelmajda.chatapp.utils.ViewTypeDelegateAdapter
import com.pawelmajda.chatapp.utils.inflate
import kotlinx.android.synthetic.main.item_message.view.*

class MessageDelegateAdapter(val clickFunc: (ViewType) -> Boolean) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return MessageViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as MessageViewHolder).bind(item as Message)
    }

    inner class MessageViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_message)) {

        fun bind(message: Message) = with(itemView) {


            author.text = message.user?.name
            content.text = message.content

            Glide.with(this)
                    .load(message.user?.avatarId)
                    .apply(RequestOptions()
                            .placeholder(R.drawable.ic_person)
                            .error(R.drawable.ic_person))
                    .into(avatar)

            this.setOnLongClickListener { clickFunc(message) }

        }
    }


}