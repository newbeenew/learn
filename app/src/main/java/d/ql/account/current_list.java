package d.ql.account;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import DBManager.DBManager;
import d.ql.account.dummy.DummyCurrents;
import d.ql.account.dummy.DummyCurrents.DummyItem;
import util.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class current_list extends Fragment{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    public Date getStart_date() {
        return start_date;
    }

    private void UpdateAdapter(){
        getCurrentList();
        RecyclerView recyclerView2 =(RecyclerView)getView();
        recyclerView2.setAdapter(new CurrentRecyclerViewAdapter(DummyCurrents.ITEMS, mListener));
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
        UpdateAdapter();
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
        UpdateAdapter();
    }

    private Date start_date = null;
    private Date end_date = null;

    public Vector<tagItem<account>> getTag_accounts() {
        return tag_accounts;
    }

    public <Item> Vector<Item> getSelected_items(Vector<tagItem<Item>> tag_items) {
        Vector<Item> selected_items = new Vector<>(0);
        for(int i= 0; i < tag_items.size(); ++i){
            if(tag_items.elementAt(i).select){
                selected_items.add(tag_items.elementAt(i).item);
            }
        }
        return selected_items;
    }

    public void setTag_accounts(Vector<tagItem<account>> tag_accounts) {
        this.tag_accounts = tag_accounts;

        UpdateAdapter();
    }

    public Vector<tagItem<way>> getTag_ways() {
        return tag_ways;
    }

    public void setTag_ways(Vector<tagItem<way>> tag_ways) {
        this.tag_ways = tag_ways;
        UpdateAdapter();
    }

    private Vector<tagItem<account>> tag_accounts = new Vector<>(0);
    private Vector<tagItem<way>> tag_ways = new Vector<>(0);

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public current_list() {
    }

    private void QueryAccounts(){
        DBManager dbManager = new DBManager(getContext());
        Vector<account> _accounts = dbManager.get_allAccount();
        for (int i = 0; i < _accounts.size(); ++i) {
            tagItem<account> tag_account = new tagItem<>();
            tag_account.item = _accounts.elementAt(i);
            tag_account.select = true;
            tag_accounts.add(tag_account);
        }
    }

    private void QueryWays(){
        DBManager dbManager = new DBManager(getContext());
        Vector<way> _ways = dbManager.get_allWay();
        for (int i = 0; i < _ways.size(); ++i) {
            tagItem<way> tag_way= new tagItem<>();
            tag_way.item = _ways.elementAt(i);
            tag_way.select = true;
            tag_ways.add(tag_way);
        }
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static current_list newInstance(int columnCount) {
        current_list fragment = new current_list();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_current_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            DummyCurrents.clear();
            if(tag_accounts.isEmpty()){
                QueryAccounts();
            }

            if (tag_ways.isEmpty()){
                QueryWays();
            }

            if (start_date == null){
                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_MONTH, 1);
                start_date = new Date(c.getTimeInMillis());
            }

            if (end_date == null){
                end_date = new Date(Calendar.getInstance().getTimeInMillis());
            }

            getCurrentList();
            recyclerView.setAdapter(new CurrentRecyclerViewAdapter(DummyCurrents.ITEMS, mListener));


        }

        return view;
    }

    private void getCurrentList(){
        DBManager dbManager = new DBManager(getContext());
        Vector<current> currents = dbManager.get_currents(start_date, end_date, getSelected_items(tag_accounts), getSelected_items(tag_ways) );
        for (int i =0; i < currents.size(); ++i){
            current cu = currents.elementAt(i);
            if(cu.get_account().getName() == null){
                continue;
            }
            DummyItem item = new DummyItem(
                    cu.get_time(),
                    cu.get_way().get_name() + "\n" + (cu.get_way().get_type() == way.WAY_TYPE.INCOME?"+":"-") +Double.toString(cu.get_payment()),
                    cu.get_description(),
                    cu.get_account(),
                    cu.get_way());
            DummyCurrents.addItem(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onResume(){
        super.onResume();
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
            void onListFragmentInteraction(DummyItem item);
    }
}
