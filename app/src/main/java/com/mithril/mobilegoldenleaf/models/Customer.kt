package com.mithril.mobilegoldenleaf.models

import androidx.room.Entity
import androidx.room.Ignore

@Entity
class Customer : User {

    var identification: String = ""
    var address: String = ""
    var status: Boolean = true
    var notifiable: Boolean = true
    var synchronized: Boolean = false

    constructor()

    @Ignore
    constructor(name: String, phoneNumber: String, identification: String, address: String, notifiable: Boolean) : super(name, phoneNumber) {
        this.identification = identification
        this.address = address
        this.notifiable = notifiable
        this.status = true
    }

    @Ignore
    constructor(id: Long, name: String, phoneNumber: String, identification: String, address: String, status: Boolean, notifiable: Boolean) : super(id, name, phoneNumber) {
        this.identification = identification
        this.address = address
        this.status = status
        this.notifiable = notifiable
    }


}
