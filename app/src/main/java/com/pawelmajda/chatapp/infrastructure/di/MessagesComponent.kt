package com.pawelmajda.chatapp.infrastructure.di

import com.pawelmajda.chatapp.infrastructure.data.DataInitializer
import com.pawelmajda.chatapp.presentation.messages.MessagesFragment
import com.pawelmajda.chatapp.presentation.messages.MessagesPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (MessagesModule::class)])
interface MessagesComponent {
    fun inject(messagesFragment: MessagesFragment)

    fun getPresenter(): MessagesPresenter
    fun getDataInitalizer(): DataInitializer
}