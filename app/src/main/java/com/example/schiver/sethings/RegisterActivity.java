package com.example.schiver.sethings;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.schiver.sethings.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity {

    FirebaseDatabase myDb;
    DatabaseReference dbRef,dbRef2;
    Button RegistBtn;
    ProgressBar progressBar;
    EditText edt_uname,edt_pass,edt_cpass,edt_name,edt_phone,edt_home;

    Handler handler;
    Runnable runnable;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Users");
        dbRef2 = myDb.getReference("SeThings-Usage");

        progressBar = (ProgressBar) findViewById(R.id.loading_bar);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        RegistBtn   = findViewById(R.id.BtnSubmit);
        edt_uname   = findViewById(R.id.EdtUname);
        edt_pass    = findViewById(R.id.EdtPass);
        edt_cpass   = findViewById(R.id.EdtCPass);
        edt_name    = findViewById(R.id.EdtName);
        edt_phone   = findViewById(R.id.EdtPhone);
        edt_home    = findViewById(R.id.EdtHome);

        final Intent SuccessRegister = new Intent(this, SuccessRegisterActivity.class);


        RegistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                edt_uname.setFocusable(false);
                edt_pass.setFocusable(false);
                edt_cpass.setFocusable(false);
                edt_name.setFocusable(false);
                edt_phone.setFocusable(false);
                edt_home.setFocusable(false);

                handler =  new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        progressBar.setVisibility(View.GONE);
                        if(edt_pass.getText().toString().equals(edt_cpass.getText().toString())){
                            postAccount();
                            startActivity(SuccessRegister);
                        }else{
                            Toast.makeText(RegisterActivity.this, "Confirm password didn't match", Toast.LENGTH_SHORT).show();
                            edt_uname.setFocusableInTouchMode(true);
                            edt_pass.setFocusableInTouchMode(true);
                            edt_cpass.setFocusableInTouchMode(true);
                            edt_name.setFocusableInTouchMode(true);
                            edt_phone.setFocusableInTouchMode(true);
                            edt_home.setFocusableInTouchMode(true);
                        }
                        //startActivity(SuccessRegister);
                    }
                };
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(runnable);
                    }
                },5000,3000);
            }
        });
    }



    private void postAccount(){
        String postUname    = edt_uname.getText().toString();
        String postPass     = edt_pass.getText().toString();
        String postName     = edt_name.getText().toString();
        String postPhone    = edt_phone.getText().toString();
        String postHome     = edt_home.getText().toString();

        final User userPost = new User(postUname,postPass,postName,postPhone,postHome);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(userPost.getUsername()).exists()){
                    Toast.makeText(RegisterActivity.this, "User already registered", Toast.LENGTH_SHORT).show();
                }else{
                    dbRef.child(userPost.getUsername()).setValue(userPost);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*Post post = new Post(postUname,postPass,postName,postPhone,postHome);
        Energy energy = new Energy("100");
        dbRef.push().setValue(post);
        dbRef2.push().setValue(energy);*/
    }
}
