package wgu.stone.utility;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Provides the DateTimeFormatter methods.
 */
public final class DateTimeFormatterUtility {

    private final static DateTimeFormatter d1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Private constructor to prevent instantiation of this class.
     */
    private DateTimeFormatterUtility() {

    }

    /**
     * Formats a string and converts to a user's ZoneDateTime string.
     * @param dateTime dateTime parameter passed that will be parsed.
     * @return returns formatted string that is now in the User's ZoneDateTime.
     */
    public static String formatDateTime(String dateTime) {
        LocalDateTime l = LocalDateTime.parse(dateTime, d1);
        ZonedDateTime z = l.atZone(ZoneOffset.UTC);
        ZonedDateTime zz = z.withZoneSameInstant(ZoneId.systemDefault());
        String formattedString = zz.format(d1);
        return formattedString;
    }
}
