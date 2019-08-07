package com.mithril.mobilegoldenleaf.database.converter;

import androidx.room.TypeConverter;

import com.mithril.mobilegoldenleaf.models.OrderStatus;


public class StatusConverter {

    @TypeConverter
    public String toString(OrderStatus orderStatus) {
        return orderStatus.name();
    }

    @TypeConverter
    public OrderStatus fromString(String orderStatus) {
        if (orderStatus != null) {
            return OrderStatus.valueOf(orderStatus);
        }
        return OrderStatus.PENDENTE;
    }
}
