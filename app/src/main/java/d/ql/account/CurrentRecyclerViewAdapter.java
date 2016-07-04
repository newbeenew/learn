package d.ql.account;

import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import d.ql.account.current_list.OnListFragmentInteractionListener;
import d.ql.account.dummy.DummyCurrents.DummyItem;
import util.Util;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CurrentRecyclerViewAdapter extends RecyclerView.Adapter<CurrentRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public CurrentRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if (viewType == BODY_VIEW) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_current, parent, false);
        }
        else  {
            view =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.current_list_header, parent, false);

            final Calendar c = Calendar.getInstance();
            TextView startDate = (TextView)view.findViewById(R.id.current_start_date);
            startDate.setText(Integer.toString(c.get(Calendar.YEAR)) + "-" + Integer.toString(c.get(Calendar.MONTH) + 1) + "-" + "1");

            TextView endDate = (TextView)view.findViewById(R.id.current_end_date);
            endDate.setText(Util.ConvertToDateString(c.getTime()));
        }

        return new ViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder.ViewType == HEAD_VIEW){
            return;
        }
        holder.mItem = mValues.get(position);
        holder.mTimeView.setText(mValues.get(position).GetTime());
        holder.mContentView.setText(mValues.get(position).toString());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    holder.mContentView.setText(holder.mItem.toString());
                    holder.mTimeView.setText(holder.mItem.GetTime());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_VIEW;
        }else{
            return BODY_VIEW ;
        }
    }

    private final int HEAD_VIEW = 0;
    private final int BODY_VIEW = 1;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTimeView;
        public final TextView mContentView;
        public final int ViewType;
        public DummyItem mItem;

        public ViewHolder(View view,int ViewType) {
            super(view);
            mView = view;
            mTimeView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            this.ViewType = ViewType;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
