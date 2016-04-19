package d.ql.account.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import d.ql.account.account;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyAccounts {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<account> ITEMS = new ArrayList<account>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, account> ITEM_MAP = new HashMap<String, account>();

    public static void clear(){
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static void addItem(account item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getName(), item);
    }


    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }



}
