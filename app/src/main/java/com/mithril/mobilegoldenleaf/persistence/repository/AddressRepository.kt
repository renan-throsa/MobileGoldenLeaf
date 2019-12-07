package com.mithril.mobilegoldenleaf.persistence.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query


import com.mithril.mobilegoldenleaf.models.Address

@Dao
interface AddressRepository {

    @Insert
    fun save(address: Address): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(address: Address)


    @Query("SELECT a.* FROM Address a WHERE a.id = :addressId")
    fun get(addressId: Long): Address


}
