package DBManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            _account.setDb_id(account_cursor.getInt(account_cursor.getColumnIndex("_id")));
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
            _account.setDb_id(accounts_cursor.getInt(accounts_cursor.getColumnIndex("_id")));
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
                Double.toString(_account.getBalance()),
                _account.getName(),
                Integer.toString(_account.getDb_id())
        });
    }

    public void delete_account(account _account){
        db.execSQL("DELETE FROM account WHERE _id = ?", new String[]{
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
            _way.set_name(way_cursor.getString(way_cursor.getColumnIndex("name")));
            int type = way_cursor.getInt(2);
            _way.set_type(way.WAY_TYPE.values()[type]);
            _way.setDb_id(way_cursor.getInt(way_cursor.getColumnIndex("_id")));

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
            _way.setDb_id(ways_cursor.getInt(ways_cursor.getColumnIndex("_id")));
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

    public void update_way(way _way){
        db.execSQL("UPDATE way SET name = ?, type = ? WHERE _id = ?",
                new String[]{
                        _way.get_name(),
                        Integer.toString(_way.get_type().ordinal()),
                        Integer.toString(_way.getDb_id())
                });
    }

    public Vector<current >get_currents(String sql) {
        Vector<current> all_current = new Vector<>(0);
        Cursor currents_cursor = db.rawQuery(sql, null);
        while (currents_cursor.moveToNext()){
            current _current = new current();
            _current.set_description(currents_cursor.getString(currents_cursor.getColumnIndex("description")));

            account _account = new account();
            String s = currents_cursor.getString(currents_cursor.getColumnIndex("account_id"));
            _account.setDb_id(currents_cursor.getInt(currents_cursor.getColumnIndex("account_id")));
            int i = currents_cursor.getColumnIndex("account_name");
            s = currents_cursor.getString(i);
            _account.setName(currents_cursor.getString(currents_cursor.getColumnIndex("account_name")));
            _account.setBalance(currents_cursor.getDouble(currents_cursor.getColumnIndex("account.balance")));
            _current.set_account(_account);

            way _way = new way();
            _way.set_name(currents_cursor.getString(currents_cursor.getColumnIndex("way_name")));
            _way.set_type(way.WAY_TYPE.values()[currents_cursor.getInt(currents_cursor.getColumnIndex("way.type"))]);
            _current.set_way(_way);

            _current.set_payment(currents_cursor.getDouble(currents_cursor.getColumnIndex("current.payment")));

            String timeStamp = currents_cursor.getString(currents_cursor.getColumnIndex("current.time"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lcc = Long.valueOf(timeStamp);
            _current.set_time(new Date(lcc));
            try {
                _current.set_time(format.parse(timeStamp));
            }catch (ParseException e) {

            }

            all_current.add(_current);
        }
        return all_current;
    }

    public Vector<current >get_currents(){
        return get_currents("SELECT current._id , current.payment, current.time,current.description, current.way_id,current.account_id, " +
                "way.name AS way_name, way.type , account.name AS account_name, account.balance FROM  current LEFT OUTER JOIN way ON current.way_id" +
                " = way._id LEFT OUTER JOIN account ON current.account_id = account._id");
    }

    public Vector<current>get_currents(Date start, Date end, Vector<account> accounts, Vector<way> ways){
        String sql = "SELECT current._id , current.payment, current.time,current.description, current.way_id,current.account_id, " +
                "way.name AS way_name, way.type , account.name AS account_name, account.balance FROM  current LEFT OUTER JOIN way ON current.way_id" +
                " = way._id LEFT OUTER JOIN account ON current.account_id = account._id";
        sql += " where current.time >= " + String.valueOf(start.getTime());
        sql += " and current.time <= " + String.valueOf(end.getTime());

        sql += " and current.account_id in (";
        for (int i = 0; i < accounts.size(); ++i){
            sql += String.valueOf(accounts.elementAt(i).getDb_id()) + ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ")";

        sql += " and current.way_id in (";
        for (int i = 0; i < ways.size(); ++i){
            sql += String.valueOf(ways.elementAt(i).getDb_id()) + ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ")";

        return get_currents(sql);
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
