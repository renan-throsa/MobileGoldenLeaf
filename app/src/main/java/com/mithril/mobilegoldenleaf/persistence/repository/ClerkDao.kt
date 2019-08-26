package com.mithril.mobilegoldenleaf.persistence.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import com.mithril.mobilegoldenleaf.models.Clerk

@Dao
interface ClerkDao {
    @Insert
    fun save(address: Clerk): Long

    @Update
    fun edit(address: Clerk): Int

    @Query("SELECT * FROM Clerk")
    fun all(): List<Clerk>

}
