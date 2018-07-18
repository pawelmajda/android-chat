package com.pawelmajda.chatapp.infrastructure.data

import com.pawelmajda.chatapp.domain.model.Attachment
import io.realm.Realm
import javax.inject.Inject

interface AttachmentsRepo {
    fun delete(attachment: Attachment)
}

class AttachmentsRepository @Inject constructor(val realm: Realm) : AttachmentsRepo {

    override fun delete(attachment: Attachment) {
        realm.executeTransaction { r ->
            val attachmentToDelete = r
                    .where(Attachment::class.java)
                    .equalTo("id", attachment.id)
                    .findFirst()

            if(attachmentToDelete != null) {
                attachmentToDelete.deleteFromRealm()
            }
        }
    }

}