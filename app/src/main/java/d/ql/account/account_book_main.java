package d.ql.account;

import android.net.Uri;
import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import DBManager.DBManager;
import d.ql.account.dummy.DummyCurrents;
import util.Util;

public class account_book_main extends AppCompatActivity
        implements current_list.OnListFragmentInteractionListener,account_list.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        InitDataBase();

        layoutInflater = LayoutInflater.from(this);

        tabs = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabs.setup(this, getSupportFragmentManager(), R.id.my_tabcontent);
        tabs.getTabWidget().setDividerDrawable(null);

        for(int i = 0; i < mFragmentTags.length; i++){
            TabHost.TabSpec tabSpec = tabs.newTabSpec(mFragmentTags[i]).setIndicator(mFragmentTags[i]);
            tabs.addTab(tabSpec, mFragmentClass[i], null);
            tabs.getTabWidget().getChildTabViewAt(i).setBackgroundColor(0xFFFF0000);
        }


        //添加后重新回到这里会触发
        Intent intent = getIntent();
        if (intent != null){
            boolean success = intent.getBooleanExtra(AddCurrentActivity.add_current_result, false);
            if(success){
                Toast.makeText(account_book_main.this,"添加成功",Toast.LENGTH_LONG).show();
            }
        }
    }

    private LayoutInflater layoutInflater;


    // 标题
    private String mFragmentTags[] = {
            "add",
            "list",
            "accounts",
    };
    private Class mFragmentClass[] = {
            FragmentTab.class,
            current_list.class,
            account_list.class,
    };

    public void addCurrent(View view){
        Intent intent = new Intent(this, AddCurrentActivity.class);
        startActivity(intent);

    }

    private FragmentTabHost tabs;

    private void InitDataBase(){

        //SQLiteDatabase db = openOrCreateDatabase(database_name, Context.MODE_PRIVATE, null);


        //db.close();
        DBManager dbManager = new DBManager(this);
    }

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

/*
    public Vector<account> get_accountVector() {
        return m_accountVector;
    }



        public void add_account(account _account) {
        m_accountVector.add(_account);
    }

    private Vector<account> m_accountVector;*/


    @Override
    public void onListFragmentInteraction(DummyCurrents.DummyItem item) {
        item.bDetails = !item.bDetails;
    }

    @Override
    public void onListFragmentInteraction(account item) {
        Intent intent = new Intent(this, accountDetailActivity.class);
        intent.putExtra("account_name", item.getName());
        intent.putExtra("account_balance", item.getBalance());
        intent.putExtra("account_db_id",item.getDb_id());
        startActivity(intent);
    }

    public void clh_change_time(View view){
        Util.ChangeDataText(this, view);
    }
}
