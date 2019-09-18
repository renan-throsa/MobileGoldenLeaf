package com.mithril.mobilegoldenleaf.persistence.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import com.mithril.mobilegoldenleaf.models.Client

@Dao
interface ClientRepository {

    @Insert
    fun save(address: Client): Long

    @Update
    fun update(address: Client)

    @Query("SELECT * FROM Client")
    fun all(): List<Client>

    @Query("SELECT c.* FROM Client c WHERE c.name LIKE :term")
    fun search(term: String): List<Client>

}
