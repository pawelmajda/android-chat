package com.pawelmajda.chatapp.presentation.messages

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import com.pawelmajda.chatapp.R
import com.pawelmajda.chatapp.application.ChatApp
import com.pawelmajda.chatapp.presentation.messages.adapter.MessagesAdapter
import com.pawelmajda.chatapp.utils.InfiniteScrollListener
import com.pawelmajda.chatapp.utils.ViewType
import kotlinx.android.synthetic.main.fragment_messages.*

class MessagesFragment :
        MvpFragment<MessagesContract.View, MessagesContract.Presenter>(),
        MessagesContract.View {

    private val messagesAdapter by lazy { MessagesAdapter( { onItemClicked(it) }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ChatApp.messagesComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun createPresenter(): MessagesContract.Presenter {
        return ChatApp.messagesComponent.getPresenter()
    }

    override fun initItems(items: List<ViewType>) {
        initMessagesRecyclerView()

        messagesAdapter.initItems(items)
    }

    override fun addItems(items: List<ViewType>) {
        messagesList.post( { messagesAdapter.addItems(items) })
    }

    override fun deleteItemsFromList(items: List<ViewType>) {
        for (item in items) {
            messagesAdapter.deleteItem(item)
        }
    }

    private fun initMessagesRecyclerView() {
        messagesList.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
            layoutManager = linearLayout
            adapter = messagesAdapter

            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener(
                    { getPresenter().loadMoreMessages(messagesAdapter.getLastMessageId()) },
                    linearLayout))
        }
    }

    private fun onItemClicked(item: ViewType): Boolean {
        AlertDialog.Builder(context)
                .setMessage(getString(R.string.delete_text))
                .setPositiveButton(getString(R.string.delete_action)) { dialog, which -> getPresenter().deleteItem(item) }
                .setNegativeButton(getString(R.string.cancel_action), null)
                .create()
                .show()

        return true
    }
}