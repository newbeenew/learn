package d.ql.account;

/**
 * Created by ql on 16-3-16.
 */
import java.util.Date;

import d.ql.account.account;
import d.ql.account.way;
public class current {

    public account get_account() {
        return m_account;
    }

    public void set_account(account m_account) {
        this.m_account = m_account;
    }

    public String get_description() {
        return m_description;
    }

    public void set_description(String _description) {
        this.m_description = _description;
    }

    public double get_payment() {
        return m_payment;
    }

    public void set_payment(double m_payment) {
        this.m_payment = m_payment;
    }

    public way get_way() {
        return m_way;
    }

    public void set_way(way _way) {
        this.m_way = _way;
    }

    public Date get_time() {
        return m_time;
    }

    public void set_time(Date time) {
        this.m_time = time;
    }

    public int getDb_id() {
        return db_id;
    }

    public void setDb_id(int db_id) {
        this.db_id = db_id;
    }

    private Date m_time;
    private account m_account;
    private String m_description;
    private double m_payment;
    private way m_way;
    private int db_id;
}
