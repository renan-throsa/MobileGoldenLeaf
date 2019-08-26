package com.mithril.mobilegoldenleaf.persistence.repository

import androidx.room.*

import com.mithril.mobilegoldenleaf.models.Product

@Dao
interface ProductRepository {

    @Insert
    fun save(product: Product): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(product: Product)

    @Query("SELECT * FROM Product")
    fun all(): List<Product>

    @Query(value = "")
    fun search(term:String): List<Product>

    @Query(value = "SELECT p.* FROM Product p WHERE p.id = :productId")
    fun get(productId: Long): Product

    @Query("SELECT p.* FROM Product p JOIN Category c ON p.categoryId = c.id WHERE p.categoryId = :categoryId")
    fun productsWith(categoryId: Int): List<Product>


}
