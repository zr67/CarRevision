package com.example.carrevision.database;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Converters class for the database
 */
public class Converters {
    /**
     * Converts a Long to a Date object
     * @param value Date in Long format
     * @return Date in Date format
     */
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    /**
     * Converts a Date to a Long object
     * @param date Date in Date format
     * @return Date in Long format
     */
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}