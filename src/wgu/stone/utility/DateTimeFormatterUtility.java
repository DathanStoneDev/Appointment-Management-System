package wgu.stone.utility;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;


/**
 * Provides the DateTimeFormatter methods.
 */
public final class DateTimeFormatterUtility {

    //DateTimeFormatter to match the required format for mySQL.
    private final static DateTimeFormatter d1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Private constructor to prevent instantiation of this class.
     */
    private DateTimeFormatterUtility() {

    }

    /**
     * Formats the LocalDateTime to the d1 formatter for the tableview presentation.
     * @param ldt startDateTime and endDateTime LocalDateTimes from the appointment objects.
     * @return returns a formatted string of the LocalDateTime for tableviews.
     */
    public static String formatDateTimeForTableview(LocalDateTime ldt) {
        String formattedString = ldt.format(d1);
        return formattedString;
    }

    /**
     * Formats the LocalDateTime to UTC to be stored in the Database.
     * @param ldt LocalDateTime from the startDateTime and endDateTime fields of an appointment object.
     * @return returns a formatted UTC String that will be persisted into the Database.
     */
    public static String formatLocalDateTimeUTCForDatabase(LocalDateTime ldt) {
        ZonedDateTime loc = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        ZonedDateTime utc = loc.withZoneSameInstant(ZoneOffset.UTC);
        String startFinal = utc.format(d1);
        return startFinal;
    }

    /**
     * Formats the Timestamp from the Database to a LocalDateTime based on the user's current locale.
     * @param t Timestamp pulled from the database.
     * @return returns the LocalDateTime that corresponds to the user's locale.
     */
    public static LocalDateTime formatDateTimeFromDatabase(Timestamp t) {
        LocalDateTime l = t.toLocalDateTime();
        ZonedDateTime z = l.atZone(ZoneId.of("UTC"));
        ZonedDateTime zz = z.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime finalLocalDateTime = zz.toLocalDateTime();
        return finalLocalDateTime;
    }

}
