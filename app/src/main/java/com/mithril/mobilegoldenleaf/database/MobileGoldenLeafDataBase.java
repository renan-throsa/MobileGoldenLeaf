package com.mithril.mobilegoldenleaf.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mithril.mobilegoldenleaf.database.converter.CaledarConverter;
import com.mithril.mobilegoldenleaf.database.converter.StatusConverter;
import com.mithril.mobilegoldenleaf.database.dao.AddressDao;
import com.mithril.mobilegoldenleaf.database.dao.CategoryDao;
import com.mithril.mobilegoldenleaf.database.dao.ClerkDao;
import com.mithril.mobilegoldenleaf.database.dao.ClientDao;
import com.mithril.mobilegoldenleaf.database.dao.ItemDao;
import com.mithril.mobilegoldenleaf.database.dao.OrderDao;
import com.mithril.mobilegoldenleaf.database.dao.ProductDao;
import com.mithril.mobilegoldenleaf.models.Address;
import com.mithril.mobilegoldenleaf.models.Category;
import com.mithril.mobilegoldenleaf.models.Clerk;
import com.mithril.mobilegoldenleaf.models.Client;
import com.mithril.mobilegoldenleaf.models.Item;
import com.mithril.mobilegoldenleaf.models.Order;
import com.mithril.mobilegoldenleaf.models.Product;

@Database(entities = {Clerk.class, Client.class, Address.class, Order.class, Item.class
        , Category.class, Product.class}
        , version = 1, exportSchema = false)
@TypeConverters({CaledarConverter.class, StatusConverter.class})
public abstract class MobileGoldenLeafDataBase extends RoomDatabase {

    public abstract ProductDao getProductDao();

    public abstract CategoryDao getCategoryDao();

    public abstract AddressDao getAddressDao();

    public abstract ClerkDao getClerkDao();

    public abstract ClientDao getClientDao();

    public abstract OrderDao getOrderDao();

    public abstract ItemDao getItemDao();
}
