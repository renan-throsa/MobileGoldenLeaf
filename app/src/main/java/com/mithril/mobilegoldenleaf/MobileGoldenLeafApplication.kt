package com.mithril.mobilegoldenleaf

import android.app.Application

import androidx.room.Room

import com.mithril.mobilegoldenleaf.persistence.MobileGoldenLeafDataBase
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Product
import java.math.BigDecimal

class MobileGoldenLeafApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeDatabase()
    }

    private fun initializeDatabase() {
        val bebidas = Category("Bebidas")
        val leite_substitutos = Category("Leite / Substitutos")

        val database = Room
                .databaseBuilder(this, MobileGoldenLeafDataBase::class.java, "GoldenLeaf.db")
                .allowMainThreadQueries()
                .build()

        val categoryDao = database.categoryRepository
        val bebidasId = categoryDao.save(bebidas)
        val productRepository = database.productRepository
        productRepository.save(
                Product(bebidasId, "Coca Cola", "REFRIGERANTE COCA-COLA GARRAFA 2L", "7894900011517", BigDecimal(5.25)))
        val leite_substitutosId = categoryDao.save(leite_substitutos)
        productRepository.save(
                Product(leite_substitutosId, "NESTLE IDEAL", "NESTLÉ IDEAL SACHÊ", "7891000015391", BigDecimal(3.50)))

    }
}
