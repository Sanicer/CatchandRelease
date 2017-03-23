package com.sanics.catchandrelease;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                nextActivity();
                finish();
            }
        },1000);

    }

    public void nextActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
