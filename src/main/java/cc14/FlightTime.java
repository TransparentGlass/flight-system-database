package cc14;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FlightTime {

    private static final SimpleDateFormat FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static long toMillis(String dateTime) {
        try {
            Date d = FORMAT.parse(dateTime);
            return d.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
