package com.mithril.mobilegoldenleaf.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mithril.mobilegoldenleaf.persistence.converter.BigDecimalConverter

import com.mithril.mobilegoldenleaf.persistence.converter.CaledarConverter
import com.mithril.mobilegoldenleaf.persistence.converter.StatusConverter
import com.mithril.mobilegoldenleaf.models.Category
import com.mithril.mobilegoldenleaf.models.Clerk
import com.mithril.mobilegoldenleaf.models.Customer
import com.mithril.mobilegoldenleaf.models.Item
import com.mithril.mobilegoldenleaf.models.Order
import com.mithril.mobilegoldenleaf.models.Product
import com.mithril.mobilegoldenleaf.persistence.repository.*

@Database(entities = [Clerk::class, Customer::class, Order::class, Item::class, Category::class, Product::class], version = 6, exportSchema = false)
@TypeConverters(CaledarConverter::class, StatusConverter::class, BigDecimalConverter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract val clerkRepository: ClerkRepository

    abstract val productDao: ProductDao

    abstract val categoryRepository: CategoryRepository

    abstract val customerDao: CustomerDao

    abstract val orderDao: OrderDao

    abstract val itemDao: ItemDao

    companion object {

        private const val GOLDEN_LEAF_DATABASE = "GoldenLeaf.db"

        fun getInstance(context: Context): AppDataBase {
            return Room
                    .databaseBuilder(
                            context,
                            AppDataBase::class.java,
                            GOLDEN_LEAF_DATABASE)
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
}
