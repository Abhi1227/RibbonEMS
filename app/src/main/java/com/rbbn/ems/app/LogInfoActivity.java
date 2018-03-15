package com.rbbn.ems.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import com.rbbn.ems.R;
import com.rbbn.ems.adapters.TrapsRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by nsoni on 3/13/2018.
 */

public class LogInfoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_info);
        toolbar = (Toolbar) findViewById(R.id.logsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Logs");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.trapRecyclerView);
        TrapsRecyclerAdapter adapter = new TrapsRecyclerAdapter(getApplicationContext(), getTraps());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(getApplicationContext()); // (Context context, int spanCount)
        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);

        recyclerView.setItemAnimator(new DefaultItemAnimator()); // Even if we dont use it then also our items shows default animation. #Check Docs
    }
    public static ArrayList<String> getTraps() {

        ArrayList<String> dataList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {

            dataList.add("sonusTrapId: 123456, sonusTrapEventName:InsightTrap, sonusDeviceName: insight" + i);
        }

        return dataList;
    }
}
