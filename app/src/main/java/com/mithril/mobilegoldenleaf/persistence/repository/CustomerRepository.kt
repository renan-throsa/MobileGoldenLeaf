package com.mithril.mobilegoldenleaf.persistence.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.OnConflictStrategy
import androidx.room.Query


import com.mithril.mobilegoldenleaf.models.Customer

@Dao
interface CustomerRepository {

    @Insert
    fun save(customer: Customer): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(customers: List<Customer>)

    @Update
    fun update(address: Customer)

    @Query("SELECT * FROM Customer")
    fun get(): List<Customer>

    @Query("SELECT c.* FROM Customer c WHERE c.name LIKE :term")
    fun search(term: String): List<Customer>

    @Query("SELECT c.* FROM Customer c WHERE c.id = :clientId")
    fun get(clientId: Long): Customer

}
