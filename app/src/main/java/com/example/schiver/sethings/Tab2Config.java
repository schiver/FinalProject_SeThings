package com.example.schiver.sethings;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.schiver.sethings.Model.ConfigDevice;
import com.example.schiver.sethings.Model.ConfigStatus;
import com.example.schiver.sethings.Model.Device;
import com.example.schiver.sethings.Model.DeviceData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Tab2Config extends Fragment {
    FirebaseDatabase myDb;
    DatabaseReference dbRef;

    //ArrayList<Device> deviceContent = new ArrayList<>();
    ArrayList<ConfigDevice> mConfigDevice = new ArrayList<>();
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2config, container, false);

        /*mConfigDevice.add(new ConfigDevice(R.drawable.ic_thermometer,"Temp","23",Color.rgb(1, 163, 164)));
        mConfigDevice.add(new ConfigDevice(R.drawable.ic_plug,"Plug","Line 4",Color.rgb(131, 149, 167)));
        mConfigDevice.add(new ConfigDevice(R.drawable.ic_lamp,"Lamp","Line 6",Color.rgb(84, 160, 255)));
        mConfigDevice.add(new ConfigDevice(R.drawable.ic_lamp,"Lamp","Line 6",Color.rgb(84, 160, 255)));
        mConfigDevice.add(new ConfigDevice(R.drawable.ic_lamp,"Lamp","Line 6",Color.rgb(84, 160, 255)));
        mConfigDevice.add(new ConfigDevice(R.drawable.ic_lamp,"Lamp","Line 6",Color.rgb(84, 160, 255)));*/
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.configRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity() , 3);
        mAdapter = new ConfigAdapter(mConfigDevice);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Status");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int index = 1 , colorBg = 0, icon = 0;
                int actuator,lamp;
                float energy,sensor;
                String dataShown = null;
                mConfigDevice.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ConfigStatus myConfigStatus = ds.getValue(ConfigStatus.class);
                    if(myConfigStatus.getDeviceType().equals("Sensor")){
                        colorBg = Color.rgb(1, 163, 164);
                        icon = R.drawable.ic_thermometer;
                        dataShown = Float.toString(myConfigStatus.getSensorValue());
                    }else if(myConfigStatus.getDeviceType().equals("Lamp")){
                        colorBg = Color.rgb(84, 160, 255);
                        icon = R.drawable.ic_lamp;
                        if(myConfigStatus.getLampStatus() == 1){
                            dataShown = "On";
                        }else{
                            dataShown = "Off";
                        }
                    }else if(myConfigStatus.getDeviceType().equals("Plug")){
                        colorBg = Color.rgb(131, 149, 167);
                        icon = R.drawable.ic_plug;
                        if(myConfigStatus.getEnergyUsage() == 1){
                            dataShown = "On";
                        }else{
                            dataShown = "Off";
                        }
                    }
                    mConfigDevice.add(new ConfigDevice(icon,myConfigStatus.getDeviceName(),dataShown,colorBg,
                            myConfigStatus.getDeviceId(),myConfigStatus.getDeviceName(),myConfigStatus.getDeviceType()));
                    index++;
                }
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
