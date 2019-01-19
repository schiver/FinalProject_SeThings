package com.example.schiver.sethings;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.widget.Toast;

import com.example.schiver.sethings.Model.EnergyDetailUsage;
import com.example.schiver.sethings.Model.EnergyUsage;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EnergyView extends AppCompatActivity {
    LineChart mLineChart;
    ArrayList<Entry> yValues = new ArrayList<>();
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    String newString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_view);
        int currentOrientation = this.getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT){
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else{
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        /*if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("deviceID");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("deviceID");
        }*/
        mLineChart = (LineChart) findViewById(R.id.LineGraph);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        Date date = new Date();
        String dateNow = dateFormat.format(date);

        Bundle extras = getIntent().getExtras();
        newString= extras.getString("deviceID");
        myDb = FirebaseDatabase.getInstance();
        //Toast.makeText(EnergyView.this, "SeThings-Usage_Detail/"+newString+"/"+dateNow, Toast.LENGTH_SHORT).show();
        //dbRef = myDb.getReference("SeThings-Usage_Detail/"+newString+"/"+dateNow);
        dbRef = myDb.getReference("SeThings-Usage_Detail/device-01/15-01-2019/");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                yValues.clear();
                mLineChart.setDragEnabled(true);
                mLineChart.setScaleEnabled(false);

                int index = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    EnergyDetailUsage pointUsage = ds.getValue(EnergyDetailUsage.class);
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

    }
}
