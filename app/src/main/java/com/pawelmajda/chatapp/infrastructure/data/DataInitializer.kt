package com.pawelmajda.chatapp.infrastructure.data

import android.content.Context
import com.google.gson.Gson
import com.pawelmajda.chatapp.R
import com.pawelmajda.chatapp.domain.model.Message
import com.pawelmajda.chatapp.domain.model.User
import javax.inject.Inject

class DataInitializer @Inject constructor(val context: Context,
                                          private val usersRepository: UsersRepo,
                                          private val messagesRepository: MessagesRepo) {

    fun initData() {
        val data = Gson().fromJson(context.resources.openRawResource(R.raw.data).reader(), Data::class.java)

        for(user in data.users) {
            usersRepository.createOrUpdate(user)
        }

        for(message in data.messages) {
            messagesRepository.createOrUpdate(message)
        }
    }

    class Data {
        var messages: List<Message> = ArrayList()
        var users: List<User> = ArrayList()
    }
}


