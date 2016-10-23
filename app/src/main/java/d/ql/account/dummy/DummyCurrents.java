package d.ql.account.dummy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import d.ql.account.account;
import d.ql.account.current;
import d.ql.account.way;
import util.Util;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyCurrents {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
   // public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    public static void clear(){
        ITEMS.clear();
       // ITEM_MAP.clear();
    }

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
      //  ITEM_MAP.put(item.time, item);
    }
/*
    private static DummyItem createDummyItem(int position) {
        //return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }*/

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        private final Date time;
        public final String content;
        public final String details;
        public account _account;
        public way _way;
        public boolean bDetails ;
        public current _current;

        public DummyItem(Date time, String content, String details, current _current) {
            this.time = _current.get_time();
            this.content = content;
            this.details = details;
            bDetails = false;
            this._account =_current.get_account();
            this._way = _current.get_way();
            this._current = _current;
        }

        public String GetTime(){
            if (bDetails){
                return  Util.ConvertToDetailTimeString(time).replace(' ', '\n');
            }
            else{
                return Util.ConvertToDateString(time);
            }
        }

        @Override
        public String toString() {
            if (!bDetails)
                return content;
            else
                return content + "\n" + details + "\n" + _account.getName() + "\n" + (_way.get_type() == way.WAY_TYPE.INCOME?"收入:":"支出:") + _way.get_name();
        }
    }
}
