package com.mithril.mobilegoldenleaf.persistence.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

import com.mithril.mobilegoldenleaf.models.Order

@Dao
interface OrderDao {
    @Insert
    fun save(order: Order): Long

    @Update
    fun edit(order: Order)

    @Query("SELECT * FROM `Order`")
    fun all(): List<Order>

}
