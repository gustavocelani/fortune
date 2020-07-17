package com.gcelani.fortune.database;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Converters
 */
public class Converters {

    /**
     * timestampToDate
     * @param timestamp timestamp
     * @return date
     */
    @TypeConverter
    public static Date timestampToDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    /**
     * dateToTimestamp
     * @param date date
     * @return timestamp
     */
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
