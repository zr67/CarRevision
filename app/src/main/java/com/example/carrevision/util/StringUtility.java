package com.example.carrevision.util;

import com.example.carrevision.ui.BaseActivity;

import java.text.DateFormat;
import java.text.ParseException;
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
     * @param activity Activity
     * @return Formatted string
     */
    public static String intToString(int i, BaseActivity activity) {
        LocaleManager localeManager = new LocaleManager(activity);
        String rv = String.format(Locale.ENGLISH, "%,d", i);
        if (localeManager.getLanguage().equals(LocaleManager.LANG_FR)) {
            rv = rv.replace(',', '\'');
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
     * @param activity Activity
     * @return Formatted string
     */
    public static String dateToDateTimeString(Date date, BaseActivity activity) {
        return getDateFormat(activity).format(date);
    }

    /**
     * Gets the correct date format
     * @param activity Activity
     * @return Date format
     */
    private static SimpleDateFormat getDateFormat(BaseActivity activity) {
        LocaleManager localeManager = new LocaleManager(activity);
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        if (localeManager.getLanguage().equals(LocaleManager.LANG_FR)) {
            df = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.FRENCH);
        }
        return df;
    }

    /**
     * Gets the date from a date-time string
     * @param dateTimeString Date-time string
     * @param activity Activity
     * @return Date from the date-time string or null if string is empty
     * @throws ParseException when the date can not be parsed
     */
    public static Date dateTimeStringToDate(String dateTimeString, BaseActivity activity) throws ParseException {
        if (dateTimeString.trim().equals("")) {
            return null;
        }
        SimpleDateFormat df = getDateFormat(activity);
        df.setLenient(false);
        return df.parse(dateTimeString);
    }

    /**
     * Converts a date into a string containing only the year
     * @param date Date to convert
     * @param activity Activity
     * @return Formatted string
     */
    public static String dateToYearString(Date date, BaseActivity activity) {
        LocaleManager localeManager = new LocaleManager(activity);
        DateFormat df = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        if (localeManager.getLanguage().equals(LocaleManager.LANG_FR)) {
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
