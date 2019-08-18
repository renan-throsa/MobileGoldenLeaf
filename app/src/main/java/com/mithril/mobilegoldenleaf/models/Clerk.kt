package com.mithril.mobilegoldenleaf.models


import androidx.room.Entity
import androidx.room.Ignore

@Entity
class Clerk : User {
    var email: String? = null
    var password: String? = null

    constructor() {}

    @Ignore
    constructor(name: String, phoneNumber: String, email: String, password: String) : super(name, phoneNumber) {
        this.email = email
        this.password = password
    }

    @Ignore
    constructor(id: Int, name: String, phoneNumber: String, email: String, password: String) : super(id, name, phoneNumber) {
        this.email = email
        this.password = password
    }


}
