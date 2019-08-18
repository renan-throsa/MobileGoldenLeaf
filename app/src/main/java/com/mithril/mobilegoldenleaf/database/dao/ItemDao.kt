package com.mithril.mobilegoldenleaf.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import com.mithril.mobilegoldenleaf.models.Item

@Dao
interface ItemDao {

    @Insert
    fun save(item: Item): Long

    @Update
    fun edit(item: Item): Int

    @Query("SELECT * FROM Item")
    fun all(): List<Item>
}
