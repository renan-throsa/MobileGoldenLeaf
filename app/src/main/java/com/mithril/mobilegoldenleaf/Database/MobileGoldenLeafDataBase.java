package com.mithril.mobilegoldenleaf.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mithril.mobilegoldenleaf.Database.dao.CategoryDao;
import com.mithril.mobilegoldenleaf.Database.dao.ProductDao;
import com.mithril.mobilegoldenleaf.models.Category;
import com.mithril.mobilegoldenleaf.models.Product;

@Database(entities = {Category.class, Product.class}, version = 1, exportSchema = false)
public abstract class MobileGoldenLeafDataBase extends RoomDatabase {

    public abstract ProductDao getProductDao();

    public abstract CategoryDao getCategoryDao();
}
