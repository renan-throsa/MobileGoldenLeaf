package com.mithril.mobilegoldenleaf.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mithril.mobilegoldenleaf.models.Address;

import java.util.List;

@Dao
public interface AddressDao {

    @Insert
    long save(Address address);

    @Update
    void edit(Address address);

    @Query("SELECT * FROM Address")
    List<Address> all();

}
