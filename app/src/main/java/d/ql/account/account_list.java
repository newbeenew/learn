package d.ql.account;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Date;
import java.util.Vector;

import DBManager.DBManager;
import d.ql.account.dummy.DummyAccounts;
import d.ql.account.dummy.DummyCurrents;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class account_list extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public account_list() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static account_list newInstance(int columnCount) {
        account_list fragment = new account_list();
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

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            DummyAccounts.clear();
            getAccountList();
            setHasOptionsMenu(true);

            recyclerView.setAdapter(new MyaccountRecyclerViewAdapter(DummyAccounts.ITEMS, mListener));
        }
        return view;
    }


    private  void getAccountList(){
        DBManager dbManager = new DBManager(getContext());
        Vector<account> _accounts = dbManager.get_allAccount();
        for (int i =0; i < _accounts.size(); ++i){
            account _ac = _accounts.elementAt(i);
            if(_ac.getName() == null){
                continue;
            }
            DummyAccounts.addItem(_ac);
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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.add(1,Menu.FIRST,Menu.FIRST,"add account");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case 1:
                add_account();
        }
        return true;
    }

    private final void add_account(){
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.account_dialog,  (ViewGroup)(getActivity().findViewById(R.id.account_dialog)));
        new AlertDialog.Builder(getContext())
                .setTitle("add account")
                .setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBManager dbManager = new DBManager(getContext());
                        account new_account = new account();
                        EditText et_name = (EditText)layout.findViewById(R.id.account_name);
                        new_account.setName(et_name.getText().toString());

                        EditText et_balance = (EditText)layout.findViewById(R.id.account_balance);
                        new_account.setBalance(Double.parseDouble(et_balance.getText().toString()));
                        dbManager.add_account(new_account);
                    }
                })
                .setNegativeButton("取消",null)
                .show();
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
        void onListFragmentInteraction(account item);
    }
}
