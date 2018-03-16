package com.rbbn.ems.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rbbn.ems.R;
import com.rbbn.ems.model.Alarms;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.TransitionManager;

import java.util.List;

/**
 * Created by nsoni on 3/15/2018.
 */

public class AlarmsRecyclerAdapter extends RecyclerView.Adapter<AlarmsRecyclerAdapter.MyViewHolder> {
    List<Alarms> mData;

    private LayoutInflater inflater;
//    String [] bgColorsArray = {"#FD7879","#C960E3","#60B6E3","#81FCCB","#7E76FA"};
//    String [] bgColorsArray = {"#FD8F53","#E33F58","#3F42E3","#5CD1FC","#CB51FA"}; //tes
    String [] bgColorsArray = {"#d9534f","#f0ad4e","#FFF672","#5bc0de","#5cb85c"};
//    String [] bgColorsArray = {"#FFEBBA","#E8B7AA","#FCC7FF","#AAAEE8","#CAFFFC"};
//    String [] bgColorsArray = {"#FF5457","#E8B7AA","#FCC7FF","#AAAEE8","#CAFFFC"};



    String whiteColor = "#ffffff";
    Context context;

    public AlarmsRecyclerAdapter(Context context, List<Alarms> data) {
        inflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.alarm_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Alarms current = mData.get(position);
        holder.setData(current, position);
        holder.hiddenAlarmLayout.setVisibility(View.GONE);

        holder.alarmCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean shouldExpand = holder.hiddenAlarmLayout.getVisibility() == View.GONE;

                ChangeBounds transition = new ChangeBounds();
                transition.setDuration(200);

                if (shouldExpand) {
                    holder.hiddenAlarmLayout.setVisibility(View.VISIBLE);
                    holder.toggleImage.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                } else {
                    holder.hiddenAlarmLayout.setVisibility(View.GONE);
                    holder.toggleImage.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                }

                TransitionManager.beginDelayedTransition(holder.alarmCard, transition);
                holder.itemView.setActivated(shouldExpand);

            }
        });
        holder.sendAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String alarmMessage = current.getEventName() + "\n" + "Type : " +current.getType() + "\n" + "Device Name: "+current.getDevice() + "\n" + "Severity : " + current.getEventSeverity() + "\n" + "Summary : " + current.getSummary();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                sendIntent.putExtra(Intent.EXTRA_TEXT, alarmMessage);
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItem(List<Alarms> newData) {
        this.mData = newData;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, type, device, date, summary;
        TextView typeLabel, deviceLabel, dateLabel, summaryLabel;
        RelativeLayout alarmCard;
        LinearLayout hiddenAlarmLayout;
        Button sendAlarmButton;
        ImageView toggleImage;
        int position;
        Alarms current;

        public MyViewHolder(View itemView) {
            super(itemView);
            eventName  = (TextView)  itemView.findViewById(R.id.eventNameTxtView);
            type  = (TextView)  itemView.findViewById(R.id.typeTxtView);
            device  = (TextView)  itemView.findViewById(R.id.deviceTxtView);
            date = (TextView)itemView.findViewById(R.id.dateTxtView);
            summary = (TextView)itemView.findViewById(R.id.summaryTxtView);

            deviceLabel = (TextView)itemView.findViewById(R.id.deviceLabel);
            typeLabel = (TextView)itemView.findViewById(R.id.typeLabel);
            summaryLabel = (TextView)itemView.findViewById(R.id.summaryLabel);
            dateLabel = (TextView)itemView.findViewById(R.id.dateLabel);

            toggleImage = itemView.findViewById(R.id.toggle);
            alarmCard = itemView.findViewById(R.id.alarmCardView);
            sendAlarmButton = itemView.findViewById(R.id.sendAlarmButton);
            hiddenAlarmLayout = itemView.findViewById(R.id.hiddenAlarmLayout);
        }

        public void setData(Alarms current, int position) {

            this.eventName.setText(current.getEventName());
            this.type.setText(current.getType());
            this.device.setText(current.getDevice());
            this.date.setText(current.getDate());
            this.summary.setText(current.getSummary());

//                this.alarmCard.setCardBackgroundColor(Color.parseColor(bgColorsArray[random]));
            switch (current.getEventSeverity()){
                case "5" : this.alarmCard.setBackground(ContextCompat.getDrawable(context,R.drawable.critical_bg));
                    eventName.setTextColor(Color.parseColor(whiteColor));
                    type.setTextColor(Color.parseColor(whiteColor));
                    device.setTextColor(Color.parseColor(whiteColor));
                    date.setTextColor(Color.parseColor(whiteColor));
                    summary.setTextColor(Color.parseColor(whiteColor));
                    typeLabel.setTextColor(Color.parseColor(whiteColor));
                    deviceLabel.setTextColor(Color.parseColor(whiteColor));
                    dateLabel.setTextColor(Color.parseColor(whiteColor));
                    summaryLabel.setTextColor(Color.parseColor(whiteColor));
                    sendAlarmButton.setTextColor(Color.parseColor(whiteColor));
                break;
                case "4" : this.alarmCard.setBackground(ContextCompat.getDrawable(context,R.drawable.major_bg));
                    break;
                case "3" : this.alarmCard.setBackground(ContextCompat.getDrawable(context,R.drawable.minor_bg));
                    break;
                case "2" : this.alarmCard.setBackground(ContextCompat.getDrawable(context,R.drawable.warning_bg));
                    break;
                case "1" : this.alarmCard.setBackground(ContextCompat.getDrawable(context,R.drawable.info_bg));
                    break;
                default:
                    break;
            }
            this.position = position;
            this.current = current;
        }
    }
}