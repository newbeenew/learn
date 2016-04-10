package d.ql.account;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import DBManager.DBManager;
import d.ql.account.Accounts;
import d.ql.account.Ways;

public class AddCurrentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_current);

        m_dbManager = new DBManager(this);
        InitWaySpinner();
        InitSelectAccountSpinner();

        EditText editText = (EditText) findViewById(R.id.current_input);
        editText.setOnEditorActionListener(new onCurrentEditorActionListener(this));
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               EditText editText = (EditText) findViewById(R.id.current_input);
                               editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                               InputMethodManager inputManager =
                                       (InputMethodManager) editText.getContext().
                                               getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(editText, 0);
                           }

                       },

                998);


        set_time_fmt((EditText) findViewById(R.id.date), TIME_FMT.DATE);
        set_time_fmt((EditText) findViewById(R.id.time), TIME_FMT.TIME);

    }

    //把日期转为字符串
    public static String ConvertToString(Date date)
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

    private enum TIME_FMT{
        DATE,
        TIME
    }
    private void set_time_fmt( EditText edit, TIME_FMT fmt){

        SimpleDateFormat time_fmt;
        if(fmt == TIME_FMT.TIME){
            time_fmt = new SimpleDateFormat("HH:mm:ss");
        }else{
            time_fmt = new SimpleDateFormat("yyyy-MM-dd");
        }

        Date full_time = new Date(System.currentTimeMillis());
        edit.setText(time_fmt.format(full_time));
    }



    private void InitWaySpinner()
    {
        Spinner way = (Spinner)findViewById(R.id.select_way);
        Vector<way> ways = m_dbManager.get_allWay();//Ways.GetInstace().getWays();
        /*if (ways.size() <= 0){
            way consume_way = new way();
            consume_way.set_name("消费");
            consume_way.set_type(d.ql.account.way.WAY_TYPE.OUTGO);
            ways.add(consume_way);
        }*/

        Spinner type = (Spinner)findViewById(R.id.type);
        String type_name = type.getSelectedItem().toString();
        d.ql.account.way.WAY_TYPE eType = d.ql.account.way.WAY_TYPE.OUTGO;
        if (type_name.equals("收入") ) {
            eType = d.ql.account.way.WAY_TYPE.INCOME;
        }

        String[] ways_array = new String[ways.size()];
        for(int i = 0; i < ways.size(); ++i){
            if (eType == ways.elementAt(i).get_type()) {
                ways_array[i] = ways.elementAt(i).get_name();
            }
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ways_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        way.setAdapter(adapter);
    }

    private void InitSelectAccountSpinner()
    {
        Spinner select_account = (Spinner)findViewById(R.id.select_account);
        Vector<account> accounts = m_dbManager.get_allAccount();//Accounts.GetInstance().getM_accountVec();
       /* if (accounts.size() <= 0){
            account crash_account_tmp = new account();
            crash_account_tmp.setName("现金");
            crash_account_tmp.setBalance(0);
            accounts.add(crash_account_tmp);
        }*/
        String[] account_name_array = new String[accounts.size()];
        for(int i = 0; i < accounts.size(); ++i){
            account_name_array[i] = accounts.elementAt(i).getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, account_name_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_account.setAdapter(adapter);
    }

    public final static String add_current_result = "add_current_result";
    private class onCurrentEditorActionListener implements EditText.OnEditorActionListener{
        public onCurrentEditorActionListener(AddCurrentActivity _activity){
            activity=_activity;
        }

        public  boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            Spinner select_way_Spinner = (Spinner)findViewById(R.id.select_way);
            String way_name = select_way_Spinner.getSelectedItem().toString();
            way selected_way = m_dbManager.get_way(way_name);   //Ways.GetInstace().get_way(way_name);

            Spinner account = (Spinner)findViewById(R.id.select_account);
            String account_name = account.getSelectedItem().toString();
            account select_account =  m_dbManager.get_account(account_name);//Accounts.GetInstance().get_account(account_way);

           // EditText current_text = (EditText)findViewById(R.id.current_input);
            double current = Double.parseDouble(v.getText().toString());
            double new_Balance = select_account.getBalance() - current;
            select_account.setBalance(new_Balance);

            EditText decript_input = (EditText)findViewById(R.id.comment);

            current new_current = new current();
            new_current.set_account(select_account);
            new_current.set_description(decript_input.getText().toString());
            new_current.set_way(selected_way);
            new_current.set_payment(current);

            EditText date = (EditText)findViewById(R.id.date);
            String str_date = date.getText().toString();

            EditText time = (EditText)findViewById(R.id.time);
            str_date += ' ';
            str_date += time.getText().toString();

            new_current.set_time(ConvertToTime(str_date));
            m_dbManager.add_current(new_current);

            m_dbManager.update_account(select_account);
          /*  currents.GetInstance().add_current(new_current);
            AddCurrentActivity.this.setResult(RESULT_OK);
            AddCurrentActivity.this.finish();*/
            Intent intent = new Intent(activity,account_book_main.class);
            intent.putExtra(add_current_result,true);
            startActivity(intent);
            return true;
        }
        private AddCurrentActivity activity;
    }

    private FrameLayout create_time_piker(TIME_FMT fmt){
        if (fmt == TIME_FMT.TIME){
            return new TimePicker(this);
        }
        else{
            return new DatePicker(this);
        }
    }

    public void change_time(View view) {
        final Calendar c = Calendar.getInstance();
        final EditText edit = (EditText)view;
        if(edit.getId() == R.id.date){
            DatePickerDialog date_piker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    edit.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH),c.get(Calendar.DATE));
            date_piker.show();
        }
        else if(edit.getId() == R.id.time){
            TimePickerDialog time_piker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    edit.setText(hourOfDay + ":" + minute + "00");
                }
            }, c.get(Calendar.HOUR), c.get(Calendar.MINUTE),true);
            time_piker.show();
        }
    }

    private DBManager m_dbManager;
}
