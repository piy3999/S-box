package com.example.piyusharyan.s_box;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piyusharyan.s_box.MyAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
{

    private final Context mContext;
    private List<String> mData1, mData2;
    private List<Integer> mData3;
    private List<String> mData4;
    public HomeAdapter(Context mContext, List<String> data1, List<String> data2,List<Integer> data3,List<String > data4) {
        this.mContext = mContext;
        if (data1 != null)
            mData1 = data1;
        else
            mData1 = new ArrayList<String>();

        if (data2 != null)
            mData2 = data2;
        else
            mData2 = new ArrayList<String>();

        if(data3!=null)
            mData3 = data3;
        else
            mData3 = new ArrayList<Integer>();


        if(data4!=null)
            mData4 = data4;
        else
            mData4 = new ArrayList<String>();
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
        View itemView = inflater.inflate(R.layout.list_item2, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
       Picasso.with(mContext).load(mData1.get(position)).memoryPolicy(MemoryPolicy.NO_CACHE).into(myViewHolder.tv1);

        myViewHolder.tv2.setText(mData2.get(position));
//        myViewHolder.cnt.setText(mData3.get(position)+"");

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                clickListener.onClick(v, position);
            }
        });



        myViewHolder.tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1Listener.onClick(v,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData1.size();
    }

    ClickListener clickListener;

    C1Listener c1Listener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener= clickListener;
    }

    public void setC1Listener(C1Listener c1Listener)
    {
        this.c1Listener= c1Listener;
    }




    public interface C1Listener{
        public void onClick(View v,int pos);
    }

    public interface ClickListener {
        public void onClick(View v, int pos);

    }


    public interface LikClickListener {
        public void onClick(View v, int pos);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        protected ImageView tv1;
        protected TextView tv2;
        protected Button tv3;
        protected Button tv4;
        protected TextView cnt;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv1 = (ImageView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
            tv4 = (Button) itemView.findViewById(R.id.suggest);




        }

    }
}