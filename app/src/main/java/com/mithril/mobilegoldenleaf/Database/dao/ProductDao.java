package com.mithril.mobilegoldenleaf.Database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mithril.mobilegoldenleaf.models.Product;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    long save(Product product);

    @Update
    void edit(Product product);

    @Query("SELECT * FROM Product")
    List<Product> all();


}
