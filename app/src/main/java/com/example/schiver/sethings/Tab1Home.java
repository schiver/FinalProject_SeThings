package com.example.schiver.sethings;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schiver.sethings.Model.ConfigStatus;
import com.example.schiver.sethings.Model.EnergyUsage;
import com.example.schiver.sethings.Model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Tab1Home extends Fragment  {
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    DatabaseReference dbRef2;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    LineChart mLineChart;
    ArrayList<String> dataTest = new ArrayList<String>();
    ArrayList<Entry> yValues = new ArrayList<>();
    //GraphView myGraph;
    LineGraphSeries series;
    ArrayList<ExampleItem> exampleList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab1home, container, false);

        CardView cardView1 = (CardView) rootView.findViewById(R.id.EnergyCard);
        CardView cardView2 = (CardView) rootView.findViewById(R.id.DeviceCard);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent energyIntent = new Intent(rootView.getContext() , EnergyMonitoring.class);
                startActivity(energyIntent);
            }
        });
        mLineChart = (LineChart) rootView.findViewById(R.id.LineGraph);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.deviceRecycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity() , LinearLayoutManager.HORIZONTAL, false);
        mAdapter = new ExampleAdapter(exampleList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Usage");
        dbRef2 = myDb.getReference("SeThings-Status");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                yValues.clear();
                mLineChart.setDragEnabled(true);
                mLineChart.setScaleEnabled(false);
                DataPoint[] dataUsage = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    EnergyUsage pointUsage = ds.getValue(EnergyUsage.class);
                    yValues.add(new Entry(index,pointUsage.getUsage()));
                    index++;
                }
                LineDataSet set1 = new LineDataSet(yValues,"Energy Usage");
                set1.setFillAlpha(110);
                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);
                LineData data = new LineData(dataSets);
                mLineChart.setData(data);
                mLineChart.notifyDataSetChanged();
                mLineChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dbRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int index = 1 , colorBg = 0, icon = 0;
                int actuator,lamp;
                float energy,sensor;
                String dataShown = null;
                exampleList.clear();
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
                    exampleList.add(new ExampleItem(icon,myConfigStatus.getDeviceName(),dataShown,colorBg));
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
