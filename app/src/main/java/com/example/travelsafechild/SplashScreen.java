package com.example.travelsafechild;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler =  new Handler();
        Runnable myRunnable = new Runnable() {
            public void run() {

                //Starting new Activity
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        };

        //Stopping splash screen for 4 seconds.
        handler.postDelayed(myRunnable, 4000);
    }
}