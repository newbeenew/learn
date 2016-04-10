package DBManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import d.ql.account.account;
import d.ql.account.current;
import d.ql.account.way;

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
            assert account_cursor.getCount() == 1;
            account_cursor.moveToNext();
            _account = new account();
            _account.setName(account_cursor.getString(1));
            _account.setBalance(account_cursor.getDouble(2));
        }
        account_cursor.close();
        return _account;
    }

    public Vector<account> get_allAccount(){
        Vector<account> accounts = new Vector<account>(0);
        Cursor accounts_cursor = db.rawQuery("SELECT * FROM account", null);
        while (accounts_cursor.moveToNext()){
            account _account = new account();
            _account.setName(accounts_cursor.getString(1));
            _account.setBalance(accounts_cursor.getDouble(2));
            accounts.add(_account);
        }
        accounts_cursor.close();
        return accounts;
    }

    public void add_account( account _account){
        db.execSQL("INSERT INTO account VALUES(NULL,?,?)", new String[]{
                _account.getName(),
                Double.toString(_account.getBalance())
        });
    }

    public void update_account( account _account){
        db.execSQL("UPDATE account SET balance = ? , name = ? WHERE _id = ?", new String[]{
                _account.getName(),
                Double.toString(_account.getBalance()),
                Integer.toString(_account.getDb_id())
        });
    }

    public way get_way(String name){
        way _way = null;
        Cursor way_cursor = db.rawQuery("SELECT * FROM way WHERE NAME = ?", new String[]{name});
        if (way_cursor.getCount() != 0){
            assert way_cursor.getCount() == 1;
            way_cursor.moveToNext();
            _way = new way();
            _way.set_name(name);
            int type = way_cursor.getInt(2);
            _way.set_type(way.WAY_TYPE.values()[type]);

        }
        way_cursor.close();
        return _way;
    }

    public Vector<way> get_allWay(){
        Vector<way> ways = new Vector<>(0);
        Cursor ways_cursor = db.rawQuery("SELECT * FROM way", null);
        while (ways_cursor.moveToNext()){
            way _way = new way();
            _way.set_name(ways_cursor.getString(1));
            _way.set_type(way.WAY_TYPE.values()[ways_cursor.getInt(2)]);
            ways.add(_way);
        }
        return ways;
    }

    public void add_way(way _way){
        db.execSQL("INSERT INTO way VALUES(NULL,?,?)",
                new String[]{
                        _way.get_name(),
                        Integer.toString(_way.get_type().ordinal())
                });
    }

    public void update_wat(way _way){
        db.execSQL("UPDATE way SET name = ?, type = ? WHERE _id = ?",
                new String[]{
                        _way.get_name(),
                        Integer.toString(_way.get_type().ordinal()),
                        Integer.toString(_way.getDb_id())
                });
    }

    public Vector<current >get_currents(){
        Vector<current> all_current = new Vector<>(0);
        Cursor currents_cursor = db.rawQuery("SELECT current.*,way.*, account.*, account.balance FROM current LEFT JOIN ON current.way_id" +
                " = way._id LEFT JOIN ON current.account_id = account._id",null);
        while (currents_cursor.moveToNext()){
            current _current = new current();
            _current.set_description(currents_cursor.getString(currents_cursor.getColumnIndex("descript")));

            account _account = new account();
            _account.setDb_id(currents_cursor.getInt(currents_cursor.getColumnIndex("account._id")));
            _account.setName(currents_cursor.getString(currents_cursor.getColumnIndex("account.name")));
            _account.setBalance(currents_cursor.getInt(currents_cursor.getColumnIndex("account.balance")));
            _current.set_account(_account);

            way _way = new way();
            _way.setDb_id(currents_cursor.getInt(currents_cursor.getColumnIndex("way._id")));
            _way.set_name(currents_cursor.getString(currents_cursor.getColumnIndex("way.name")));
            _way.set_type(way.WAY_TYPE.values()[currents_cursor.getInt(currents_cursor.getColumnIndex("way.type"))]);
            _current.set_way(_way);

            _current.set_payment(currents_cursor.getDouble(currents_cursor.getColumnIndex("current.payment")));

            String timeStamp = currents_cursor.getString(currents_cursor.getColumnIndex("current.timestamp"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                _current.set_time(format.parse(timeStamp));
            }catch (ParseException e) {

            }

            all_current.add(_current);
        }
        return all_current;
    }

    public void add_current(current _current){
        db.execSQL("INSERT INTO current VALUES(NULL,?,?,?,?,?)", new String[]{
                        Long.toString(_current.get_time().getTime()),
                        Integer.toString(_current.get_account().getDb_id()),
                        Integer.toString(_current.get_way().getDb_id()),
                        Double.toString(_current.get_payment()),
                        _current.get_description()
        });
    }

    public void update_current(current _current){
        db.execSQL("UPDATE current SET time=?, account_id=?, way_id=?, payment=?, description=? where _id=?", new String[]{
                        Long.toString(_current.get_time().getTime()),
                        Integer.toString(_current.get_account().getDb_id()),
                        Integer.toString(_current.get_way().getDb_id()),
                        Double.toString(_current.get_payment()),
                        _current.get_description(),
                        Integer.toString(_current.getDb_id())
        });
    }
}
