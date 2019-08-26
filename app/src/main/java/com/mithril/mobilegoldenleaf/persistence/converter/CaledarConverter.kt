package com.mithril.mobilegoldenleaf.persistence.converter

import androidx.room.TypeConverter

import java.util.Calendar

class CaledarConverter {

    @TypeConverter
    fun toLong(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }

    @TypeConverter
    fun toCalendar(value: Long?): Calendar {
        val currentyMonment = Calendar.getInstance()
        if (value != null) {
            currentyMonment.timeInMillis = value
        }
        return currentyMonment
    }

}
