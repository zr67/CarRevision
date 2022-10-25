package com.example.carrevision.database;

import androidx.room.TypeConverter;

import com.example.carrevision.util.Status;

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

    /**
     * Converts an integer to a Status enum object
     * @param value Integer to convert
     * @return Status enum object
     */
    @TypeConverter
    public static Status fromInt(int value) {
        return Status.valueOf(value);
    }

    /**
     * Converts a Status enum object to int
     * @param status Status to convert
     * @return Integer
     */
    @TypeConverter
    public static int statusToInt(Status status) {
        return status.getValue();
    }
}