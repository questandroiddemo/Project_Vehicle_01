package com.example.project_vehicle_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import Common.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapter;
    IMyAidlInterface iMyAidlInterface;
    Boolean connected=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout=findViewById(R.id.tab_layout);
        pager2=findViewById(R.id.view_pager2);


        FragmentManager fm=getSupportFragmentManager();
        adapter= new FragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);



        tabLayout.addTab(tabLayout.newTab().setText("Control"));
        tabLayout.addTab(tabLayout.newTab().setText("Settings"));



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        TabLayout.Tab tab= tabLayout.getTabAt(1);
        tab.select();

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        Intent intent = new Intent("com.example.project_vehicle_service_01.AIDL");

        intent.setClassName("com.example.project_vehicle_service_01",
                "com.example.project_vehicle_service_01.MyService");
        if(getBaseContext().getApplicationContext().bindService(intent, serviceCon, Context.BIND_AUTO_CREATE)){
            connected=true;
            Toast.makeText(getApplicationContext(), "BindServiceSuccess", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(getApplicationContext(), "BindServiceFailed", Toast.LENGTH_SHORT).show();


}




    private final ServiceConnection serviceCon=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };




}
