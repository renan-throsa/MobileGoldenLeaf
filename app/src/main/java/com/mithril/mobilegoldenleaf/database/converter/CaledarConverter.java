package com.mithril.mobilegoldenleaf.database.converter;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class CaledarConverter {

    @TypeConverter
    public Long toLong(Calendar calendar) {
        return calendar.getTimeInMillis();
    }

    @TypeConverter
    public Calendar toCalendar(Long value) {
        Calendar currentyMonment = Calendar.getInstance();
        if (value != null) {
            currentyMonment.setTimeInMillis(value);
        }
        return currentyMonment;
    }

}
