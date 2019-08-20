package com.mithril.mobilegoldenleaf.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import com.mithril.mobilegoldenleaf.models.Product

@Dao
interface ProductDao {

    @Insert
    fun save(product: Product): Long

    @Update
    fun update(product: Product)

    @Query("SELECT * FROM Product")
    fun all(): List<Product>


}
