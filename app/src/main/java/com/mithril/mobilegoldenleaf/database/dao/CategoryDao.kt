package com.mithril.mobilegoldenleaf.database.dao

import androidx.room.Dao
import androidx.room.Insert

import com.mithril.mobilegoldenleaf.models.Category

@Dao
interface CategoryDao {
    @Insert
    fun save(category: Category): Long

}
