package com.pawelmajda.chatapp.presentation.messages.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.pawelmajda.chatapp.R
import com.pawelmajda.chatapp.domain.model.Message
import com.pawelmajda.chatapp.utils.ViewType
import com.pawelmajda.chatapp.utils.ViewTypeDelegateAdapter
import com.pawelmajda.chatapp.utils.inflate
import kotlinx.android.synthetic.main.item_message.view.*

class MyMessageDelegateAdapter(val clickFunc: (ViewType) -> Boolean) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return MyMessageViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as MyMessageViewHolder).bind(item as Message)
    }

    inner class MyMessageViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_my_message)) {


        fun bind(message: Message) = with(itemView) {
            content.text = message.content

            this.setOnLongClickListener { clickFunc(message) }
        }
    }
}