package com.mithril.mobilegoldenleaf.database.dao

import androidx.room.*

import com.mithril.mobilegoldenleaf.models.Product

@Dao
interface ProductDao {

    @Insert
    fun save(product: Product): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(product: Product)

    @Query("SELECT * FROM Product")
    fun all(): List<Product>


    @Query("SELECT p.* FROM Product p JOIN Category c ON p.categoryId = c.id WHERE p.categoryId = :categoryId")
    fun productsWith(categoryId: Int): List<Product>


}
