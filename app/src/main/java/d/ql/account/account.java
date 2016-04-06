package d.ql.account;

import java.util.Vector;

/**
 * Created by ql on 16-3-16.
 */
public class account {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    private String name;
    private double balance;

    public int getDb_id() {
        return db_id;
    }

    public void setDb_id(int db_id) {
        this.db_id = db_id;
    }

    private int db_id;
}

