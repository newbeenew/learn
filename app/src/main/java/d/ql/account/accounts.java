package d.ql.account;

import java.util.Vector;

/**
 * Created by ql on 16-3-18.
 */
public class accounts{
    public Vector<account> getM_accountVec() {
        return m_accountVec;
    }

    public void add_account(account _account){
        m_accountVec.add(_account);
    }

    public void remove_account(account _account){
        m_accountVec.remove(_account);
    }
    
    public account get_account(String name){
        for (m_accountVec.:
             ) {
            
        }
    }

    private Vector<account> m_accountVec;
}