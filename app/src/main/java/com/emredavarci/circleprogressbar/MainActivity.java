package com.emredavarci.circleprogressbar;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Handler handle = null;
    int progress = 0;
    Runnable runnable = null;

    CircleProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (CircleProgressBar) findViewById(R.id.progressBar);

        // Simulate download
        handle = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                progress += 1;

                // Set progress
                progressBar.setProgress(progress);
                progressBar.setText(String.valueOf(progress));

                if(progress >= 100){
                    progressBar.setTextColor(Color.GREEN);
                    progressBar.setText("DONE");
                    handle.removeCallbacks(runnable);
                }
                handle.postDelayed(runnable, 100);
            }
        };
        runnable.run();
    }
}
