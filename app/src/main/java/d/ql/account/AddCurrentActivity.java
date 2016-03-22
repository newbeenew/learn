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

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import d.ql.account.Accounts;

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
        timer.schedule(new TimerTask()
                       {
                           public void run() {
                               EditText editText = (EditText) findViewById(R.id.current_input);
                               editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                               InputMethodManager inputManager =
                                       (InputMethodManager)editText.getContext().
                                               getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(editText, 0);
                           }

                       },

                998);
    }

    private void InitWaySpinner()
    {
        Spinner way = (Spinner)findViewById(R.id.select_way);
        Vector<String> ways = Ways.GetInstace().getWays();
        if (ways.size() <= 0){
            ways.add("消费");
        }
        String[] ways_array = new String[ways.size()];
        ways.copyInto(ways_array);

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
        public onCurrentEditorActionListener(AddCurrentActivity _activity
        ){
            activity=_activity;
        }
        public  boolean onEditorAction(TextView v, int actionId, KeyEvent event)
        {

            new AlertDialog.Builder(activity)
                    .setTitle("标题")
                    .setMessage("简单消息框")
                    .setPositiveButton("确定", null)
                    .show();
            return true;
        }
        private AddCurrentActivity activity;
    }
}
