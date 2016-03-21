package d.ql.account;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import d.ql.account.Ways;
import d.ql.account.accounts;
public class AddCurrentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_current);

        InitWaySpinner();
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
                       {
                           public void run()
                           {
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
        Spinner way = (Spinner)findViewById(R.id.way);
        Vector<String> ways = Ways.GetInstace().getWays();
        if (ways.size() <= 0){
            ways.add("消费");
        }
        String[] ways_array = new String[ways.size()];
        ways.copyInto(ways_array);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ways_array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//绑定 Adapter到控件
        way.setAdapter(adapter);
    }
}
