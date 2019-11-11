package com.example.asnaui.iemmis;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.asnaui.iemmis.Fragments.About;
import com.example.asnaui.iemmis.Fragments.JobReport;
import com.example.asnaui.iemmis.Fragments.ServiceRequest;
import com.example.asnaui.iemmis.Fragments.Settings;
import com.example.asnaui.iemmis.Model.Logs;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    public ServiceRequest serviceRequest = new ServiceRequest();
    JobReport jobReport = new JobReport();
    About about = new About();
    Settings settings = new Settings();
    FragmentTransaction ft;
    FragmentManager fm;

    public static ArrayList<Logs> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation);

        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        navigationView.addHeaderView(getDrawerHeader());
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.content, serviceRequest).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.START)) {
                    drawerLayout.closeDrawer(Gravity.START);
                } else {
                    drawerLayout.openDrawer(Gravity.START);
                }
                break;
            case R.id.add:
                serviceRequest.requestFormDialog(null, "ADD", 0).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.request:
                toolbar.setTitle("Request");
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.content, serviceRequest).commit();
                break;
            case R.id.view:
                toolbar.setTitle("Report");
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.content, jobReport).commit();
                break;
            case R.id.about:
                toolbar.setTitle("About");
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.content, about).commit();
                break;
            case R.id.settings:
                toolbar.setTitle("Settings");
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.content, about).commit();
                break;
        }
        drawerLayout.closeDrawer(Gravity.START);
        return false;
    }

    public View getDrawerHeader() {
        View view = LayoutInflater.from(this).inflate(R.layout.drawer_header, null, false);
        return view;
    }

}
