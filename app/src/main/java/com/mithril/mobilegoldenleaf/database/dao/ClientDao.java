package com.mithril.mobilegoldenleaf.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mithril.mobilegoldenleaf.models.Client;

import java.util.List;

@Dao
public interface ClientDao {

    @Insert
    long save(Client address);

    @Update
    void edit(Client address);

    @Query("SELECT * FROM Client")
    List<Client> all();

}
