package d.ql.account.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import d.ql.account.account;
import d.ql.account.way;

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
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    public static void clear(){
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
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
        public final String id;
        public final String content;
        public final String details;
        public account _account;
        public way _way;
        public boolean bDetails ;

        public DummyItem(String id, String content, String details, account _account, way _way) {
            this.id = id;
            this.content = content;
            this.details = details;
            bDetails = false;
            this._account =_account;
            this._way = _way;
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
