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
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.rbbn.ems.R;
import com.rbbn.ems.adapters.NodesRecyclerAdapter;
import com.rbbn.ems.model.Alarms;
import com.rbbn.ems.model.Node;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nsoni on 3/15/2018.
 */

public class NodeInfoActivity extends AppCompatActivity {

    private TextView name, ip, version;
    private Node node;
    private Toolbar toolbar;
    RequestQueue mQueue;
    ArrayList<Node> nodeList = new ArrayList<>();
    NodesRecyclerAdapter nodesRecyclerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_info);

        ip = (TextView) findViewById(R.id.emsIpTextView);
        version = (TextView) findViewById(R.id.emsVersionTextView);
        toolbar = (Toolbar) findViewById(R.id.nodesToolbar);
        mQueue = Volley.newRequestQueue(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nodes List");
        getSupportActionBar().setIcon(R.drawable.ribbon_icon);
//        toolbar.setNavigationIcon(R.drawable.back_arrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.nodesRecyclerView);
        nodesRecyclerAdapter = new NodesRecyclerAdapter(getApplicationContext(), nodeList);
        recyclerView.setAdapter(nodesRecyclerAdapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext()); // (Context context, int spanCount)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getNodeList();
    }



    private void getNodeList() {
        String url = "http://10.54.28.97:8999/mobileems/node?ip=10.54.28.97";
//        final ArrayList<Alarms> dataList = new ArrayList<>();
        final JsonArrayRequest request1 = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    nodeList.clear();
                    for (int j = 0; j < response.length(); j++) {

                        Node nodeObject = new Node();
                        JSONObject node = response.getJSONObject(j);
                        if(node != null) {
                            nodeObject.setName(node.getString("name"));
                            nodeObject.setType(node.getString("type"));
                            nodeObject.setIpAddress(node.getString("ip"));
                            nodeObject.setVersion(node.getString("version"));
                            nodeObject.setStatus(node.getString("enabled"));

                            nodeList.add(nodeObject);
                        }

                        Log.i("Navin","name = "+ response.length());

                    }
                    nodesRecyclerAdapter.setItem(nodeList);
                    nodesRecyclerAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(request1);
    }
@Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.node_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String msg = null;

        switch (item.getItemId()) {

            case R.id.node_speech:
                Intent i = new Intent(NodeInfoActivity.this, SpeechRecognitionActivity.class);
                i.putExtra("Activity Name",this.getClass().getSimpleName());
                startActivity(i);
                break;
            case R.id.refreshNodeList:
                getNodeList();
                break;
            case R.id.sbc:
                msg = getString(R.string.sbc);
                break;

            case R.id.insight:
                msg = getString(R.string.insight);
                break;

            case R.id.psx:
                msg = getString(R.string.psx);
                break;

            case R.id.gsx:
                msg = getString(R.string.gsx);
                break;

            case R.id.dsc:
                msg = getString(R.string.dsc);
                break;
            case R.id.sgx:
                msg = getString(R.string.sgx);
                break;
            case R.id.ads:
                msg = getString(R.string.ads);
                break;
            case R.id.asx:
                msg = getString(R.string.asx);
                break;
        }

        if(msg!=null) {
            Toast.makeText(this, msg + " clicked !", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
