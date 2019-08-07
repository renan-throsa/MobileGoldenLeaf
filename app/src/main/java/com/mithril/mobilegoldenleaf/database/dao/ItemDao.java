package com.mithril.mobilegoldenleaf.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mithril.mobilegoldenleaf.models.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    long save(Item item);

    @Update
    int edit(Item item);

    @Query("SELECT * FROM Item")
    List<Item> all();
}
