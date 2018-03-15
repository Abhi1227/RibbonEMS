package com.rbbn.ems.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView criticalCountTxtView, majorCountTxtView, minorCountTxtView, warningCountTxtView, infoCountTxtView;
    int criticalCount = 0, majorCount =0, minorCount = 0, warningCount = 0, infoCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_info);
        toolbar = (Toolbar) findViewById(R.id.alarmsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Alarms");
        getSupportActionBar().setIcon(R.drawable.ribbon_icon);
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        mQueue = Volley.newRequestQueue(this);
        recyclerView = (RecyclerView) findViewById(R.id.alarmRecyclerView);
        adapter = new AlarmsRecyclerAdapter(getApplicationContext(), dataList);
        recyclerView.setAdapter(adapter);
        criticalCountTxtView = (TextView) findViewById(R.id.criticalCount);
        majorCountTxtView = (TextView) findViewById(R.id.majorCount);
        minorCountTxtView = (TextView) findViewById(R.id.minorCount);
        warningCountTxtView = (TextView) findViewById(R.id.warningCount);
        infoCountTxtView = (TextView) findViewById(R.id.infoCount);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext()); // (Context context, int spanCount)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Even if we dont use it then also our items shows default animation. #Check Docs
//        jsonParse();
        jsonParse1();

    }



    private void jsonParse1() {
        String url = "http://10.54.9.18:8999/mobileems/alarmlist";
//        final ArrayList<Alarms> dataList = new ArrayList<>();
        final JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    dataList.clear();
                    criticalCount = 0;
                    majorCount = 0;
                    minorCount = 0;
                    warningCount = 0;
                    infoCount = 0;
                    for (int j = 0; j < response.length(); j++) {

                        Alarms alarmObject = new Alarms();
                        JSONObject alarm = response.getJSONObject(j);
                        alarmObject.setEventName(alarm.getString("Id"));
                        alarmObject.setType(alarm.getString("Type"));
                        alarmObject.setDevice(alarm.getString("Node"));
                        alarmObject.setEventSeverity(alarm.getString("Sev"));
                        switch (alarmObject.getEventSeverity()){
                            case "5" : criticalCount++;
                                break;
                            case "4" : majorCount++;
                                break;
                            case "3" : minorCount++;
                                break;
                            case "2" :warningCount++;
                                break;
                            case "1" :infoCount++;
                                break;
                            default:
                                break;
                        }
                        alarmObject.setDate(alarm.getString("time"));
                        alarmObject.setSummary(alarm.getString("Sum"));
                        dataList.add(alarmObject);
                        criticalCountTxtView.setText(criticalCount+"");
                        majorCountTxtView.setText(majorCount+"");
                        minorCountTxtView.setText(minorCount+"");
                        warningCountTxtView.setText(warningCount+"");
                        infoCountTxtView.setText(infoCount+"");

                    }
                    adapter.setItem(dataList);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alarm_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String msg = "";

        switch (item.getItemId()) {

            case R.id.refreshAlarmList:
                jsonParse1();

                break;


        }

        Toast.makeText(this, msg + " clicked !", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

}
