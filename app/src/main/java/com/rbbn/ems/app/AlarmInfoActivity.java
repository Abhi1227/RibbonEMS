package com.rbbn.ems.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rbbn.ems.R;
import com.rbbn.ems.adapters.AlarmsRecyclerAdapter;
import com.rbbn.ems.model.Alarms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nsoni on 3/13/2018.
 */

public class AlarmInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    RequestQueue mQueue;
    ArrayList<Alarms> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    AlarmsRecyclerAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_info);
        toolbar = (Toolbar) findViewById(R.id.alarmsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Alarms");
        mQueue = Volley.newRequestQueue(this);
        recyclerView = (RecyclerView) findViewById(R.id.alarmRecyclerView);
        adapter = new AlarmsRecyclerAdapter(getApplicationContext(),dataList);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext()); // (Context context, int spanCount)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Even if we dont use it then also our items shows default animation. #Check Docs
        jsonParse();
//        jsonParse1();
    }

    private void jsonParse() {
        String url = "https://api.myjson.com/bins/iiqat";
//        final ArrayList<Alarms> dataList = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("alarms");

                    for(int i =0 ; i<jsonArray.length(); i++){
                        Alarms alarmObject = new Alarms();
                        JSONObject alarm = jsonArray.getJSONObject(i);
                        alarmObject.setEventName(alarm.getString("eventName"));
                        alarmObject.setType(alarm.getString("type"));
                        alarmObject.setDevice(alarm.getString("device"));
                        alarmObject.setEventSeverity(alarm.getString("eventSeverity"));
                        alarmObject.setDate(alarm.getString("date"));
                        alarmObject.setSummary(alarm.getString("summary"));
                        dataList.add(alarmObject);

                        Log.i("Navin","dataList = " + dataList.size());
                    }
                    adapter.setItem(dataList);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request);
    }

//    public static ArrayList<Alarms> getData() {
//
//        ArrayList<Alarms> dataList = new ArrayList<>();
//
//        for (int i = 0; i < 20; i++) {
//
//            Alarms alarms = new Alarms();
//            alarms.setEventName("sonusCpEventLogFileDebugLevelInfoNotification " + i);
//            alarms.setType("Insight" + i);
//            alarms.setDevice("localhost " + i);
//            if (i < 5) {
//                alarms.setEventSeverity("Critical");
//            } else {
//                alarms.setEventSeverity("Major");
//            }
//
//
//            dataList.add(alarms);
//        }
//
//        return dataList;
//    }

    private void jsonParse1() {
        String url = "http://10.54.9.18:8999/mobileems/alarmlist";
//        final ArrayList<Alarms> dataList = new ArrayList<>();
        final JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                    for(int j =0 ;j < response.length() ; j++){
                        try {
                            JSONObject obj = response.getJSONObject(j);
                            Log.i("Navin","newResponse = " + obj.getString("Id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    adapter.setItem(dataList);
                    adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request1);
    }

}
