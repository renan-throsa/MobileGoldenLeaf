package com.mithril.mobilegoldenleaf.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Address {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var street: String? = null
    var zipCode: String? = null

    constructor()


    @Ignore
    constructor(id: Long, street: String, zipCode: String) {
        this.id = id
        this.street = street
        this.zipCode = zipCode
    }

}
