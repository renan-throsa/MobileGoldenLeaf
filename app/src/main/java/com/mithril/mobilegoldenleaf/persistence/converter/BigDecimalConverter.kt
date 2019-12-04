package com.mithril.mobilegoldenleaf.persistence.converter

import androidx.room.TypeConverter
import java.math.BigDecimal


class BigDecimalConverter {

    @TypeConverter
    fun toDouble(value: BigDecimal): Double? {
        return value.toDouble()
    }

    @TypeConverter
    fun fromBigDecimal(value: Double?): BigDecimal {
        return if (value != null) {
            BigDecimal(value)
        } else BigDecimal.ZERO
    }

}