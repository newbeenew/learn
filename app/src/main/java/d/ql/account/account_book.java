package d.ql.account;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Vector;

public class account_book extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
    }

    public void addCurrent(View view){
        Intent intent = new Intent(this, AddCurrentActivity.class);
        startActivity(intent);
    }

    public Vector<account> get_accountVector() {
        return m_accountVector;
    }

    public void add_account(account _account) {
        m_accountVector.add(_account);
    }

    private Vector<account> m_accountVector;
}
