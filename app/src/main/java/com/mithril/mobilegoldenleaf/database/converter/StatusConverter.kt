package com.mithril.mobilegoldenleaf.database.converter

import androidx.room.TypeConverter

import com.mithril.mobilegoldenleaf.models.OrderStatus


class StatusConverter {

    @TypeConverter
    fun toString(orderStatus: OrderStatus): String {
        return orderStatus.name
    }

    @TypeConverter
    fun fromString(orderStatus: String?): OrderStatus {
        return if (orderStatus != null) {
            OrderStatus.valueOf(orderStatus)
        } else OrderStatus.PENDENTE
    }
}
