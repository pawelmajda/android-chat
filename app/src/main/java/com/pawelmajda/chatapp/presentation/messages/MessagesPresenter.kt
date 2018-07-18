package com.pawelmajda.chatapp.presentation.messages

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import com.pawelmajda.chatapp.domain.model.Attachment
import com.pawelmajda.chatapp.domain.model.Message
import com.pawelmajda.chatapp.domain.model.ViewTypes
import com.pawelmajda.chatapp.infrastructure.data.AttachmentsRepo
import com.pawelmajda.chatapp.infrastructure.data.AttachmentsRepository
import com.pawelmajda.chatapp.infrastructure.data.MessagesRepo
import com.pawelmajda.chatapp.utils.ViewType
import javax.inject.Inject

class MessagesPresenter @Inject constructor(val messagesRepository: MessagesRepo, val attachmentsRepository: AttachmentsRepo)
    : MvpBasePresenter<MessagesContract.View>(), MessagesContract.Presenter {

    override fun attachView(view: MessagesContract.View) {
        super.attachView(view)
        view.initItems(prepareItems(messagesRepository.getAll()))
    }

    override fun loadMoreMessages(before: Long) {
        view.addItems(prepareItems(messagesRepository.getAll(before)))
    }

    override fun deleteItem(item: ViewType) {
        when(item.getViewType()) {
            ViewTypes.ATTACHMENT -> deleteAttachment(item as Attachment)
            ViewTypes.MESSAGE, ViewTypes.MY_MESSAGE -> deleteMessage(item as Message)
        }
    }

    private fun deleteAttachment(attachment: Attachment) {
        attachmentsRepository.delete(attachment)
        view.deleteItemsFromList(arrayListOf(attachment))
    }

    private fun deleteMessage(message: Message) {
        messagesRepository.delete(message)

        val items = arrayListOf<ViewType>(message)
        items.addAll(message.attachments)
        view.deleteItemsFromList(items)
    }

    private fun prepareItems(messages: List<Message>) : List<ViewType> {
        val items: MutableList<ViewType> = ArrayList()

        for(message in messages) {
            items.addAll(message.attachments.reversed())
            items.add(message)
        }

        return items
    }
}