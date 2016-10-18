package d.ql.account;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Vector;

/**
 * Created by ql on 16-5-20.
 */

public class CheckBoxListDlg<T> extends DialogFragment {

    private Vector<tagItem<T>> mValues;
    private OnListFragmentInteractionListener mListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.checkbox_list, container);

        if (view instanceof RecyclerView){
            RecyclerView rv = (RecyclerView)view;
            Context context = view.getContext();
            rv.setLayoutManager(new LinearLayoutManager(context));

            rv.setAdapter(new CheckBoxRVAdapter(mValues, mListener));
        }

        return view;
    }

    public void setValues(Vector<tagItem<T>> Values){
        mValues = Values;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }


    }

    //Object = T
    public interface OnListFragmentInteractionListener{
        // TODO: Update argument type and name
        void onListFragmentInteraction(Vector values);
        void OnCancel();
    }
}
