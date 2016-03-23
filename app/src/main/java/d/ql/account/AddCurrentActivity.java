package d.ql.account;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Reader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import d.ql.account.Accounts;
import d.ql.account.Ways;

public class AddCurrentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_current);

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
    }

    private void InitWaySpinner()
    {
        Spinner way = (Spinner)findViewById(R.id.select_way);
        Vector<way> ways = Ways.GetInstace().getWays();
        if (ways.size() <= 0){
            way consume_way = new way();
            consume_way.set_name("消费");
            consume_way.set_type(d.ql.account.way.WAY_TYPE.OUTGO);
            ways.add(consume_way);
        }

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
        Vector<account> accounts = Accounts.GetInstance().getM_accountVec();
        if (accounts.size() <= 0){
            account crash_account_tmp = new account();
            crash_account_tmp.setName("现金");
            crash_account_tmp.setBalance(0);
            accounts.add(crash_account_tmp);
        }
        String[] account_name_array = new String[accounts.size()];
        for(int i = 0; i < accounts.size(); ++i){
            account_name_array[i] = accounts.elementAt(i).getName();
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, account_name_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_account.setAdapter(adapter);
    }

    private class onCurrentEditorActionListener implements EditText.OnEditorActionListener{
        public onCurrentEditorActionListener(AddCurrentActivity _activity){
            activity=_activity;
        }

        public  boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            Spinner select_way_Spinner = (Spinner)findViewById(R.id.select_way);
            String way_name = select_way_Spinner.getSelectedItem().toString();
            way selected_way = Ways.GetInstace().get_way(way_name);

            Spinner account = (Spinner)findViewById(R.id.select_account);
            String account_way = account.getSelectedItem().toString();
            account select_account = Accounts.GetInstance().get_account(account_way);

           // EditText current_text = (EditText)findViewById(R.id.current_input);
            double current = Double.parseDouble(v.getText().toString());
            double new_Balance = select_account.getBalance() - current;
            select_account.setBalance(new_Balance);

            EditText decript_input = (EditText)findViewById(R.id.comment);

            current new_current = new current();
            new_current.set_account(select_account);
            new_current.set_descript(decript_input.getText().toString());
            new_current.set_way(selected_way);
            new_current.set_payment(current);
            currents.GetInstance().add_current(new_current);
            AddCurrentActivity.this.finish();
            return true;
        }
        private AddCurrentActivity activity;
    }
}
