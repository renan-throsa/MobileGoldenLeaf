package com.mithril.mobilegoldenleaf.models

import androidx.room.Entity
import androidx.room.Ignore

@Entity
class Client : User {

    var identification: String? = null
    var addressId: Long = 0L
    var status: Boolean? = null
    var notifiable: Boolean? = null

    constructor()

    @Ignore
    constructor(name: String, phoneNumber: String, identification: String, address_id: Long, notifiable: Boolean?) : super(name, phoneNumber) {
        this.identification = identification
        this.addressId = address_id
        this.notifiable = notifiable
        this.status = true
    }

    @Ignore
    constructor(id: Long, name: String, phoneNumber: String, identification: String, address_id: Long, status: Boolean?, notifiable: Boolean?) : super(id, name, phoneNumber) {
        this.identification = identification
        this.addressId = address_id
        this.status = status
        this.notifiable = notifiable
    }


}
