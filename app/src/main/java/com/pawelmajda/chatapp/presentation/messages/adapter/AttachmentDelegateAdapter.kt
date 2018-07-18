package com.pawelmajda.chatapp.presentation.messages.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.pawelmajda.chatapp.R
import com.pawelmajda.chatapp.domain.model.Attachment
import com.pawelmajda.chatapp.utils.ViewType
import com.pawelmajda.chatapp.utils.ViewTypeDelegateAdapter
import com.pawelmajda.chatapp.utils.inflate
import kotlinx.android.synthetic.main.item_attachment.view.*
import android.widget.RelativeLayout
import com.bumptech.glide.request.RequestOptions


class AttachmentDelegateAdapter(val clickFunc: (ViewType) -> Boolean) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return AttachmentViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as AttachmentViewHolder).bind(item as Attachment)
    }

    inner class AttachmentViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_attachment)) {

        fun bind(attachment: Attachment) = with(itemView) {
            title.text = attachment.title

            Glide.with(this)
                    .load(attachment.url)
                    .apply(RequestOptions()
                            .placeholder(R.drawable.ic_empty_attachment)
                            .error(R.drawable.ic_empty_attachment))
                    .into(thumbnail)


            this.setOnLongClickListener { clickFunc(attachment) }

        }
    }
}