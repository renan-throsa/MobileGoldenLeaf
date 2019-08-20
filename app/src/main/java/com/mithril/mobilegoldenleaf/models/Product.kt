package com.mithril.mobilegoldenleaf.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey

import java.io.Serializable

import androidx.room.ForeignKey.SET_NULL
import java.math.BigDecimal

@Entity(foreignKeys = [ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["categoryId"], onDelete = SET_NULL)])
class Product : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var categoryId: Int = 0
    var brand: String? = null
    var description: String? = null
    var code: String? = null
    var unitCost: BigDecimal = BigDecimal.ZERO
    var isAvailable: Boolean = false

    @Ignore
    constructor(id: Int, category_id: Int, brand: String, description: String, code: String, unit_cost: BigDecimal, is_available: Boolean) {
        this.id = id
        this.categoryId = category_id
        this.brand = brand
        this.description = description
        this.code = code
        this.unitCost = unit_cost
        this.isAvailable = is_available
    }

    @Ignore
    constructor(category_id: Int, brand: String, description: String, code: String, unit_cost: BigDecimal) {
        this.categoryId = category_id
        this.brand = brand
        this.description = description
        this.code = code
        this.unitCost = unit_cost
        this.isAvailable = true
    }

    constructor() {

    }

    override fun toString(): String {
        return "$brand'$description'$code'$unitCost"
    }

    fun hasValidId(): Boolean {
        return this.id > 0
    }
}
