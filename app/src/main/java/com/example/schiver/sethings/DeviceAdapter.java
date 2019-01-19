package com.example.schiver.sethings;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schiver.sethings.Model.ConfigData;
import com.example.schiver.sethings.Model.ConfigStatus;
import com.example.schiver.sethings.Model.Device;
import com.example.schiver.sethings.Model.DeviceData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {
    private ArrayList<Device> mDeviceList;
    public Dialog myDialog;
    public Dialog editDialog;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    DatabaseReference dbRef2;
    DatabaseReference dbRef3;
    DatabaseReference dbRef4;
    String [] DEVICE_TYPE = {"Sensor","Plug","Actuator","Lamp"};
    public static class DeviceViewHolder extends RecyclerView.ViewHolder{

            public ImageView mImageView;
            public ImageView mIconMenu;
            public TextView mTextView1;
            public TextView mTextView2;
            public CardView mCardView;

            public DeviceViewHolder(@NonNull View itemView) {
                super(itemView);
                mImageView = itemView.findViewById(R.id.DeviceIcon);
                mTextView1 = itemView.findViewById(R.id.DeviceName);
                mTextView2 = itemView.findViewById(R.id.DeviceType);
                mCardView  = itemView.findViewById(R.id.deviceCardHolder);
                mIconMenu = itemView.findViewById(R.id.optionMenu);
            }
    }

    public DeviceAdapter(ArrayList<Device> mDeviceList) {
        this.mDeviceList = mDeviceList;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.device_list,viewGroup,false);
        DeviceViewHolder dvh = new DeviceViewHolder(v);
        myDialog = new Dialog(v.getContext());
        editDialog = new Dialog(v.getContext());
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Device");
        dbRef3 = myDb.getReference("SeThings-Status");
        dbRef4 = myDb.getReference("SeThings-Control");
        return dvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final DeviceViewHolder deviceViewHolder, int i) {
        final Device currentItem =  mDeviceList.get(i);
        deviceViewHolder.mImageView.setImageResource(currentItem.getmImageResource());
        deviceViewHolder.mTextView1.setText(currentItem.getmText1());
        deviceViewHolder.mTextView2.setText(currentItem.getmText2());
        deviceViewHolder.mIconMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View insideView = v;
                PopupMenu popup = new PopupMenu(v.getContext(),deviceViewHolder.mIconMenu);
                popup.inflate(R.menu.menu_device);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menuEdit:
                                //handle menu1 click
                                //Toast.makeText(insideView.getContext(), "Edit "+currentItem.getmDeviceId(), Toast.LENGTH_SHORT).show();
                                editShowDialog(insideView,currentItem.getmDeviceId(),currentItem.getmText1(),currentItem.getmText2());
                                break;
                            case R.id.menuDelete:
                                //handle menu2 click
                                confirmDelete(currentItem.getmDeviceId());
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
        /*deviceViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), currentItem.getmDeviceId(), Toast.LENGTH_SHORT).show();
            }
        });*/
        //deviceViewHolder.mCardView.setCardBackgroundColor(currentItem.getmColor());
    }

    private void editShowDialog(View v , String deviceId , String deviceName ,String deviceType){
        final EditText txtDeviceId,txtDeviceName;
        Button btnAdd;
        ImageView btnClose;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(v.getContext(),
                android.R.layout.simple_dropdown_item_1line,DEVICE_TYPE);
        editDialog.setContentView(R.layout.device_popup_edit);
        dbRef = myDb.getReference("SeThings-Device");

        final BetterSpinner betterSpinner = editDialog.findViewById(R.id.EdtDeviceType);
        txtDeviceId = (EditText) editDialog.findViewById(R.id.EdtDeviceID);
        txtDeviceName = (EditText) editDialog.findViewById(R.id.EdtDeviceName);
        btnAdd = (Button) editDialog.findViewById(R.id.BtnEditDevice);
        btnClose = (ImageView) editDialog.findViewById(R.id.CloseButton);

        txtDeviceId.setText(deviceId);
        txtDeviceId.setEnabled(false);
        txtDeviceName.setText(deviceName);
        betterSpinner.setText(deviceType);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputDeviceId = txtDeviceId.getText().toString();
                String inputDeviceName = txtDeviceName.getText().toString();
                String inputDeviceType = betterSpinner.getText().toString();

                postDevice(v, inputDeviceId,inputDeviceName,inputDeviceType); // call function to post data
            }
        });
        betterSpinner.setAdapter(arrayAdapter);
        editDialog.show();
    }

    private void postDevice(View view , String input1, String input2, String input3) {
        final DeviceData myDevice = new DeviceData(input1,input2,input3);
        final View layoutView = view;
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(myDevice.getDeviceID()).exists()){
                    dbRef.child(myDevice.getDeviceID()).setValue(myDevice);
                    editDialog.dismiss();
                }else{

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
                    dbRef3.child(myConfigStatus.getDeviceId()).setValue(myConfigStatus);
                    //Toast.makeText(getContext(), "Device ID arleady installed", Toast.LENGTH_SHORT).show();
                }else{

                    //myDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbRef4 = myDb.getReference("SeThings-Control");
        final ConfigData myConfigData = new ConfigData(input1,"#","#","#");
        dbRef4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(myConfigData.getDeviceId()).exists()){
                    dbRef4.child(myConfigData.getDeviceId()).setValue(myConfigData);
                    //Toast.makeText(getContext(), "Device ID arleady installed", Toast.LENGTH_SHORT).show();
                }else{

                    //myDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void confirmDelete(final String deviceId) {
        myDialog.setContentView(R.layout.device_popup_delete);
        Button btnYes,btnNo;
        btnYes = (Button) myDialog.findViewById(R.id.btnConfirm);
        btnNo = (Button) myDialog.findViewById(R.id.btnCancel);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dbRef.child(deviceId).removeValue();
                        myDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dbRef3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dbRef3.child(deviceId).removeValue();
                        myDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                dbRef4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dbRef4.child(deviceId).removeValue();
                        myDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }

}
