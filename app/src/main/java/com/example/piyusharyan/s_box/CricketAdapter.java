package com.example.piyusharyan.s_box;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.piyusharyan.s_box.MyAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CricketAdapter extends RecyclerView.Adapter<CricketAdapter.MyViewHolder>
{

    private final Context mContext;
    private List<String> mData1, mData2;

    public CricketAdapter(Context mContext, List<String> data1, List<String> data2) {
        this.mContext = mContext;
        if (data1 != null)
            mData1 = data1;
        else
            mData1 = new ArrayList<String>();

        if (data2 != null)
            mData2 = data2;
        else
            mData2 = new ArrayList<String>();
    }

    public void add(String s, int position) {
        position = position == -1 ? getItemCount() : position;
        mData1.add(position, s);

        notifyItemInserted(position);
    }

    public void remove(int position) {
        if (position < getItemCount()) {
            mData1.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.list_item1, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {
        myViewHolder.tv1.setText(mData1.get(position));
        myViewHolder.tv2.setText(mData2.get(position));
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                clickListener.onClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData1.size();
    }

    ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        public void onClick(View v, int pos);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv1;
        protected TextView tv2;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);

        }

    }
}