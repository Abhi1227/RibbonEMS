package com.rbbn.ems.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.rbbn.ems.R;
import com.rbbn.ems.adapters.TrapsRecyclerAdapter;
import com.rbbn.ems.model.Node;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nsoni on 3/15/2018.
 */

public class LogInfoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    ArrayList<String> logsList = new ArrayList<>();
    TrapsRecyclerAdapter trapsRecyclerAdapter;
    RequestQueue mQueue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_info);
        toolbar = (Toolbar) findViewById(R.id.logsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Logs");
        getSupportActionBar().setIcon(R.drawable.ribbon_icon);
//        toolbar.setNavigationIcon(R.drawable.back_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mQueue = Volley.newRequestQueue(this);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.trapRecyclerView);
        trapsRecyclerAdapter = new TrapsRecyclerAdapter(getApplicationContext(), logsList);
        recyclerView.setAdapter(trapsRecyclerAdapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext()); // (Context context, int spanCount)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Even if we dont use it then also our items shows default animation. #Check Docs
        getLogsList();
    }

@Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
	
    private void getLogsList() {
        Log.i("Navin", "name123123 = " );
        String url = "http://10.54.28.97:8999/mobileems/diag";
//        final ArrayList<Alarms> dataList = new ArrayList<>();
        final JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.i("Navin", "name123 = " + response.length());
                    logsList.clear();

                    for (int j = 1; j < response.length(); j++) {

                        JSONObject log = response.getJSONObject(j);
                        logsList.add(log.getString("data"));
                        Log.i("Navin", "name = " + response.length());

                    }
                    trapsRecyclerAdapter.setItem(logsList);
                    trapsRecyclerAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Navin", "error= ");
            }
        });
        mQueue.add(request1);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logs_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String msg = null;

        switch (item.getItemId()) {

            case R.id.search:

                break;

            case R.id.refreshLogsList:
                getLogsList();

                break;


        }


        return super.onOptionsItemSelected(item);
    }
}
