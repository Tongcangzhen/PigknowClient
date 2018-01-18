package com.example.ldjg.pigknowclient;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ldjg.pigknowclient.DB.Record;
import com.example.ldjg.pigknowclient.ItemFragment.OnListFragmentInteractionListener;
import com.example.ldjg.pigknowclient.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Record> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<Record> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mDateView.setText(mValues.get(position).getUpLoadDate());
        holder.mNumView.setText(mValues.get(position).getNum()+"");
        int audit = mValues.get(position).getAudit();
        if (audit == 0) {
            holder.mAuditView.setText("未审核");
        } else if (audit == 1) {
            holder.mAuditView.setText("通过");
        } else {
            holder.mAuditView.setText("未通过");
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mAuditView;
        public final TextView mNumView;
        public final TextView mDateView;
        public Record mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mAuditView = (TextView) view.findViewById(R.id.audit);
            mNumView = (TextView) view.findViewById(R.id.num);
            mDateView = (TextView) view.findViewById(R.id.date);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNumView.getText() + "'";
        }
    }
}
