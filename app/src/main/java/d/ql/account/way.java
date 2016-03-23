package d.ql.account;

/**
 * Created by ql on 16-3-23.
 */
public class way {
    public enum WAY_TYPE{
        INCOME,
        OUTGO
    }

    public String get_name() {
        return m_name;
    }

    public void set_name(String _name) {
        this.m_name = _name;
    }

    public WAY_TYPE get_type() {
        return m_type;
    }

    public void set_type(WAY_TYPE _type) {
        this.m_type = _type;
    }

    private String m_name;
    private WAY_TYPE m_type;
}
