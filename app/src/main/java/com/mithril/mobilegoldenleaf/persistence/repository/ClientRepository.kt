package com.mithril.mobilegoldenleaf.persistence.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.OnConflictStrategy
import androidx.room.Query


import com.mithril.mobilegoldenleaf.models.Client

@Dao
interface ClientRepository {

    @Insert
    fun save(client: Client): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(clients: List<Client>)

    @Update
    fun update(address: Client)

    @Query("SELECT * FROM Client")
    fun all(): List<Client>

    @Query("SELECT c.* FROM Client c WHERE c.name LIKE :term")
    fun search(term: String): List<Client>

    @Query("SELECT c.* FROM Client c WHERE c.id = :clientId")
    fun get(clientId: Long): Client

}
