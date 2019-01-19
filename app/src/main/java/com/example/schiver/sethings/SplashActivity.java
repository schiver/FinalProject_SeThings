package com.example.schiver.sethings;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private TextView tv;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv = (TextView) findViewById(R.id.TextIcon1);
        iv = (ImageView) findViewById(R.id.LogoIcon);
        Animation myAnimation = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tv.startAnimation(myAnimation);
        iv.startAnimation(myAnimation);
        final Intent Login = new Intent(this, LoginActivity.class);
        Thread timer =  new Thread() {
            public void run(){
                try{
                    sleep(5000);
                }catch (InterruptedException e  ){
                    e.printStackTrace();
                }
                finally {
                    startActivity(Login);
                    finish();
                }
            }
        };
        timer.start();
    }
}
