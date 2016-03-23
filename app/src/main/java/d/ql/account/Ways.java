package d.ql.account;

import java.util.Vector;
import d.ql.account.way;

/**
 * Created by ql on 16-3-17.
 */
public class Ways {

    public static Ways GetInstace()
    {
        return  s_instance;
    }
    public Vector<way> getWays() {
        return m_ways;
    }

    public void add_way(way _way){
        if(!m_ways.contains(_way)) {
            m_ways.add(_way);
        }
    }

    public way get_way(String name)
    {
        for (int i = 0; i < m_ways.size(); ++i){
            way _way = m_ways.elementAt(i);
            if (_way.get_name().equals(name)){
                return _way;
            }
        }
        return null;
    }


    private Vector<way> m_ways = new Vector<way>();
    private static Ways s_instance = new Ways();
}
