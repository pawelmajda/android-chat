package com.pawelmajda.chatapp.presentation
import com.nhaarman.mockito_kotlin.*
import com.pawelmajda.chatapp.domain.model.Attachment
import com.pawelmajda.chatapp.domain.model.Message
import com.pawelmajda.chatapp.infrastructure.data.AttachmentsRepo
import com.pawelmajda.chatapp.infrastructure.data.MessagesRepo
import com.pawelmajda.chatapp.presentation.messages.MessagesContract
import com.pawelmajda.chatapp.presentation.messages.MessagesPresenter
import io.realm.RealmList
import org.junit.Test

class MessagesPresenterTests {

    private val messagesRepository = mock<MessagesRepo>()
    private val attachmentsRepository = mock<AttachmentsRepo>()
    private val view = mock<MessagesContract.View>()
    private val subject = MessagesPresenter(messagesRepository, attachmentsRepository)

    init {
        subject.attachView(view)
    }

    @Test
    fun loadMoreMessages_should_succeed() {
        //arrange
        val before = 12L
        val messages = arrayListOf(Message(), Message(), Message())
        messages[1].attachments = RealmList(Attachment(), Attachment())
        whenever(messagesRepository.getAll(before)).thenReturn(messages)

        //act
        subject.loadMoreMessages(before)

        //assert
        verify(messagesRepository).getAll(before)
        verify(view).addItems(argThat { size == 5 })
    }

    @Test
    fun deleteItem_should_delete_attachment() {
        //arrange
        val attachment = Attachment()

        //act
        subject.deleteItem(attachment)

        //assert
        verify(attachmentsRepository).delete(attachment)
        verify(view).deleteItemsFromList(argThat { size == 1 })
    }

    @Test
    fun deleteItem_should_delete_message() {
        //arrange
        val message = Message()
        message.attachments = RealmList(Attachment(), Attachment())

        //act
        subject.deleteItem(message)

        //assert
        verify(messagesRepository).delete(message)
        verify(view).deleteItemsFromList(argThat { size == 3 })
    }


}