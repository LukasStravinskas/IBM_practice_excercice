package lt.ibm.weather.utils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static Date stringToDateConverter(String sDate) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd:HH").parse(sDate);
    }
}
