package com.mithril.mobilegoldenleaf.persistence.repository

import androidx.room.*

import com.mithril.mobilegoldenleaf.models.Address

@Dao
interface AddressRepository {

    @Insert
    fun save(address: Address): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun update(address: Address)

    @Query("SELECT a.* FROM Address a")
    fun all(): List<Address>

    @Query("SELECT a.* FROM Address a WHERE a.id = :addressId")
    fun get(addressId: Long): Address

    @Delete
    fun delete(address: Address)


}
