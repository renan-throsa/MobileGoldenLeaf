package com.mithril.mobilegoldenleaf.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Address {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var street: String? = null
    var zipCode: String? = null

    constructor() {

    }

    @Ignore
    constructor(street: String, zipCode: String) {
        this.street = street
        this.zipCode = zipCode
    }

    @Ignore
    constructor(id: Int, street: String, zipCode: String) {
        this.id = id
        this.street = street
        this.zipCode = zipCode
    }

}
