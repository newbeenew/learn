package d.ql.account;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class AddCurrentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_current);

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

}
