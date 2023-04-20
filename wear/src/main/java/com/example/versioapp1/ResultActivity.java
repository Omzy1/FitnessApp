package com.example.versioapp1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

public class ResultActivity extends Activity {

    private TextView TextViewTimer, TextViewDistance, TextViewCalories;
    private TextView TextViewPace, TextViewHeartRate, TextViewStepDetector;
    private TextView TextViewTime;
    private ImageButton btnResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //TextView
        TextViewTimer = findViewById(R.id.TextViewTimer);
        TextViewDistance = findViewById(R.id.TextViewDistance);
        TextViewCalories = findViewById(R.id.TextViewCalories);
        TextViewPace = findViewById(R.id.TextViewPace);
        TextViewDistance = findViewById(R.id.TextViewDistance);
        TextViewHeartRate = findViewById(R.id.TextViewHeartRate);
        TextViewStepDetector = findViewById(R.id.TextViewStepDetector);
        TextViewTime = findViewById(R.id.TextViewTime);

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
        //displaying
        TextViewTimer.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
        TextViewHeartRate.setText(String.valueOf(Float.parseFloat(String.format("%.1f", heartRate)))
                + " BPM");
        TextViewStepDetector.setText(String.valueOf(stepDetector) + " steps");
        TextViewDistance.setText(String.valueOf(Double.parseDouble(String.format("%.2f", distance))) + " km");
        TextViewCalories.setText(String.valueOf(Double.parseDouble(String.format("%.2f", calories))) + " cal");
        TextViewPace.setText(String.valueOf(Double.parseDouble(String.format("%.2f", pace))) + " kmh");
        TextViewTime.setText(time);

        btnResult = findViewById(R.id.btnResult);
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to main activity and send data
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                intent1.putExtra("hours", hours);
                intent1.putExtra("minutes", minutes);
                intent1.putExtra("seconds", seconds);
                intent1.putExtra("heartRate", heartRate);
                intent1.putExtra("stepDetector", stepDetector);
                intent1.putExtra("distance", distance);
                intent1.putExtra("calories", calories);
                intent1.putExtra("pace", pace);
                intent1.putExtra("time", time);
                intent1.putExtra("current_date", currentDate);
                startActivity(intent1);
            }
        });


    }
}