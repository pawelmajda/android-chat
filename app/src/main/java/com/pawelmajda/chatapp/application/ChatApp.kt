package com.pawelmajda.chatapp.application

import android.app.Application
import com.pawelmajda.chatapp.infrastructure.di.AppModule
import com.pawelmajda.chatapp.infrastructure.di.DaggerMessagesComponent
import com.pawelmajda.chatapp.infrastructure.di.MessagesComponent
import io.realm.Realm
import android.preference.PreferenceManager

class ChatApp : Application() {
    val dataInitializedKey = "data_initialized"

    companion object {
        lateinit var messagesComponent: MessagesComponent
    }

    override fun onCreate() {
        super.onCreate()
        messagesComponent = DaggerMessagesComponent.builder()
                .appModule(AppModule(this))
                .build()
        Realm.init(this)

        initData()
    }

    fun initData() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if(!prefs.getBoolean(dataInitializedKey, false)) {
            messagesComponent.getDataInitalizer().initData()

            val editor = prefs.edit()
            editor.putBoolean(dataInitializedKey, true)
            editor.apply()
        }
    }
}