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

    public void remove_account(String name){
        for (int i=0;i < m_accountVec.size();++i) {
            if (m_accountVec.elementAt(i).getName().equals(name)){
                 m_accountVec.remove(i);
            }
        }
    }

    public account get_account(String name){
        for (int i=0;i < m_accountVec.size();++i) {
            if (m_accountVec.elementAt(i).getName().equals(name)){
                return m_accountVec.elementAt(i);
            }
        }
        return null;
    }
    public static accounts GetInstance(){
        return s_instance;
    }


    private Vector<account> m_accountVec;
    private static accounts s_instance = new accounts();
}