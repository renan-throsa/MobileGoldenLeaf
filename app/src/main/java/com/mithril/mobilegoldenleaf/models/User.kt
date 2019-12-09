package com.mithril.mobilegoldenleaf.models

import androidx.room.PrimaryKey

abstract class User {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
    var name: String = ""
    var phone_number: String = "000000000"


    constructor()

    constructor(name: String, phone_number: String) {
        this.name = name
        this.phone_number = phone_number
    }

    constructor(id: Long, name: String, phone_number: String) {
        this.id = id
        this.name = name
        this.phone_number = phone_number
    }


}
