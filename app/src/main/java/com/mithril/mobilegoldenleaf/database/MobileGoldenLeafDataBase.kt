package com.mithril.mobilegoldenleaf.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

import com.mithril.mobilegoldenleaf.database.converter.CaledarConverter
import com.mithril.mobilegoldenleaf.database.converter.StatusConverter
import com.mithril.mobilegoldenleaf.database.dao.AddressDao
import com.mithril.mobilegoldenleaf.database.dao.CategoryDao
import com.mithril.mobilegoldenleaf.database.dao.ClerkDao
import com.mithril.mobilegoldenleaf.database.dao.ClientDao
import com.mithril.mobilegoldenleaf.database.dao.ItemDao
import com.mithril.mobilegoldenleaf.database.dao.OrderDao
import com.mithril.mobilegoldenleaf.database.dao.ProductDao
import com.mithril.mobilegoldenleaf.models.Address
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Clerk
import com.mithril.mobilegoldenleaf.models.Client
import com.mithril.mobilegoldenleaf.models.Item
import com.mithril.mobilegoldenleaf.models.Order
import com.mithril.mobilegoldenleaf.models.Product

@Database(entities = [Clerk::class, Client::class, Address::class, Order::class, Item::class, Category::class, Product::class], version = 2, exportSchema = false)
@TypeConverters(CaledarConverter::class, StatusConverter::class)
abstract class MobileGoldenLeafDataBase : RoomDatabase() {

    abstract val productDao: ProductDao

    abstract val categoryDao: CategoryDao

    abstract val addressDao: AddressDao

    abstract val clerkDao: ClerkDao

    abstract val clientDao: ClientDao

    abstract val orderDao: OrderDao

    abstract val itemDao: ItemDao

    companion object {

        private val GOLDEN_LEAF_DATA_BASE_DB = "GoldenLeafDataBase.db"

        fun getInstance(context: Context): MobileGoldenLeafDataBase {
            return Room.databaseBuilder(context, MobileGoldenLeafDataBase::class.java, GOLDEN_LEAF_DATA_BASE_DB)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
}
