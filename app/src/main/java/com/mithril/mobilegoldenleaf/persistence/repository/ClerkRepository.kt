package com.mithril.mobilegoldenleaf.persistence.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mithril.mobilegoldenleaf.models.Clerk
@Dao
interface ClerkRepository {

    @Query("SELECT c.* FROM Clerk c WHERE c.email = :email")
    fun get(email: String): Clerk?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(clerk: Clerk)
}