package d.ql.account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import DBManager.DBManager;

public class accountDetailActivity extends AppCompatActivity {

    private account _account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _account = new account();
        Intent intent = getIntent();
        _account.setName(intent.getStringExtra("account_name"));
        _account.setBalance(intent.getDoubleExtra("account_balance", 0));
        _account.setDb_id(intent.getIntExtra("account_db_id", 0));

        SetAccountView();

    }

    private void SetAccountView(){
        EditText et_name = (EditText)findViewById(R.id.account_detail_name);
        et_name.setText(_account.getName());

        EditText et_balance = (EditText)findViewById(R.id.account_detail_balance);
        et_balance.setText(Double.toString(_account.getBalance()));
    }


    public void modify_account(View view) {
        LayoutInflater inflater = getLayoutInflater();
        final ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.account_dialog, (ViewGroup) (findViewById(R.id.account_dialog)));

        EditText dlg_et_name = (EditText)layout.findViewById(R.id.account_name);
        dlg_et_name.setText(_account.getName());

        EditText dlg_et_balance = (EditText)layout.findViewById(R.id.account_balance);
        dlg_et_balance.setText(Double.toString(_account.getBalance()));

        new AlertDialog.Builder(this)
                .setTitle("add account")
                .setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager dbManager = new DBManager(accountDetailActivity.this);
                        EditText dlg_et_name = (EditText)layout.findViewById(R.id.account_name);
                        _account.setName(dlg_et_name.getText().toString());

                        EditText dlg_et_balance = (EditText)layout.findViewById(R.id.account_balance);
                        _account.setBalance(Double.parseDouble(dlg_et_balance.getText().toString()));
                        dbManager.update_account(_account);

                        SetAccountView();
                        Toast.makeText(accountDetailActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("取消",null)
                .show();
    }

    public void delete_account(View view){

        new AlertDialog.Builder(this)
                .setTitle("确定要删除帐号" + _account.getName() + " 吗?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager dbManager = new DBManager(accountDetailActivity.this);
                        dbManager.delete_account(_account);
                        accountDetailActivity.this.finish();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
