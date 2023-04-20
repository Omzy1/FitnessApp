package com.example.versioapp1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class ResultRunningActivity extends Activity {
    private TextView TextViewTimer, TextViewDistance, TextViewCalories;
    private TextView TextViewPace, TextViewHeartRate, TextViewStepDetector;
    private TextView TextViewTime;
    private ImageButton btnResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_running);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //TextView
        TextViewTimer = findViewById(R.id.TextRViewTimer);
        TextViewDistance = findViewById(R.id.TextRViewDistance);
        TextViewCalories = findViewById(R.id.TextRViewCalories);
        TextViewPace = findViewById(R.id.TextRViewPace);
        TextViewDistance = findViewById(R.id.TextRViewDistance);
        TextViewHeartRate = findViewById(R.id.TextRViewHeartRate);
        TextViewStepDetector = findViewById(R.id.TextRViewStepDetector);
        TextViewTime = findViewById(R.id.TextRViewTime);
        //getting data from RunningActivity
        Intent intent = getIntent();
        int minutes = intent.getIntExtra("minutes", 0);
        int hours = intent.getIntExtra("hours", 0);
        int seconds = intent.getIntExtra("seconds", 0);
        float heartRate = intent.getFloatExtra("heart_rate", 0);
        int stepDetector = intent.getIntExtra("step_detector", 0);
        double distance = intent.getDoubleExtra("distance", 0.0);
        double calories = intent.getDoubleExtra("calories", 0.0);
        double pace = intent.getDoubleExtra("pace", 0.0);
        String time = intent.getStringExtra("time");
        String currentDate = intent.getStringExtra("current_date");
        //display running Activity
        TextViewTimer.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
        TextViewHeartRate.setText(String.valueOf(Float.parseFloat(String.format("%.1f", heartRate)))
                + " BPM");
        TextViewStepDetector.setText(String.valueOf(stepDetector) + " steps");
        TextViewDistance.setText(String.valueOf(Double.parseDouble(String.format("%.2f", distance))) + " km");
        TextViewCalories.setText(String.valueOf(Double.parseDouble(String.format("%.2f", calories))) + " cal");
        TextViewPace.setText(String.valueOf(Double.parseDouble(String.format("%.2f", pace))) + " kmh");
        TextViewTime.setText(time);

        //button
        btnResult = findViewById(R.id.btnResultR);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to main activity and send data
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                intent2.putExtra("hoursR", hours);
                intent2.putExtra("minutesR", minutes);
                intent2.putExtra("secondsR", seconds);
                intent2.putExtra("heartRateR", heartRate);
                intent2.putExtra("stepDetectorR", stepDetector);
                intent2.putExtra("distanceR", distance);
                intent2.putExtra("caloriesR", calories);
                intent2.putExtra("paceR", pace);
                intent2.putExtra("timeR", time);
                intent2.putExtra("current_dateR", currentDate);
                startActivity(intent2);
            }
        });

    }

}