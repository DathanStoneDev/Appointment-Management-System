package wgu.stone.utility;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;


/**
 * Provides the DateTimeFormatter methods.
 */
public final class DateTimeFormatterUtility {

    public final static DateTimeFormatter d1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Private constructor to prevent instantiation of this class.
     */
    private DateTimeFormatterUtility() {

    }

    public static String formatDateTimeForTableview(LocalDateTime ldt) {
        String formattedString = ldt.format(d1);
        System.out.println(formattedString);
        return formattedString;
    }

    public static String formatLocalDateTimeUTCForDatabase(LocalDateTime ldt) {
        ZonedDateTime loc = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        ZonedDateTime utc = loc.withZoneSameInstant(ZoneOffset.UTC);
        String startFinal = utc.format(d1);
        return startFinal;
    }

    public static LocalDateTime formatDateTimeFromDatabase(Timestamp t) {
        LocalDateTime l = t.toLocalDateTime();
        ZonedDateTime z = l.atZone(ZoneId.of("UTC"));
        ZonedDateTime zz = z.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime finalLocalDateTime = zz.toLocalDateTime();
        return finalLocalDateTime;
    }

}
