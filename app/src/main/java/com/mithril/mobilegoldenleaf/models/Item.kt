package com.mithril.mobilegoldenleaf.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import androidx.room.ForeignKey.CASCADE
import androidx.room.ForeignKey.SET_NULL
import androidx.room.Ignore

@Entity
class Item {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    @ForeignKey(entity = Order::class, parentColumns = ["id"], childColumns = ["orderId"])
    var orderId: Long = 0
    @ForeignKey(entity = Product::class, parentColumns = ["id"], childColumns = ["productId"])
    var productId: Long = 0
    var quantity: Double = 0.toDouble()
    var extendedCost: Double = 0.toDouble()

    constructor() {}

    @Ignore
    constructor(order_id: Long, product_id: Long, quantity: Double, extended_cost: Double) {
        this.orderId = order_id
        this.productId = product_id
        this.quantity = quantity
        this.extendedCost = extended_cost
    }

    @Ignore
    constructor(id: Long, order_id: Long, product_id: Long, quantity: Double, extended_cost: Double) {
        this.id = id
        this.orderId = order_id
        this.productId = product_id
        this.quantity = quantity
        this.extendedCost = extended_cost
    }
}
