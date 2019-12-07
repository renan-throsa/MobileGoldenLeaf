package com.mithril.mobilegoldenleaf.models

import androidx.room.PrimaryKey

abstract class User {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
    var name: String = ""
    var phoneNumber: String = ""

    fun hasValidId(): Boolean {
        return id != 0L
    }

    constructor()

    constructor(name: String, phoneNumber: String) {
        this.name = name
        this.phoneNumber = phoneNumber
    }

    constructor(id: Long, name: String, phoneNumber: String) {
        this.id = id
        this.name = name
        this.phoneNumber = phoneNumber
    }


}
