package com.pawelmajda.chatapp.infrastructure.data

import com.pawelmajda.chatapp.domain.model.Message
import com.pawelmajda.chatapp.domain.model.User
import io.realm.Realm
import io.realm.Sort
import javax.inject.Inject

interface MessagesRepo {
    fun getAll(before: Long? = null, limit: Int = 20): List<Message>
    fun createOrUpdate(message: Message)
    fun delete(message: Message)
}

class MessagesRepository @Inject constructor(val realm: Realm) : MessagesRepo  {

    override fun getAll(before: Long?, limit: Int): List<Message> {
        if(before == null) {
            val size = realm.where(Message::class.java).findAll().size

            val messages = realm
                    .where(Message::class.java)
                    .greaterThan("id", size - limit + 1)
                    .sort("id", Sort.DESCENDING)
                    .findAll()

            return realm.copyFromRealm(messages)
        }

        val messages = realm
                .where(Message::class.java)
                .between("id", before - limit, before - 1)
                .sort("id", Sort.DESCENDING)
                .findAll()

        return realm.copyFromRealm(messages)
    }

    override fun createOrUpdate(message: Message) {
        realm.executeTransaction { r ->
            val user = r
                .where(User::class.java)
                .equalTo("id", message.userId)
                .findFirst()

            if(user != null) {
                message.user = r.copyFromRealm(user)
            }

            r.insertOrUpdate(message)
        }
    }

    override fun delete(message: Message) {
        realm.executeTransaction { r ->
            val messageToDelete = r
                    .where(Message::class.java)
                    .equalTo("id", message.id)
                    .findFirst()

            if(messageToDelete != null) {
                messageToDelete.deleteFromRealm()
            }
        }
    }

}