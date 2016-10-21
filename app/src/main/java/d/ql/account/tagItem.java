package d.ql.account;

/**
 * Created by ql on 16-5-21.
 */
public class tagItem<T>{
    T item;
    boolean select;


    public tagItem(T _item, boolean isSelect)
    {
        item = _item;
        select = isSelect;
    }
}
