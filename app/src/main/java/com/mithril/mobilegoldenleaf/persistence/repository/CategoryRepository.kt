package com.mithril.mobilegoldenleaf.persistence.repository

import androidx.room.*

import com.mithril.mobilegoldenleaf.models.Category

@Dao
interface CategoryRepository {
    @Insert
    fun save(category: Category): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(categories: List<Category>)

    @Update
    fun update(category: Category): Int

    @Query("SELECT c.* FROM Category c WHERE c.id = :categoryId")
    fun get(categoryId: Long): Category

    @Query("SELECT c.* FROM Category c")
    fun all(): List<Category>

    @Query("SELECT c.* FROM Category c WHERE c.title LIKE :term")
    fun search(term: String): List<Category>
}
