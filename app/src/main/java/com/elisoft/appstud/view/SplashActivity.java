package com.elisoft.appstud.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.elisoft.appstud.R;

/**
 * @author phuc.tran
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * Timer for waiting some seconds before navigating to Main page
     */
    private final int TIME = 3000; // Wait for 3 seconds
    private CountDownTimer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        initTimer();
    }

    /**
     * Setup timer (wait for some seconds before navigating to Main page)
     */
    private void initTimer() {
        timer = new CountDownTimer(TIME, TIME) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                /**
                 * Open main page
                 */
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                // Close splash screen
                finish();
            }
        };

        // Start timer
        timer.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        /**
         * Cancel timer if user presses Back
         */
        if (timer != null) {
            timer.cancel();
        }
    }
}
