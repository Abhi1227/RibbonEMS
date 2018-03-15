package com.rbbn.ems.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.rbbn.ems.R;
import com.rbbn.ems.adapters.NodesRecyclerAdapter;
import com.rbbn.ems.model.Node;

import java.util.ArrayList;

/**
 * Created by nsoni on 3/13/2018.
 */

public class NodeInfoActivity extends AppCompatActivity {

    private TextView name, ip, version;
    private Node node;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_info);

        ip = (TextView) findViewById(R.id.emsIpTextView);
        version = (TextView) findViewById(R.id.emsVersionTextView);
        toolbar = (Toolbar) findViewById(R.id.nodesToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nodes List");
        getSupportActionBar().setIcon(R.drawable.ribbon_icon);
        toolbar.setNavigationIcon(R.drawable.back_arrow);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.nodesRecyclerView);
        NodesRecyclerAdapter adapter = new NodesRecyclerAdapter(getApplicationContext(), getData());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext()); // (Context context, int spanCount)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void getNodeDetailsFromServer() {
        String url = "http://10.54.9.18:8999/mobileems/nodeList";
    }

    public static ArrayList<Node> getData() {

        ArrayList<Node> dataList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {

            Node node = new Node();
            node.setName("Node " + i);
            node.setIpAddress("10.34.56.78");
            node.setType("SBC");
            node.setVersion("V10.00.11");
            node.setStatus("Online");
            dataList.add(node);
        }

        return dataList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.node_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String msg = "";

        switch (item.getItemId()) {

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

        Toast.makeText(this, msg + " clicked !", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }
}
