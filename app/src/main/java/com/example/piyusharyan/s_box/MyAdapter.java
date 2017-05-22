package com.example.piyusharyan.s_box;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{

    private final Context mContext;
    private List<String> mData1;

    public MyAdapter(Context mContext, List<String> data1) {
        this.mContext = mContext;
        if (data1 != null)
            mData1 = data1;
        else
            mData1 = new ArrayList<String>();

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
        View itemView = inflater.inflate(R.layout.list_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, final int position) {
        Picasso.with(mContext).load(mData1.get(position)).resize(250,250).into(myViewHolder.img1);

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
        protected ImageView img1;
        public MyViewHolder(View itemView) {
            super(itemView);
            img1 = (ImageView) itemView.findViewById(R.id.img);

        }

    }



}