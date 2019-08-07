package com.mithril.mobilegoldenleaf.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mithril.mobilegoldenleaf.models.Clerk;

import java.util.List;

@Dao
public interface ClerkDao {
    @Insert
    long save(Clerk address);

    @Update
    int edit(Clerk address);

    @Query("SELECT * FROM Clerk")
    List<Clerk> all();

}
