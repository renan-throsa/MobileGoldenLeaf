package com.mithril.mobilegoldenleaf.persistence.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import com.mithril.mobilegoldenleaf.models.Address

@Dao
interface AddressDao {

    @Insert
    fun save(address: Address): Long

    @Update
    fun edit(address: Address)

    @Query("SELECT * FROM Address")
    fun all(): List<Address>

}
