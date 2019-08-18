package com.mithril.mobilegoldenleaf

import android.app.Application

import androidx.room.Room

import com.mithril.mobilegoldenleaf.database.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.database.dao.CategoryDao
import com.mithril.mobilegoldenleaf.database.dao.ProductDao
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product

class MobileGoldenLeafApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        //initializeDatabase();
    }

    private fun initializeDatabase() {
        val bebidas = Category("Bebidas")
        //Category leite_substitutos = new Category("Leite / Substitutos");

        val database = Room
                .databaseBuilder(this, MobileGoldenLeafDataBase::class.java, "GoldenLeafDataBase.db")
                .allowMainThreadQueries()
                .build()

        val categoryDao = database.categoryDao
        val id = categoryDao.save(bebidas)
        val productDao = database.productDao
        //productDao.save(Product(id.toInt(), "Coca Cola", "Refrigerante Fanta Uva 2L", "12345678", 5.25))
        //dao.save(new Product(2, "Qboa", "Água sanitária", "12345687", 3.50));

    }
}
