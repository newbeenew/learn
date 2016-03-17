package d.ql.account;

import java.util.Vector;

/**
 * Created by ql on 16-3-17.
 */
public class Ways {

    public Vector<String> getWays() {
        return m_ways;
    }

    public void add_way(String way){
        m_ways.add(way);
    }


    private Vector<String> m_ways;
}
