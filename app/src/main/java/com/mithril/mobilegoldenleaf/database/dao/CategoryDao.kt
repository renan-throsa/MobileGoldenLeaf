package com.mithril.mobilegoldenleaf.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import com.mithril.mobilegoldenleaf.models.Category

@Dao
interface CategoryDao {
    @Insert
    fun save(category: Category): Long

    @Update
    fun update(category: Category): Int

    @Query("SELECT c.* FROM Category c")
    fun all(): List<Category>
}
