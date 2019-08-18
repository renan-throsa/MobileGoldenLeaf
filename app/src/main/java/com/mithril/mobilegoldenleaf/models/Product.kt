package com.mithril.mobilegoldenleaf.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey

import java.io.Serializable

import androidx.room.ForeignKey.SET_NULL
import java.math.BigDecimal

@Entity(foreignKeys = [ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["category_id"], onDelete = SET_NULL)])
class Product : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var category_id: Int = 0
    var brand: String? = null
    var description: String? = null
    var code: String? = null
    var unit_cost: BigDecimal = BigDecimal.ZERO
    var isIs_available: Boolean = false

    @Ignore
    constructor(id: Int, category_id: Int, brand: String, description: String, code: String, unit_cost: BigDecimal, is_available: Boolean) {
        this.id = id
        this.category_id = category_id
        this.brand = brand
        this.description = description
        this.code = code
        this.unit_cost = unit_cost
        this.isIs_available = is_available
    }

    @Ignore
    constructor(category_id: Int, brand: String, description: String, code: String, unit_cost: BigDecimal) {
        this.category_id = category_id
        this.brand = brand
        this.description = description
        this.code = code
        this.unit_cost = unit_cost
        this.isIs_available = true
    }

    constructor() {

    }

    override fun toString(): String {
        return "$brand'$description'$code'$unit_cost"
    }

    fun hasValidId(): Boolean {
        return this.id > 0
    }
}
