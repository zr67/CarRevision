package com.example.carrevision.database;

import android.util.Base64;

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
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    /**
     * Converts a Date to a Long object
     * @param date Date in Date format
     * @return Date in Long format
     */
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    /**
     * Converts an integer to a Status enum object
     * @param value Integer to convert
     * @return Status enum object
     */
    public static Status fromInt(int value) {
        return Status.valueOf(value);
    }

    /**
     * Converts a Status enum object to int
     * @param status Status to convert
     * @return Integer
     */
    public static int statusToInt(Status status) {
        return status.getValue();
    }

    /**
     * Converts a base64 string to a byte array
     * @param value Base64 string
     * @return Byte array
     */
    public static byte[] fromBase64String(String value) {
        return Base64.decode(value, Base64.DEFAULT);
    }

    /**
     * Converts a byte array to a base64 string
     * @param value Byte array
     * @return Base64 string
     */
    public static String byteArrayToBase64String(byte[] value) {
        return Base64.encodeToString(value, Base64.DEFAULT);
    }
}