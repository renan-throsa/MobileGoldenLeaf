package com.mithril.mobilegoldenleaf.models

import androidx.room.*

import java.io.Serializable

import java.math.BigDecimal

@Entity
class Product : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
    @ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["categoryId"])
    var categoryId: Long = 0L
    var brand: String? = null
    var description: String? = null
    var code: String? = null
    var unitCost: BigDecimal = BigDecimal.ZERO
    var isAvailable: Boolean = false
    var synchronized: Boolean = false

    constructor()

    @Ignore
    constructor(id: Long, category_id: Long, brand: String, description: String, code: String, unit_cost: BigDecimal, is_available: Boolean) {
        this.id = id
        this.categoryId = category_id
        this.brand = brand
        this.description = description
        this.code = code
        this.unitCost = unit_cost
        this.isAvailable = is_available
    }

    @Ignore
    constructor(category_id: Long, brand: String, description: String, code: String, unit_cost: BigDecimal) {
        this.categoryId = category_id
        this.brand = brand
        this.description = description
        this.code = code
        this.unitCost = unit_cost
        this.isAvailable = true
    }


    override fun toString(): String {
        return "$brand'$description'$code'$unitCost"
    }

}
