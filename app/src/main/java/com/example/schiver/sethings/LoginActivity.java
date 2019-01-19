package com.example.schiver.sethings;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.schiver.sethings.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button RegisterButton , LoginButton;
    FirebaseDatabase myDb;
    DatabaseReference dbRef;
    EditText inputUser, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RegisterButton = (Button) findViewById(R.id.button_register);
        LoginButton = (Button) findViewById(R.id.button_login);
        inputUser = (EditText) findViewById(R.id.txtUser);
        inputPassword = (EditText) findViewById(R.id.txtPass);
        myDb = FirebaseDatabase.getInstance();
        dbRef = myDb.getReference("SeThings-Users");



        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent RegisterIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(RegisterIntent);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login(inputUser.getText().toString(),inputPassword.getText().toString());
            }
        });
    }
    private void Login(final String username , final String password){
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()){
                    if(!username.isEmpty()){
                        User login = dataSnapshot.child(username).getValue(User.class);
                        if(login.getPassword().equals(password)){
                            Intent HomeActivity = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(HomeActivity);
                        }else{
                            Toast.makeText(LoginActivity.this, "Account not registered yet", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Account not registered yet", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
