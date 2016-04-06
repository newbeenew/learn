package DBManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import d.ql.account.way;

/**
 * Created by ql on 16-3-29.
 */
public class DBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "account_book.db";
    private static final  int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS account _id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, balance REAL)");
        db.execSQL("INSERT INTO account VALUES(NULL,?,?)", new Object[]{"现金",0});

        db.execSQL("CREATE TABLE IF NOT EXISTS way _id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, type INTEGER )");
        db.execSQL("INSERT INTO way VALUES(NULL,?,?", new Object[]{"支出", way.WAY_TYPE.OUTGO});

        db.execSQL("CREATE TABLE IF NOT EXISTS current _id INTEGER PRIMARY KEY AUTOINCREMENT, time timestamp NOT NULL DEFAULT (datetime('now','localtime')," +
                "  account_id INTEGER, way_id INTEGER, payment REAL, description TEXT )");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
