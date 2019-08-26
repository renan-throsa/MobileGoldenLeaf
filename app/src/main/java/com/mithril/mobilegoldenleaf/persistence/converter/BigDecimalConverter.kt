package com.mithril.mobilegoldenleaf.persistence.converter

import androidx.room.TypeConverter
import java.math.BigDecimal


class BigDecimalConverter {

    @TypeConverter
    fun fromLong(value: Long?): BigDecimal? {
        return if (value == null) null else BigDecimal(value)
    }

    @TypeConverter
    fun toLong(bigDecimal: BigDecimal?): Long? {
        return bigDecimal?.toLong()
    }
}