package com.mithril.mobilegoldenleaf.models

import androidx.room.Entity
import androidx.room.Ignore

@Entity
class Client : User {

    var identification: String? = null
    var address_id: Int = 0
    var status: Boolean? = null
    var notifiable: Boolean? = null

    constructor() {}

    @Ignore
    constructor(name: String, phoneNumber: String, identification: String, address_id: Int, notifiable: Boolean?) : super(name, phoneNumber) {
        this.identification = identification
        this.address_id = address_id
        this.notifiable = notifiable
        this.status = true
    }

    @Ignore
    constructor(id: Int, name: String, phoneNumber: String, identification: String, address_id: Int, status: Boolean?, notifiable: Boolean?) : super(id, name, phoneNumber) {
        this.identification = identification
        this.address_id = address_id
        this.status = status
        this.notifiable = notifiable
    }


}
