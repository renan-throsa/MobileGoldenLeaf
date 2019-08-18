package com.mithril.mobilegoldenleaf.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.SET_NULL

@Entity(foreignKeys = [ForeignKey(entity = Product::class, parentColumns = ["id"], childColumns = ["product_id"], onDelete = SET_NULL), ForeignKey(entity = Order::class, parentColumns = ["id"], childColumns = ["order_id"], onDelete = CASCADE)])
class Item {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var order_id: Int = 0
    var product_id: Int = 0
    var quantity: Double = 0.toDouble()
    var extended_cost: Double = 0.toDouble()

    constructor() {}

    constructor(order_id: Int, product_id: Int, quantity: Double, extended_cost: Double) {
        this.order_id = order_id
        this.product_id = product_id
        this.quantity = quantity
        this.extended_cost = extended_cost
    }

    constructor(id: Int, order_id: Int, product_id: Int, quantity: Double, extended_cost: Double) {
        this.id = id
        this.order_id = order_id
        this.product_id = product_id
        this.quantity = quantity
        this.extended_cost = extended_cost
    }
}
