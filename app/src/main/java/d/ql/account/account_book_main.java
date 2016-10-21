package d.ql.account;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import DBManager.DBManager;
import d.ql.account.dummy.DummyCurrents;
import util.Util;

public class account_book_main extends AppCompatActivity implements
        current_list.OnListFragmentInteractionListener,
        account_list.OnListFragmentInteractionListener,
        CheckBoxListDlg.OnListFragmentInteractionListener,
        Util.OnSetDateListener{

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

   /* public void onListFragmentInteraction(Vector<tagItem<way>> values){
        //values.elementAt(0).item;
    }*/

    /*public void onListFragmentInteraction(Vector<tagItem<account>> values){
        //values.elementAt(0).item;
    }*/

    private final static String DateType_START = "start_date";
    private final static String DateType_END= "end_date";

    public void OnSetDate(int year, int monthOfYear, int dayOfMonth, Object userdata)
    {
        Bundle bundle = new Bundle();
        bundle.putLong((String) userdata, new GregorianCalendar(year, monthOfYear, dayOfMonth).getTimeInMillis());

        current_list fragment =  (current_list)getSupportFragmentManager().findFragmentByTag("list");

        if ((String)userdata == DateType_START) {
            fragment.setStart_date(new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime());
        }
        else if((String)userdata == DateType_START) {
            fragment.setEnd_date(new GregorianCalendar(year, monthOfYear, dayOfMonth).getTime());
        }
      /*  android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.add(fragment, fragment.getTag());
        transaction.attach(fragment);
        transaction.commit();*/


    }
    public void clh_change_start_date(View view){
        current_list fragment =  (current_list)getSupportFragmentManager().findFragmentByTag("list");
        Date start = Util.ChangeDataText(this, view, fragment.getStart_date().getTime(),DateType_START);
    }

    public void clh_change_end_date(View view){
        current_list fragment =  (current_list)getSupportFragmentManager().findFragmentByTag("list");
        Date end = Util.ChangeDataText(this, view, fragment.getEnd_date().getTime(), DateType_END);
    }

    public void clh_select_accounts(View view){
        current_list fragment =  (current_list)getSupportFragmentManager().findFragmentByTag("list");

        CheckBoxListDlg<account> account_check_box = new CheckBoxListDlg<account>();
        account_check_box.setValues(fragment.getTag_accounts());
        setTagItems = new SetTagAccounts();

        checkbox_dlg = account_check_box;
        checkbox_dlg.show(getFragmentManager(), "选择帐号");
    }

    public void clh_select_ways(View view){
        current_list fragment =  (current_list)getSupportFragmentManager().findFragmentByTag("list");

        CheckBoxListDlg<way> way_check_box = new CheckBoxListDlg<way>();
        way_check_box.setValues(fragment.getTag_ways());
        setTagItems = new SetTagWays();

        checkbox_dlg = way_check_box;
        checkbox_dlg.show(getFragmentManager(), "选择方式");
      ///  checkbox_dlg.S
    }


    private DialogFragment checkbox_dlg;
    private SetTagItems setTagItems;

    interface SetTagItems{
          void SetTagItems(current_list fragment ,Vector v);
    }

    class SetTagAccounts implements SetTagItems{
        public  void SetTagItems(current_list fragment ,Vector v) {
            fragment.setTag_accounts(v);
        }
    }

    class SetTagWays implements SetTagItems{
        public  void SetTagItems(current_list fragment ,Vector v) {
            fragment.setTag_ways(v);
        }
    }

    @Override
    public void onListFragmentInteraction(Vector values) {
        current_list fragment =  (current_list)getSupportFragmentManager().findFragmentByTag("list");
        setTagItems.SetTagItems(fragment, values);
    }

    public void OnCancel() {
        checkbox_dlg.dismiss();
    }
}
