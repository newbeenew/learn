package d.ql.account;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import d.ql.account.current_list.OnListFragmentInteractionListener;
import d.ql.account.dummy.DummyCurrents.DummyItem;
import util.Util;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CurrentRecyclerViewAdapter extends RecyclerView.Adapter<CurrentRecyclerViewAdapter.ViewHolder> {

    private List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Date start_date;
    private Date end_date;

    public void SetDate(Date start, Date end){
        start_date = start;
        end_date = end;
    }

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

            TextView startDate = (TextView)view.findViewById(R.id.current_start_date);
            startDate.setText(Util.ConvertToDateString(start_date));

            TextView endDate = (TextView)view.findViewById(R.id.current_end_date);
            endDate.setText(Util.ConvertToDateString(end_date));

            TextView incomes = (TextView)view.findViewById(R.id.incomes);
            incomes.setText(Double.toString(CalculateTotal(way.WAY_TYPE.INCOME)));

            TextView outgos  = (TextView)view.findViewById(R.id.outgos);
            outgos.setText(Double.toString(CalculateTotal(way.WAY_TYPE.OUTGO)));
        }

        return new ViewHolder(view,viewType);
    }

    public  void resetData(List<DummyItem> Values){
        mValues = Values;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder.ViewType == HEAD_VIEW){
            return;
        }

        int index = position -1;
        holder.mItem = mValues.get(index);
        holder.mTimeView.setText(mValues.get(index).GetTime());
        holder.mContentView.setText(mValues.get(index).toString());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onClickItem(holder.mItem);
                    holder.mContentView.setText(holder.mItem.toString());
                    holder.mTimeView.setText(holder.mItem.GetTime());
                }
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (null != mListener){
                    mListener.onLongClickItem(holder.mItem);
                }
                return true;
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

    public Double CalculateTotal(way.WAY_TYPE type){
        double totals = 0;
        for (DummyItem item: mValues
             ) {
            if (item._current.get_way().get_type() == type){
                totals += item._current.get_payment();
            }
        }
        return Util.ChangeDoubleRecision(totals, 2);
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
