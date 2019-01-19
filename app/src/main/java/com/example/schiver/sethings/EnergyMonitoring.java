package com.example.schiver.sethings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.schiver.sethings.Model.Device;
import com.example.schiver.sethings.Model.DeviceData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EnergyMonitoring extends AppCompatActivity {
    Toolbar myToolbar;
    private RecyclerView mRecylerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Device> deviceContent = new ArrayList<>();
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_monitoring);
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Energy Monitoring");
        myToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(EnergyMonitoring.this,Home.class);
                homeIntent.putExtra("PageNumber","0");
                startActivity(homeIntent);
            }
        });

        mRecylerView = (RecyclerView) findViewById(R.id.EnergyRecycler);
        mRecylerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL, false);
        mRecylerView.setLayoutManager(mLayoutManager);
        mAdapter = new DeviceEnergyAdapter(deviceContent,this);
    }

    @Override
    public void onResume() {
        super.onResume();
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Device");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int index = 1;
                deviceContent.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    DeviceData myDevice =  ds.getValue(DeviceData.class);
                    deviceContent.add(new Device(R.drawable.ic_plug,myDevice.getDeviceName(),myDevice.getDeviceType(),Color.rgb(131, 149, 167),myDevice.getDeviceID()));
                    index++;
                }
                mRecylerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
