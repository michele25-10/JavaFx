package it.unife.lp.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper functions for handling dates.
 *
 * @author Marco Jakob
 */
public class DateUtil {
    /** The date pattern that is used for conversion. Change as you wish. */
    private static final String DATE_PATTERN = "dd.MM.yyyy";
    /** The date formatter. */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * Returns the given date as a well formatted String.
     * The above defined {@link DateUtil#DATE_PATTERN} is used.
     *
     * @param date the date to be returned as a string
     * @return formatted string
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Converts a String in the format of the defined {@link DateUtil#DATE_PATTERN}
     * to a {@link LocalDate} object.
     *
     * Returns null if the String could not be converted.
     *
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Checks the String whether it is a valid date.
     *
     * @param dateString the date as String
     * @return true if the String is a valid date
     */
    public static boolean validDate(String dateString) {
        // Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }

    /**
     * Checks if the first LocalDate is after the second LocalDate.
     *
     * @param date1 the first LocalDate
     * @param date2 the second LocalDate
     * @return true if date1 is after date2, false otherwise
     */
    public static boolean isAfter(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return false; // Se una delle date è null, non può essere dopo l'altra
        }
        return date1.isAfter(date2);
    }

    /**
     * Checks if the first date string is after the second date string.
     * The strings are parsed using the defined {@link DateUtil#DATE_PATTERN}.
     *
     * @param date1 the first date as a String
     * @param date2 the second date as a String
     * @return true if date1 is after date2, false otherwise
     */
    public static boolean isAfter(String date1, String date2) {
        LocalDate localDate1 = parse(date1);
        LocalDate localDate2 = parse(date2);

        if (localDate1 == null || localDate2 == null) {
            return false; // Se una delle date è null o non valida, restituisce false
        }

        return localDate1.isAfter(localDate2);
    }
}
