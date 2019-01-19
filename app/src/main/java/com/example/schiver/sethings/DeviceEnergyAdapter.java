package com.example.schiver.sethings;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schiver.sethings.Model.Device;

import java.util.ArrayList;

public class DeviceEnergyAdapter extends RecyclerView.Adapter<DeviceEnergyAdapter.DeviceEnergyViewHolder> {
    private ArrayList<Device> mDeviceList;
    Context myContext;
    public static class DeviceEnergyViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public ImageView mIconMenu;
        public TextView mTextView1;
        public TextView mTextView2;
        public CardView mCardView;
        public ImageView dotMenu;
        public DeviceEnergyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.DeviceIcon);
            mTextView1 = itemView.findViewById(R.id.DeviceName);
            mTextView2 = itemView.findViewById(R.id.DeviceType);
            mCardView  = itemView.findViewById(R.id.deviceCardHolder);
            mIconMenu = itemView.findViewById(R.id.optionMenu);
            dotMenu = itemView.findViewById(R.id.optionMenu);

        }
    }

    public DeviceEnergyAdapter(ArrayList<Device> mDeviceList , Context context) {
        this.mDeviceList = mDeviceList;
        this.myContext = context;
    }

    @NonNull
    @Override
    public DeviceEnergyAdapter.DeviceEnergyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.device_list,viewGroup,false);
        DeviceEnergyAdapter.DeviceEnergyViewHolder emvh = new DeviceEnergyViewHolder(v);
        return emvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final DeviceEnergyAdapter.DeviceEnergyViewHolder deviceEnergyViewHolder, int i) {
        final Device currentItem =  mDeviceList.get(i);
        deviceEnergyViewHolder.mImageView.setImageResource(currentItem.getmImageResource());
        deviceEnergyViewHolder.mTextView1.setText(currentItem.getmText1());
        deviceEnergyViewHolder.mTextView2.setText(currentItem.getmText2());
        deviceEnergyViewHolder.dotMenu.setVisibility(View.INVISIBLE);
        deviceEnergyViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent energyActivity = new Intent(myContext,EnergyView.class);
                String paramSend = null;
                energyActivity.putExtra("deviceID",currentItem.getmDeviceId());
                myContext.startActivity(energyActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }


}
