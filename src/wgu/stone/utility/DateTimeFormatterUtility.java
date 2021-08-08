package wgu.stone.utility;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeFormatterUtility {

    private final static DateTimeFormatter d1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateTimeFormatterUtility() {

    }

    public static String formatDateTime(String dateTime) {
        LocalDateTime l = LocalDateTime.parse(dateTime, d1);
        ZonedDateTime z = l.atZone(ZoneOffset.UTC);
        ZonedDateTime zz = z.withZoneSameInstant(ZoneId.systemDefault());
        String formattedString = zz.format(d1);
        return formattedString;
    }
}
