package com.pawelmajda.chatapp.domain.model

import com.pawelmajda.chatapp.utils.ViewType
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.RealmResults
import io.realm.annotations.Index
import io.realm.annotations.LinkingObjects
import io.realm.annotations.PrimaryKey
import java.util.*

open class Attachment : RealmObject(), ViewType {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var title: String = ""
    var url: String = ""
    var thumbnailUrl: String = ""

    override fun getViewType(): Int {
        return ViewTypes.ATTACHMENT
    }
}