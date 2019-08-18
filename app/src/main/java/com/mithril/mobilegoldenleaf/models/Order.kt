package com.mithril.mobilegoldenleaf.models

import androidx.room.Entity
import androidx.room.PrimaryKey

import java.util.Calendar

@Entity
class Order {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var client_id: Int = 0
    var clerk_id: Int = 0
    var date: Calendar? = null
    var status: OrderStatus? = null

    constructor() {}

    constructor(client_id: Int, clerk_id: Int, date: Calendar, status: OrderStatus) {
        this.client_id = client_id
        this.clerk_id = clerk_id
        this.date = date
        this.status = status
    }

    constructor(id: Int, client_id: Int, clerk_id: Int, date: Calendar, status: OrderStatus) {
        this.id = id
        this.client_id = client_id
        this.clerk_id = clerk_id
        this.date = date
        this.status = status
    }


}
