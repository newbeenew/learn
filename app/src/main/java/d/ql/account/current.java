package d.ql.account;

/**
 * Created by ql on 16-3-16.
 */
import d.ql.account.account;
import d.ql.account.Ways;
public class current {

    public account get_account() {
        return m_account;
    }

    public void set_account(account m_account) {
        this.m_account = m_account;
    }

    public String get_descript() {
        return m_descript;
    }

    public void set_descript(String m_descript) {
        this.m_descript = m_descript;
    }

    public double get_payment() {
        return m_payment;
    }

    public void set_payment(double m_payment) {
        this.m_payment = m_payment;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }


    private account m_account;
    private String m_descript;
    private double m_payment;
    private String way;
}
