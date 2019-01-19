package com.example.schiver.sethings;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schiver.sethings.Model.ConfigData;
import com.example.schiver.sethings.Model.ConfigDevice;
import com.example.schiver.sethings.Model.ConfigStatus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ConfigAdapter extends RecyclerView.Adapter<ConfigAdapter.ConfigViewHolder> {
    private ArrayList<ConfigDevice> mConfigList;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    DatabaseReference dbRef2;

    private ArrayList<String> deviceEvent;
    private ArrayList<String> deviceCondition;
    private ArrayList<String> deviceAction;

    public static  class ConfigViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public CardView mCardView;
        public ConfigViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView1);
            mTextView2 = itemView.findViewById(R.id.textView2);
            mCardView  = itemView.findViewById(R.id.cardHolder);
        }
    }

    public ConfigAdapter(ArrayList<ConfigDevice> mConfigList){
        this.mConfigList = mConfigList;
    }

    @NonNull
    @Override
    public ConfigAdapter.ConfigViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.device_config_list,viewGroup,false);
        ConfigViewHolder cvh = new ConfigViewHolder(v);

        return  cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ConfigAdapter.ConfigViewHolder configViewHolder, int i) {
        final ConfigDevice currentItem = mConfigList.get(i);
        final String[] deviceEvent = new String[1];
        final String[] deviceCondition = new String[1];
        final String[] deviceAction = new String[1];
        configViewHolder.mImageView.setImageResource(currentItem.getmImageResource());
        configViewHolder.mTextView1.setText(currentItem.getmText1());
        configViewHolder.mTextView2.setText(currentItem.getmText2());
        configViewHolder.mCardView.setCardBackgroundColor(currentItem.getmColor());
        configViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDb = FirebaseDatabase.getInstance();
                dbRef = myDb.getReference("SeThings-Status");
                dbRef2 = myDb.getReference("SeThings-Control");
                int dataSensor = 0,status_lamp=0,status_plug=0,status_actuator=0;
                deviceEvent[0] = currentItem.getmId();
                if(currentItem.getmType().equals("Sensor")){
                    //Toast.makeText(v.getContext(), "Edit "+currentItem.getmType(), Toast.LENGTH_SHORT).show();
                    dataSensor = 25; status_lamp = 0; status_plug = 0; status_actuator=0;
                    deviceCondition[0] = Integer.toString(dataSensor);
                    deviceAction[0] = "AC_ON";
                }else if(currentItem.getmType().equals("Lamp")){
                    deviceCondition[0] = "NOW";
                    if(currentItem.getmText2().equals("Off")){
                        dataSensor = 0; status_lamp = 1; status_plug = 0; status_actuator=0;
                        deviceAction[0] = Integer.toString(status_lamp);
                    }else{
                        dataSensor = 0; status_lamp = 0; status_plug = 0; status_actuator=0;
                        deviceAction[0] = Integer.toString(status_lamp);
                    }
                }else if(currentItem.getmType().equals("Plug")){
                    deviceCondition[0] = "NOW";
                    if(currentItem.getmText2().equals("Off")){
                        dataSensor = 0; status_lamp = 0; status_plug = 1; status_actuator=0;
                        deviceAction[0] = Integer.toString(status_plug);
                    }else{
                        dataSensor = 0; status_lamp = 0; status_plug = 0; status_actuator=0;
                        deviceAction[0] = Integer.toString(status_plug);
                    }
                }
                Toast.makeText(v.getContext(), deviceEvent[0] +" "+ deviceCondition[0] +" "+ deviceAction[0], Toast.LENGTH_SHORT).show();
                final ConfigStatus myConfigStatus = new ConfigStatus(currentItem.getmId(),currentItem.getmName(),
                        currentItem.getmType(),status_plug,dataSensor,status_actuator,status_lamp);
                final ConfigData myConfigData = new ConfigData(currentItem.getmId(),deviceEvent[0],deviceCondition[0],deviceAction[0]);
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(currentItem.getmId()).exists()){
                            dbRef.child(currentItem.getmId()).setValue(myConfigStatus);
                        }else{

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dbRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(currentItem.getmId()).exists()){
                            dbRef2.child(currentItem.getmId()).setValue(myConfigData);
                        }else{

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //Toast.makeText(v.getContext(), "Edit "+currentItem.getmName(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mConfigList.size();
        //return 0;
    }


}
