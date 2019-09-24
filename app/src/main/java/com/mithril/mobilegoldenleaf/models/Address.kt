package com.mithril.mobilegoldenleaf.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Address {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
    var street: String? = null
    var zipCode: String? = null

    constructor()

    fun hasValidId(): Boolean {
        return id != 0L
    }

    @Ignore
    constructor(id: Long, street: String, zipCode: String) {
        this.id = id
        this.street = street
        this.zipCode = zipCode
    }

}
