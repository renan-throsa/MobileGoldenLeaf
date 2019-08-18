package com.mithril.mobilegoldenleaf.models

import androidx.room.PrimaryKey

abstract class User {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String? = null
    var phoneNumber: String? = null

    constructor() {

    }

    constructor(name: String, phoneNumber: String) {
        this.name = name
        this.phoneNumber = phoneNumber
    }

    constructor(id: Int, name: String, phoneNumber: String) {
        this.id = id
        this.name = name
        this.phoneNumber = phoneNumber
    }


}
