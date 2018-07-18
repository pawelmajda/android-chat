package com.pawelmajda.chatapp.presentation.messages

import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.pawelmajda.chatapp.utils.ViewType

class MessagesContract {
    interface View : MvpView {
        fun initItems(items: List<ViewType>)
        fun addItems(items: List<ViewType>)
        fun deleteItemsFromList(items: List<ViewType>)
    }

    interface Presenter : MvpPresenter<View> {
        fun loadMoreMessages(before: Long)
        fun deleteItem(item: ViewType)
    }
}