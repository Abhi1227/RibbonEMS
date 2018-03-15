package com.rbbn.ems.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.rbbn.ems.R;
import com.rbbn.ems.model.Node;

import java.util.List;

/**
 * Created by navinsoni2 on 12/03/18.
 */

public class NodesRecyclerAdapter extends RecyclerView.Adapter<NodesRecyclerAdapter.MyViewHolder> {
    List<Node> mData;
    private LayoutInflater inflater;
    Context context;

    public NodesRecyclerAdapter(Context context, List<Node> data) {
        inflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.node_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Node current = mData.get(position);
        holder.setData(current, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nodeNameTextView, nodeTypeTextView, nodeIpTextView, nodeVersionTextView, nodeStatusTextView;
        CardView nodeCard;
        int position;
        Node current;

        public MyViewHolder(View itemView) {
            super(itemView);
            nodeNameTextView  = (TextView)  itemView.findViewById(R.id.nodeNameTextView);
            nodeTypeTextView  = (TextView)  itemView.findViewById(R.id.nodeTypeTextView);
            nodeIpTextView  = (TextView)  itemView.findViewById(R.id.nodeIpTextView);
            nodeVersionTextView  = (TextView)  itemView.findViewById(R.id.nodeVersionTextView);
            nodeStatusTextView  = (TextView)  itemView.findViewById(R.id.nodeStatusTextView);
            nodeCard = itemView.findViewById(R.id.nodeCardView);
        }

        public void setData(Node current, int position) {
            this.nodeNameTextView.setText(current.getName());
            this.nodeIpTextView.setText(current.getIpAddress());
            this.nodeTypeTextView.setText(current.getType());
            this.nodeVersionTextView.setText(current.getVersion());
            this.nodeStatusTextView.setText(current.getStatus());
//            if(current.getEventSeverity().contains("Critical")){
//                this.alarmCard.setCardBackgroundColor(Color.RED);
//            }else {
//                this.alarmCard.setCardBackgroundColor(Color.BLUE);
//            }
//            }
//            if(position %3 == 0) {
//                this.alarmCard.setCardBackgroundColor(Color.GREEN);
//            } else if(position %3 == 1) {
//                this.alarmCard.setCardBackgroundColor(Color.RED);
//            }else if(position %3 ==2){
//                this.alarmCard.setCardBackgroundColor(Color.BLUE);
//            }
            this.position = position;
            this.current = current;
        }
    }
}
