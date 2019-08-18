package com.mithril.mobilegoldenleaf.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import com.mithril.mobilegoldenleaf.models.Client

@Dao
interface ClientDao {

    @Insert
    fun save(address: Client): Long

    @Update
    fun edit(address: Client)

    @Query("SELECT * FROM Client")
    fun all(): List<Client>

}
