package com.example.carrevision.util;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class providing static methods for string formatting in the application
 */
public class StringUtility {
    /**
     * Converts an integer to a string with the separator
     * @param i Number
     * @param context Context
     * @return Formatted string
     */
    public static String intToString(int i, Context context) {
        LocaleManager localeManager = LocaleManager.getInstance();
        String rv = String.format(Locale.ENGLISH, "%,d", i);
        if (localeManager.getLanguage(context).equals("fr")) {
            rv = String.format(Locale.FRENCH, "%'d", i);
        }
        return rv;
    }

    /**
     * Converts a plate string into a plate string without the canton abbreviation
     * @param plate Full plate string
     * @return Plate string without the canton abbreviation
     */
    public static String plateWithoutAbbreviation(String plate) {
        return plate.substring(2);
    }

    /**
     * Converts a date into a string containing the date and the time
     * @param date Date to convert
     * @param context Context
     * @return Formatted string
     */
    public static String dateToDateTimeString(Date date, Context context) {
        LocaleManager localeManager = LocaleManager.getInstance();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        if (localeManager.getLanguage(context).equals("fr")) {
            df = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.FRENCH);
        }
        return df.format(date);
    }

    /**
     * Converts a date into a string containing only the year
     * @param date Date to convert
     * @param context Context
     * @return Formatted string
     */
    public static String dateToYearString(Date date, Context context) {
        LocaleManager localeManager = LocaleManager.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        if (localeManager.getLanguage(context).equals("fr")) {
            df = new SimpleDateFormat("yyyy", Locale.FRENCH);
        }
        return df.format(date);
    }

    /**
     * Gets the canton's abbreviation from a plate
     * @param plate Plate
     * @return Canton's abbreviation
     */
    public static String abbreviationFromPlate(String plate) {
        return plate.substring(0, 2);
    }
}
