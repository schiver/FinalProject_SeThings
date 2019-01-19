package com.example.schiver.sethings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SuccessRegisterActivity extends AppCompatActivity {
    TextView linkLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);
        linkLogin = (TextView) findViewById(R.id.textLink);
        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(SuccessRegisterActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }
}
