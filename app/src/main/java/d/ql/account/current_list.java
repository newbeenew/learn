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
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import DBManager.DBManager;
import d.ql.account.dummy.DummyCurrents;
import d.ql.account.dummy.DummyCurrents.DummyItem;
import util.Util;

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

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public current_list() {
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
            getCurrentList();

            recyclerView.setAdapter(new CurrentRecyclerViewAdapter(DummyCurrents.ITEMS, mListener));
        }

        return view;
    }


    private void getCurrentList(){
        DBManager dbManager = new DBManager(getContext());
        Vector<current> currents = dbManager.get_currents();
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
