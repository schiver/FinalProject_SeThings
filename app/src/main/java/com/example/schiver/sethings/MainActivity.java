package com.example.schiver.sethings;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Toolbar mytoolbar;
    Button button1;
    //private Object Menu;
    CardView menuHome,menuConfig,menuDocs,menuDevice,menuProfile,menuHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mytoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);

        menuHome = (CardView) findViewById(R.id.Home);
        menuConfig = (CardView) findViewById(R.id.Config);
        menuDocs = (CardView) findViewById(R.id.Docs);
        menuDevice = (CardView) findViewById(R.id.Device);
        menuProfile = (CardView) findViewById(R.id.Profile);
        menuHelp = (CardView) findViewById(R.id.Help);

        menuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(MainActivity.this,Home.class);
                homeIntent.putExtra("PageNumber","0");
                startActivity(homeIntent);
                //Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
            }
        });
        menuConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Config", Toast.LENGTH_SHORT).show();
                Intent homeIntent = new Intent(MainActivity.this,Home.class);
                homeIntent.putExtra("PageNumber","1");
                startActivity(homeIntent);

            }
        });
        menuDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Docs", Toast.LENGTH_SHORT).show();
            }
        });
        menuDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Device", Toast.LENGTH_SHORT).show();
                Intent homeIntent = new Intent(MainActivity.this,Home.class);
                homeIntent.putExtra("PageNumber","2");
                startActivity(homeIntent);

            }
        });
        menuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
            }
        });
        menuHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Help", Toast.LENGTH_SHORT).show();
                Intent homeIntent = new Intent(MainActivity.this,Home.class);
                homeIntent.putExtra("PageNumber","3");
                startActivity(homeIntent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);

    }
}
