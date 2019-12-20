package com.example.warehousemanagment.ali.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.warehousemanagment.R;

public class SplashActivity extends AppCompatActivity {

    static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final TextView appLogoTxtView = findViewById(R.id.txtView_logo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent homeIntent = new Intent(SplashActivity.this, LoginActivity.class);

                startActivity(homeIntent);
                finish();

            }
        }, SPLASH_TIME_OUT);
    }
}
