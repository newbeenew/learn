package d.ql.account;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.Reader;
import java.math.BigDecimal;
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
import util.Util;

public class AddCurrentActivity extends AppCompatActivity implements
    Util.OnSetDateListener{

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
                               InputMethodManager inputManager =
                                       (InputMethodManager) editText.getContext().
                                               getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(editText, 0);
                           }

                       },

                998);


        set_time_fmt((TextView) findViewById(R.id.date), TIME_FMT.DATE);
        set_time_fmt((TextView) findViewById(R.id.time), TIME_FMT.TIME);

    }



    private enum TIME_FMT{
        DATE,
        TIME
    }
    private void set_time_fmt( TextView edit, TIME_FMT fmt){

        SimpleDateFormat time_fmt;
        if(fmt == TIME_FMT.TIME){
            time_fmt = new SimpleDateFormat("HH:mm:ss");
        }else{
            time_fmt = new SimpleDateFormat("yyyy-MM-dd");
        }

        Date full_time = new Date(System.currentTimeMillis());
        edit.setText(time_fmt.format(full_time));
    }


    private Vector<String> ways_vec = new Vector<String>(0);


    private void SetWaySpinnerAdapter(Spinner way ){
        ways_vec.add("add new way");
        final String[] ways_array = new String[ways_vec.size()];
        ways_vec.copyInto(ways_array);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ways_array);
        way.setAdapter(adapter);
    }
    private void InitWaySpinner()
    {
        final Spinner way = (Spinner)findViewById(R.id.select_way);
        Vector<way> ways = m_dbManager.get_allWay();//Ways.GetInstace().getTag_ways();
        /*if (ways.size() <= 0){
            way consume_way = new way();
            consume_way.set_name("消费");
            consume_way.set_type(d.ql.account.way.WAY_TYPE.OUTGO);
            ways.add(consume_way);
        }*/

       /* Spinner type = (Spinner)findViewById(R.time.type);
        String type_name = type.getSelectedItem().toString();
        d.ql.account.way.WAY_TYPE eType = d.ql.account.way.WAY_TYPE.OUTGO;
        if (type_name.equals("收入") ) {
            eType = d.ql.account.way.WAY_TYPE.INCOME;
        }*/

        for(int i = 0; i < ways.size(); ++i){
            if (null != ways.elementAt(i).get_name()) {
                ways_vec.add(ways.elementAt(i).get_name());
            }
        }
        ways_vec.add("add new way");
        final String[] ways_array = new String[ways_vec.size()];
        ways_vec.copyInto(ways_array);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ways_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        way.setAdapter(adapter);

        way.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                if (position == (ways_array.length - 1)) {
                    LayoutInflater inflater = getLayoutInflater();
                    ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.add_way_dialog, (ViewGroup) findViewById(R.id.add_way_dialog));
                    ClickListener listener = new ClickListener(layout, way);
                    new AlertDialog.Builder(way.getContext())
                            .setTitle("add_way")
                            .setView(layout)
                            .setPositiveButton("确定",listener)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    way.setSelection(0);
                                }
                            })
                            .show();

                }
            }


            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        way.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int pos = i;
                new AlertDialog.Builder(way.getContext())
                        .setTitle("确定要删除way" + ways_array[pos] + " 吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBManager dbManager = new DBManager(getParent());
                                dbManager.delete_way(ways_array[pos]);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                return  true;
            }
        });
    }


    class ClickListener implements DialogInterface.OnClickListener {
        private ViewGroup layout;
        private Spinner spinner;
        public ClickListener(ViewGroup _layout, Spinner _spinner){
            layout = _layout;
            spinner = _spinner;
        }
        @Override
        public void onClick(DialogInterface dialog, int which) {
            setContentView(R.layout.add_way_dialog);
            way new_way = new way();

            TextView way_name = (TextView) layout.findViewById(R.id.add_way_name);
            String str_way_name = way_name.getText().toString();
            if (0 < str_way_name.length()) {
                new_way.set_name(way_name.getText().toString());
            } else {
                spinner.setSelection(0);
                return;
            }

            Spinner way_type = (Spinner) layout.findViewById(R.id.add_way_type);
            if (way_type.getSelectedItem().toString().equals("收入")) {
                new_way.set_type(d.ql.account.way.WAY_TYPE.INCOME);
            } else {
                new_way.set_type(d.ql.account.way.WAY_TYPE.OUTGO);
            }

            m_dbManager.add_way(new_way);
            ways_vec.addElement(new_way.get_name());
            SetWaySpinnerAdapter(spinner);
            spinner.setSelection(0);
        }
    }

    public boolean OnSetDate(int year, int monthOfYear, int dayOfMonth, Object userdata) {
        return true;
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

            double current = Double.parseDouble(v.getText().toString());

            Util.UpdateAccount(select_account,selected_way.get_type(), current);

            EditText decript_input = (EditText)findViewById(R.id.comment);

            current new_current = new current();
            new_current.set_account(select_account);
            new_current.set_description(decript_input.getText().toString());
            new_current.set_way(selected_way);
            new_current.set_payment(current);

            TextView date = (TextView)findViewById(R.id.date);
            String str_date = date.getText().toString();

            TextView time = (TextView)findViewById(R.id.time);
            str_date += ' ';
            str_date += time.getText().toString();

            new_current.set_time(Util.ConvertToTime(str_date));
            m_dbManager.add_current(new_current);

            m_dbManager.update_account(select_account);
          /*  currents.GetInstance().add_current(new_current);
            AddCurrentActivity.this.setResult(RESULT_OK);
            AddCurrentActivity.this.finish();*/
            Intent intent = new Intent(activity,account_book_main.class);
            intent.putExtra(add_current_result,true);
            startActivity(intent);
            AddCurrentActivity.this.finish();
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
        final TextView edit = (TextView)view;
        if(edit.getId() == R.id.date){
            final Calendar c = Calendar.getInstance();
            Util.ChangeDataText(this, view, c.getTimeInMillis(), null);
        }
        else if(edit.getId() == R.id.time){
            Util.ChangeTimeText(this, view);
        }
    }

    private DBManager m_dbManager;
}
