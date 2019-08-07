package com.mithril.mobilegoldenleaf.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mithril.mobilegoldenleaf.models.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    long save(Order order);

    @Update
    void edit(Order order);

    @Query("SELECT * FROM `Order`")
    List<Order> all();

}
