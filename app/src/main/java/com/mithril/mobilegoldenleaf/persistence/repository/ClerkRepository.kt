package com.mithril.mobilegoldenleaf.persistence.repository

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mithril.mobilegoldenleaf.models.Clerk

interface ClerkRepository {

    @Query("SELECT c.* FROM Clerk c WHERE c.email = :clerkEmail")
    fun get(clerkEmail: String): Clerk?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(clerk: Clerk)
}