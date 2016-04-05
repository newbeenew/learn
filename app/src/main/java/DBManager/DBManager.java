package DBManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

import d.ql.account.account;

/**
 * Created by ql on 16-4-5.
 */
public class DBManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public account get_account(String name) {
        account _account = null;
        Cursor account_cursor = db.rawQuery("SELECT * FROM account WHERE NAME = ?", new String[]{name});
        if (account_cursor.getCount() != 0){
            _account = new account();
            _account.setName(account_cursor.getString(0));
            _account.setBalance(account_cursor.getDouble(1));
        }
        account_cursor.close();
        return _account;
    }

    public Vector<account> get_accountVec(){
        Vector<account> accounts = new Vector<account>(0);
        Cursor accounts_cursor = db.rawQuery("SELECT * FROM account", null);
        while (accounts_cursor.moveToNext()){
            account _account = new account();
            _account.setName(accounts_cursor.getString(0));
            _account.setBalance(accounts_cursor.getDouble(1));
            accounts.add(_account);
        }
        return accounts;
    }

    public void add_account( account _account){
        db.execSQL("INSERT INTO account VALUES(NULL,?,?)", new String[]{_account.getName(),Double.toString(_account.getBalance())});
    }

    public void update_account( account _account){
        db.execSQL("UPDATE account SET blance = ? WHERE name = ?", new String[]{_account.getName(),Double.toString(_account.getBalance())});
    }
}
