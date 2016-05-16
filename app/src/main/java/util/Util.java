package util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import d.ql.account.R;

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

    public static String ConvertToDetailTimeString(Date time)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return df.format(time);
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

    public static void ChangeDataText(Context context, View view) {
        final Calendar c = Calendar.getInstance();
        final TextView edit = (TextView)view;

            DatePickerDialog date_piker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    edit.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),c.get(Calendar.DATE));
            date_piker.show();

    }

    public static void ChangeTimeText(Context context, View view) {
        final Calendar c = Calendar.getInstance();
        final TextView edit = (TextView)view;

        TimePickerDialog time_piker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                edit.setText(hourOfDay + ":" + minute + ":00");
            }
        }, c.get(Calendar.HOUR), c.get(Calendar.MINUTE),true);
        time_piker.show();

    }
}
