package com.rbbn.ems.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.rbbn.ems.R;

import java.util.List;

/**
 * Created by nsoni on 3/15/2018.
 */

public class TrapsRecyclerAdapter extends RecyclerView.Adapter<TrapsRecyclerAdapter.MyViewHolder> {
    List<String> mData;
    private LayoutInflater inflater;
    Context context;

    public TrapsRecyclerAdapter(Context context, List<String> data) {
        inflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trap_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String current = mData.get(position);
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void setItem(List<String> newData) {
        this.mData = newData;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView trapTextView;
        CardView trapCard;
        int position;
        String current;

        public MyViewHolder(View itemView) {
            super(itemView);
            trapTextView  = (TextView)  itemView.findViewById(R.id.trapTextView);
            trapCard = itemView.findViewById(R.id.trapCardView);
        }

        public void setData(String current, int position) {
            this.trapTextView.setText(current);
            this.position = position;
            this.current = current;
        }
    }
}
