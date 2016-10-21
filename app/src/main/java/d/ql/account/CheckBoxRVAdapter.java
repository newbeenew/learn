package d.ql.account;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.List;
import java.util.Vector;

public class CheckBoxRVAdapter<T> extends RecyclerView.Adapter<CheckBoxRVAdapter.ViewHolder> {
    private final Vector<tagItem<T>> mValues;
    private final CheckBoxListDlg.OnListFragmentInteractionListener mListener;
    public CheckBoxRVAdapter(Vector<tagItem<T>> values, CheckBoxListDlg.OnListFragmentInteractionListener listener){
        mValues = values;
        mListener = listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        if(viewType == FOOT_VIEW){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkboc_list_foot, parent, false);
        }else if(viewType == BODY_VIEW){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkbox, parent ,false);
        }
        return new ViewHolder(view ,viewType);
    }

    public void onBindViewHolder(CheckBoxRVAdapter.ViewHolder holder, int position) {
        View view;
        if (position == mValues.size()) {
            Button cancel = (Button)holder.mView.findViewById(R.id.check_box_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.OnCancel();
                }
            });
            Button ok = (Button)holder.mView.findViewById(R.id.check_box_ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onListFragmentInteraction(mValues);
                    mListener.OnCancel();
                }
            });
        }
        else {
            CheckBox checkBox = (CheckBox)holder.mView.findViewById(R.id.checkbox);
            final tagItem<T> tagItem = mValues.get(position);
            holder.mItem = tagItem.item;
            checkBox.setText(holder.mItem.toString());
            checkBox.setChecked(tagItem.select);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox  checkBox = (CheckBox)v;
                    tagItem.select = !tagItem.select;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mValues.size()) {
            return FOOT_VIEW;
        }else{
            return BODY_VIEW ;
        }
    }


    private final int HEAD_VIEW = 0;
    private final int BODY_VIEW = 1;
    private final int FOOT_VIEW = 2;

    public int getItemCount() {
        return mValues.size() + 1;//and list foot
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public Object mItem;

        public ViewHolder(View view,int ViewType) {
            super(view);
            mView = view;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
 }
