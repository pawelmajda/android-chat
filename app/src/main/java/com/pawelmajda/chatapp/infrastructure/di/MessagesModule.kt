package com.pawelmajda.chatapp.infrastructure.di

import com.pawelmajda.chatapp.infrastructure.data.*
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class MessagesModule {

    @Provides
    fun provideMessagesRepo(realm: Realm): MessagesRepo {
        return MessagesRepository(realm)
    }

    @Provides
    fun provideUsersRepo(realm: Realm): UsersRepo {
        return UsersRepository(realm)
    }

    @Provides
    fun provideAttachmentsRepo(realm: Realm): AttachmentsRepo {
        return AttachmentsRepository(realm)
    }

}