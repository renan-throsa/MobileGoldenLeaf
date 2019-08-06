package com.mithril.mobilegoldenleaf.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mithril.mobilegoldenleaf.database.converter.CaledarConversor;
import com.mithril.mobilegoldenleaf.database.dao.CategoryDao;
import com.mithril.mobilegoldenleaf.database.dao.ProductDao;
import com.mithril.mobilegoldenleaf.models.Category;
import com.mithril.mobilegoldenleaf.models.Product;

@Database(entities = {Category.class, Product.class}, version = 1, exportSchema = false)
@TypeConverters({CaledarConversor.class})
public abstract class MobileGoldenLeafDataBase extends RoomDatabase {

    public abstract ProductDao getProductDao();

    public abstract CategoryDao getCategoryDao();
}
