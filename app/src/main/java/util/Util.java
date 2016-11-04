package util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import d.ql.account.R;
import d.ql.account.account;
import d.ql.account.way;

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

    public interface OnSetDateListener {
        // TODO: Update argument type and name
        public  boolean OnSetDate(int year, int monthOfYear, int dayOfMonth, Object userdata);
    }

    public static Date ChangeDataText(final Context context, View view, long initDate, final  Object userdata) {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(initDate);
        final TextView edit = (TextView)view;

        DatePickerDialog date_piker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                 public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                     OnSetDateListener listener = (OnSetDateListener)context;
                     if(!listener.OnSetDate(year, monthOfYear, dayOfMonth, userdata)){
                         return;
                     }
                     edit.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                  }
             }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),c.get(Calendar.DATE));
        date_piker.show();
        return c.getTime();
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

    public static double ChangeDoubleRecision(double value, int recision) {
        BigDecimal b = new BigDecimal(value);
        return b.setScale(recision, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public  static boolean UpdateAccount(account _account, way.WAY_TYPE wayType, double current){
        double b ;
        if (wayType == way.WAY_TYPE.INCOME){
            b = _account.getBalance() + current;
        }
        else{
            b = _account.getBalance() - current;
        }

        /*if(b.doubleValue() < 0) {
            return false;
        }*/

        _account.setBalance(ChangeDoubleRecision(b,2));
        return true;
    }
}
