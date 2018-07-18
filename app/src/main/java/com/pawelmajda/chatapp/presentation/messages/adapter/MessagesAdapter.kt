package com.pawelmajda.chatapp.presentation.messages.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.pawelmajda.chatapp.domain.model.Message
import com.pawelmajda.chatapp.domain.model.ViewTypes
import com.pawelmajda.chatapp.utils.ViewType
import com.pawelmajda.chatapp.utils.ViewTypeDelegateAdapter

class MessagesAdapter(clickFunc: (ViewType) -> Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: MutableList<ViewType> = ArrayList()
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private var ids: MutableMap<ViewType, Long> = HashMap()
    private var lastId: Long = 0

    init {
        setHasStableIds(true)
        delegateAdapters.put(ViewTypes.MESSAGE, MessageDelegateAdapter(clickFunc))
        delegateAdapters.put(ViewTypes.MY_MESSAGE, MyMessageDelegateAdapter(clickFunc))
        delegateAdapters.put(ViewTypes.ATTACHMENT, AttachmentDelegateAdapter(clickFunc))
    }

    fun initItems(items: List<ViewType>) {
        this.items.clear()
        lastId = 0
        addItems(items)
    }

    fun addItems(items: List<ViewType>) {
        val positionStart = this.items.lastIndex + 1

        this.items.addAll(items)

        for (item in items) {
            ids[item] = lastId++
        }

        notifyItemRangeChanged(positionStart, items.size)
    }

    override fun getItemId(position: Int): Long {
        val item = items[position]

        if(!ids.contains(item)) {
            ids[item] = lastId++
        }

        return ids[item]!!
    }

    fun deleteItem(item: ViewType) {
        if(!items.contains(item)) {
            return
        }

        val position = items.indexOf(item)
        items.removeAt(position)
        ids.remove(item)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    fun getLastMessageId(): Long {
        val lastMessage = items.last { it.getViewType() == ViewTypes.MESSAGE || it.getViewType() == ViewTypes.MY_MESSAGE  } as Message

        return lastMessage.id
    }

    class MessageItem(val item: ViewType, val id: Long)
}