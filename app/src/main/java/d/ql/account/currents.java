package d.ql.account;

import java.util.Vector;

/**
 * Created by ql on 16-3-18.
 */
public class currents {

    public Vector<current> get_currentVec() {
        return m_currentVec;
    }

    public void add_current(current current) {
        this.m_currentVec = m_currentVec;
    }

    public void remove_current(current _current){
        m_currentVec.remove(_current);
    }

    public void read_data()
    {

    }

    public static currents GetInstance(){
        return s_instance;
    }

    private Vector<current> m_currentVec;
    private static currents s_instance = new currents();
}
