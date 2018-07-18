package com.pawelmajda.chatapp.infrastructure.di

import android.app.Application
import android.content.Context
import com.pawelmajda.chatapp.application.ChatApp
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class AppModule(val app: ChatApp) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication() : Application = app

    @Provides
    fun provideRealm() : Realm = Realm.getDefaultInstance()

}