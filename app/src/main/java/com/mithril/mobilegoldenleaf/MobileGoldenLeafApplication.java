package com.mithril.mobilegoldenleaf;

import android.app.Application;

import androidx.room.Room;

import com.mithril.mobilegoldenleaf.database.MobileGoldenLeafDataBase;
import com.mithril.mobilegoldenleaf.database.dao.CategoryDao;
import com.mithril.mobilegoldenleaf.database.dao.ProductDao;
import com.mithril.mobilegoldenleaf.models.Category;
import com.mithril.mobilegoldenleaf.models.Product;

public class MobileGoldenLeafApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //initializeDatabase();
    }

    private void initializeDatabase() {
        Category bebidas = new Category("Bebidas");
        //Category leite_substitutos = new Category("Leite / Substitutos");

        MobileGoldenLeafDataBase database = Room
                .databaseBuilder(this, MobileGoldenLeafDataBase.class, "GoldenLeafDataBase.db")
                .allowMainThreadQueries()
                .build();

        CategoryDao categoryDao = database.getCategoryDao();
        long id = categoryDao.save(bebidas);
        ProductDao productDao = database.getProductDao();
        productDao.save(new Product((int) id, "Coca Cola", "Refrigerante Fanta Uva 2L", "12345678", 5.25));
        //dao.save(new Product(2, "Qboa", "Água sanitária", "12345687", 3.50));

    }
}
