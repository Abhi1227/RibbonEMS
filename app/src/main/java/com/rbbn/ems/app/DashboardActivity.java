package com.rbbn.ems.app;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rbbn.ems.R;


/**
 * Created by nsoni on 3/13/2018.
 */

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.dashboardToolbar);
        setSupportActionBar(toolbar);
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");
        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setIcon(R.drawable.ribbon_icon);
        toolbar.setNavigationIcon(R.drawable.back_arrow);

        TextView node_icon = (TextView) findViewById(R.id.node_icon);
        TextView fm_icon = (TextView) findViewById(R.id.fm_icon);
        TextView logging_icon = (TextView) findViewById(R.id.logging_icon);
        TextView performance_icon = (TextView) findViewById(R.id.performance_icon);
        TextView license_icon = (TextView) findViewById(R.id.license_icon);
        TextView users_icon = (TextView) findViewById(R.id.users_icon);
        TextView cloud_icon = (TextView) findViewById(R.id.cloud_icon);
        TextView settings_cog_icon = (TextView) findViewById(R.id.settings_cog_icon);

        LinearLayout nodeLayout = (LinearLayout) findViewById(R.id.node_layout);
        LinearLayout fault_layout = (LinearLayout) findViewById(R.id.fault_layout);
        LinearLayout pm_layout = (LinearLayout) findViewById(R.id.pm_layout);
        LinearLayout cloud_layout = (LinearLayout) findViewById(R.id.cloud_layout);
        LinearLayout license_layout = (LinearLayout) findViewById(R.id.license_layout);
        LinearLayout user_layout = (LinearLayout) findViewById(R.id.user_layout);
        LinearLayout logging_layout = (LinearLayout) findViewById(R.id.logging_layout);
        LinearLayout setting_layout = (LinearLayout) findViewById(R.id.setting_layout);

        nodeLayout.setOnClickListener(this);
        fault_layout.setOnClickListener(this);
        pm_layout.setOnClickListener(this);
        cloud_layout.setOnClickListener(this);
        license_layout.setOnClickListener(this);
        user_layout.setOnClickListener(this);
        logging_layout.setOnClickListener(this);
        setting_layout.setOnClickListener(this);


        node_icon.setTypeface(fontAwesomeFont);
        fm_icon.setTypeface(fontAwesomeFont);
        logging_icon.setTypeface(fontAwesomeFont);
        performance_icon.setTypeface(fontAwesomeFont);
        license_icon.setTypeface(fontAwesomeFont);
        users_icon.setTypeface(fontAwesomeFont);
        cloud_icon.setTypeface(fontAwesomeFont);
        settings_cog_icon.setTypeface(fontAwesomeFont);
    }

    @Override
    public void onClick(View view) {
        Intent i = null;

        switch (view.getId()) {
            case R.id.node_layout:
                Toast.makeText(this, "Node Clicked", Toast.LENGTH_SHORT).show();
                i = new Intent(DashboardActivity.this, NodeInfoActivity.class);
                startActivity(i);
                break;
            case R.id.fault_layout:
                Toast.makeText(this, "Fault Clicked", Toast.LENGTH_SHORT).show();
                i = new Intent(DashboardActivity.this, AlarmInfoActivity.class);
                startActivity(i);
                break;
            case R.id.pm_layout:
                Toast.makeText(this, "PM Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cloud_layout:
                Toast.makeText(this, "Cloud Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.license_layout:
                Toast.makeText(this, "License Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.user_layout:
                Toast.makeText(this, "User Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logging_layout:
                Toast.makeText(this, "Logging Clicked", Toast.LENGTH_SHORT).show();
                i = new Intent(DashboardActivity.this, LogInfoActivity.class);
                startActivity(i);
                break;
            case R.id.setting_layout:
                Toast.makeText(this, "Setting Clicked", Toast.LENGTH_SHORT).show();
                break;

        }

    }
}
