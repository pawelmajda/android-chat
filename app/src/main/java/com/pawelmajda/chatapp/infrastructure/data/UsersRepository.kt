package com.pawelmajda.chatapp.infrastructure.data

import com.pawelmajda.chatapp.domain.model.User
import io.realm.Realm
import javax.inject.Inject

interface UsersRepo {
    fun createOrUpdate(user: User)
}

class UsersRepository @Inject constructor(val realm: Realm) : UsersRepo {

    override fun createOrUpdate(user: User) {
        realm.executeTransaction { r ->
            r.insertOrUpdate(user)
        }
    }
}