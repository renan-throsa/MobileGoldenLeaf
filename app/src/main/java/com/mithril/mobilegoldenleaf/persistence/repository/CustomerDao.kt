package com.mithril.mobilegoldenleaf.persistence.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.OnConflictStrategy
import androidx.room.Query


import com.mithril.mobilegoldenleaf.models.Customer

@Dao
interface CustomerDao {

    @Insert
    fun insert(customer: Customer): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(customers: List<Customer>)

    @Update
    fun update(customer: Customer)

    @Query("SELECT * FROM Customer")
    fun get(): LiveData<List<Customer>>

    @Query("SELECT c.* FROM Customer c WHERE c.id = :clientId")
    fun get(clientId: Long): LiveData<Customer>

    @Query("SELECT c.* FROM Customer c WHERE c.name LIKE :query ORDER BY c.name")
    fun search(query: String): List<Customer>


}
