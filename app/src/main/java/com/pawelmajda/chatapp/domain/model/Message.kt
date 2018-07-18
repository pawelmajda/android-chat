package com.pawelmajda.chatapp.domain.model

import com.pawelmajda.chatapp.utils.ViewType
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey

open class Message : RealmObject(), ViewType {
    @PrimaryKey
    var id: Long = 0
    var content: String = ""
    var user: User? = null
    @Index
    var userId: Long? = null
    var attachments: RealmList<Attachment> = RealmList()

    override fun getViewType(): Int {
        return if(userId == 1L) ViewTypes.MY_MESSAGE else ViewTypes.MESSAGE
    }
}