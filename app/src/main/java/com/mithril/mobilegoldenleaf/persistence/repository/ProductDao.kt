package com.mithril.mobilegoldenleaf.persistence.repository


import androidx.room.*
import com.mithril.mobilegoldenleaf.models.Product

@Dao
interface ProductDao {

    @Insert
    fun save(product: Product): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(products: List<Product>)

    @Update
    fun update(product: Product)

    @Query("SELECT p.* FROM Product p WHERE p.description LIKE :term")
    fun search(term: String): List<Product>

    @Query("SELECT p.* FROM Product p WHERE p.id = :productId")
    fun get(productId: Long): Product

    @Query("SELECT * FROM Product")
    fun get(): List<Product>


    @Query("SELECT p.* FROM Product p WHERE p.categoryId = :categoryId")
    fun productsWith(categoryId: Long): List<Product>


}
