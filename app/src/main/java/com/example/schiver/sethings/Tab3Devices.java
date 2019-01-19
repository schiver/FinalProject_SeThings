package com.example.schiver.sethings;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schiver.sethings.Model.ConfigData;
import com.example.schiver.sethings.Model.ConfigDevice;
import com.example.schiver.sethings.Model.ConfigStatus;
import com.example.schiver.sethings.Model.Device;
import com.example.schiver.sethings.Model.DeviceData;
import com.example.schiver.sethings.Model.EnergyUsage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.series.DataPoint;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.sql.Time;
import java.util.ArrayList;

public class Tab3Devices extends Fragment {
    private RecyclerView mRecylerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    DatabaseReference dbRef2;
    DatabaseReference dbRef3;
    DatabaseReference dbRef4;

    ArrayList<Device> deviceContent = new ArrayList<>();
    Dialog myDialog;
    FloatingActionButton showPop;
    String [] DEVICE_TYPE = {"Sensor","Plug","Actuator","Lamp"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab3devices, container, false);
        showPop = (FloatingActionButton) rootView.findViewById(R.id.fab);
        showPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        myDialog = new Dialog(getContext());
        myDb = FirebaseDatabase.getInstance();

        mRecylerView = (RecyclerView) rootView.findViewById(R.id.DeviceListRecycler);
        mRecylerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity() , LinearLayoutManager.VERTICAL, false);
        mRecylerView.setLayoutManager(mLayoutManager);
        mAdapter = new DeviceAdapter(deviceContent);
        return rootView;
    }

    private void showDialog() {
        final EditText txtDeviceId,txtDeviceName;
        Button btnAdd;
        ImageView btnClose;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line,DEVICE_TYPE);
        myDialog.setContentView(R.layout.device_popup);
        final BetterSpinner betterSpinner = myDialog.findViewById(R.id.EdtDeviceType);
        txtDeviceId = (EditText) myDialog.findViewById(R.id.EdtDeviceID);
        txtDeviceName = (EditText) myDialog.findViewById(R.id.EdtDeviceName);
        btnAdd = (Button) myDialog.findViewById(R.id.BtnAddDevice);
        btnClose = (ImageView) myDialog.findViewById(R.id.CloseButton);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputDeviceId = txtDeviceId.getText().toString();
                String inputDeviceName = txtDeviceName.getText().toString();
                String inputDeviceType = betterSpinner.getText().toString();

                postDevice(inputDeviceId,inputDeviceName,inputDeviceType); // call function to post data

            }
        });
        betterSpinner.setAdapter(arrayAdapter);
        myDialog.show();
    }

    private void postDevice(String input1, String input2, String input3) {
        final DeviceData myDevice = new DeviceData(input1,input2,input3);
        dbRef2 = myDb.getReference("SeThings-Device");
        dbRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(myDevice.getDeviceID()).exists()){
                    Toast.makeText(getContext(), "Device ID arleady installed", Toast.LENGTH_SHORT).show();
                }else{
                    dbRef2.child(myDevice.getDeviceID()).setValue(myDevice);
                    myDialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbRef3 = myDb.getReference("SeThings-Status");
        final ConfigStatus myConfigStatus = new ConfigStatus(input1,input2,input3,0,0,0,0);
        dbRef3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(myConfigStatus.getDeviceId()).exists()){
                    //Toast.makeText(getContext(), "Device ID arleady installed", Toast.LENGTH_SHORT).show();
                }else{
                    dbRef3.child(myConfigStatus.getDeviceId()).setValue(myConfigStatus);
                    //myDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbRef4 = myDb.getReference("SeThings-Control");
        final ConfigData myConfigData = new ConfigData(input1,"OKE","OKE","OKE");
        dbRef4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(myConfigData.getDeviceId()).exists()){
                    //Toast.makeText(getContext(), "Device ID arleady installed", Toast.LENGTH_SHORT).show();
                }else{
                    dbRef4.child(myConfigData.getDeviceId()).setValue(myConfigData);
                    //myDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /*private void postDeviceControl(String input1){
        dbRef = myDb.getReference("SeThings-Control");
        final ConfigData myConfigData = new ConfigData(input1,"OKE","OKE","OKE");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(myConfigData.getDeviceId()).exists()){
                    //Toast.makeText(getContext(), "Device ID arleady installed", Toast.LENGTH_SHORT).show();
                }else{
                    dbRef.child(myConfigData.getDeviceId()).setValue(myConfigData);
                    //myDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    /*private void postDeviceStatus(String input1, String input2, String input3){
        dbRef = myDb.getReference("SeThings-Status");
        final ConfigStatus myConfigStatus = new ConfigStatus(input1,input2,input3,0,0,0,0);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(myConfigStatus.getDeviceId()).exists()){
                    //Toast.makeText(getContext(), "Device ID arleady installed", Toast.LENGTH_SHORT).show();
                }else{
                    dbRef.child(myConfigStatus.getDeviceId()).setValue(myConfigStatus);
                    //myDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/


    @Override
    public void onResume() {
        super.onResume();
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
