package com.mithril.mobilegoldenleaf.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.mithril.mobilegoldenleaf.models.Category;

@Dao
public interface CategoryDao {
    @Insert
    long save(Category category);

}
