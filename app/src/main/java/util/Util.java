package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ql on 16-4-19.
 */
public class Util {
    //把日期转为字符串
    public static String ConvertToDateString(Date date)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        return df.format(date);
    }
    //把字符串转为日期
    public static Date ConvertToDate(String strDate) throws Exception
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.parse(strDate);
    }

    public static Date ConvertToTime(String strDate)
    {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.parse(strDate);
        }catch (Exception e){
            // do nothing
            return null;
        }
    }
}
