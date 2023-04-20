package com.example.versioapp1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RunningActivity extends Activity implements SensorEventListener {
    private TextView txtStartLabel, txtStopLabel, txtRTimer, txtRHeartRate, txtRStepDetector;
    private TextView txtRDistance, txtRCalories;
    private TextView txtRTimerLabel, txtRHeartRateLabel, txtRStepDetectorLabel, txtRDistanceLabel;
    private TextView txtRCaloriesLabel;
    private ImageButton btnRStart, btnRStop;
    private SensorManagerHelper sensorManagerHelper;
    private SharedPreferences sh;
    private String gUser, currentDate, formattedTime;
    private String wUser, hUser, aUser;
    private double weight, height;
    private int Age;
    private int minutes, hours, seconds;
    private long mStartTime = 0;
    private Handler mHandler = new Handler();
    private Runnable mTimerRunnable;
    private float heartRateSum= 0.0f;
    private float averageHeartRate= 0.0f;
    private  float numbersofHeartRate = 0.0f;
    private String heartRate="0";
    private double met = 11.5;//8.3MET FOR RUNNING
    private double distance,caloriesBurnt,pace;
    private int stepRDetectorCount = 0;
    private double BMR = 0.0;
    SharedPreferences sharedPreferences;
    private String stepDate;
    private int stepCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);
        //keep the screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //TEXTVIEW
        txtStartLabel = findViewById(R.id.txtRStartLabel);
        txtStopLabel = findViewById(R.id.txtRStopLabel);
        txtRTimer = findViewById(R.id.txtRTimer);
        txtRHeartRate = findViewById(R.id.txtRHeartRate);
        txtRStepDetector = findViewById(R.id.txtRStepDetector);
        txtRDistance = findViewById(R.id.txtRDistance);
        txtRCalories = findViewById(R.id.txtRCalories);
        txtRTimerLabel = findViewById(R.id.txtRTimerLabel);
        txtRHeartRateLabel = findViewById(R.id.txtRHeartRateLabel);
        txtRStepDetectorLabel = findViewById(R.id.txtRStepDetectorLabel);
        txtRDistanceLabel = findViewById(R.id.txtRDistanceLabel);
        txtRCaloriesLabel = findViewById(R.id.txtRCaloriesLabel);
        //Senor manager helper
        sensorManagerHelper = new SensorManagerHelper(this);
        //BUTTONS
        btnRStart = findViewById(R.id.btnRStart);
        btnRStop = findViewById(R.id.btnRStop);
        btnRStart.setOnClickListener(new StartRButtonClickListener(this));
        btnRStop.setOnClickListener(new StopRButtonClickListener(this));
        //getting user data from mainActivity
        //getting user data from mainActivty
        sh = getSharedPreferences("userData", Context.MODE_PRIVATE);
        gUser = sh.getString("gender", "");
        wUser = sh.getString("Weight", "");
        hUser = sh.getString("Height", "");
        aUser = sh.getString("Age", "");
        //weight and height converted in double
        weight = Double.parseDouble(wUser);
        height = Double.parseDouble(hUser);
        Age = Integer.parseInt(aUser);
        //ask permission
        AskPermission();

    }

    //ask permission to user for sensors
    public void AskPermission() {
        //Ask for permission for step detector
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) { //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
    }

    private class StartRButtonClickListener implements View.OnClickListener {
        private final RunningActivity runningActivity;

        private StartRButtonClickListener(RunningActivity runningActivity) {
            this.runningActivity = runningActivity;
        }

        @Override
        public void onClick(View v) {
            sensorManagerHelper.registerHeartRateListener(runningActivity);
            sensorManagerHelper.registerStepDetectorListener(runningActivity);
            if (btnRStart.getVisibility() == View.VISIBLE) {
                btnRStart.setVisibility(View.GONE);
                btnRStop.setVisibility(View.VISIBLE);
                txtStartLabel.setText("");
                txtStopLabel.setText("Stop");
                txtRHeartRate.setText("-");
                txtRStepDetector.setText("0");
                txtRDistance.setText("0.00");
                txtRCalories.setText("0.0");
                txtRTimerLabel.setText("TIMER");
                txtRStepDetectorLabel.setText("STEPS");
                txtRHeartRateLabel.setText("HEART RATE");
                txtRDistanceLabel.setText("DISTANCE");
                txtRCaloriesLabel.setText("CALORIES");
                //start timer
                startTimer();
                //get current time
                Date currentTime = new Date();
                // format the time using SimpleDateFormat
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
                formattedTime = sdf.format(currentTime);
                //get the current date
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                currentDate = dateFormat.format(calendar.getTime());

            } else {
                btnRStart.setVisibility(View.VISIBLE);
                btnRStop.setVisibility(View.GONE);
                txtStartLabel.setText("START");
                //reset labels and texts
                txtStopLabel.setText("");
                txtRHeartRate.setText("");
                txtRStepDetector.setText("");
                txtRDistance.setText("");
                txtRCalories.setText("");
                txtRTimerLabel.setText("");
                txtRStepDetectorLabel.setText("");
                txtRHeartRateLabel.setText("");
                txtRDistanceLabel.setText("");
                txtRCaloriesLabel.setText("");
                //stop timer
                stopTimer();
                //reset values
                averageHeartRate = 0.0f;
                numbersofHeartRate = 0.0f;
                heartRateSum = 0.0f;
            }

        }
    }
    //button stop clicked
    private class StopRButtonClickListener implements View.OnClickListener {

        private final RunningActivity runningActivity;

        public StopRButtonClickListener(RunningActivity runningActivity) {
            this.runningActivity = runningActivity;
        }

        @Override
        public void onClick(View v) {
            //unregister sensors
            sensorManagerHelper.unregisterHeartRateListener(runningActivity);
            sensorManagerHelper.unregisterStepDetectorListener(runningActivity);
            //calculate average heart rate
            averageHeartRate = heartRateSum/numbersofHeartRate;
            Intent intent = new Intent(getApplicationContext(),ResultRunningActivity.class);
            // Add data to the Intent
            intent.putExtra("minutes", minutes);
            intent.putExtra("hours", hours);
            intent.putExtra("seconds", seconds);
            intent.putExtra("heart_rate", averageHeartRate);
            intent.putExtra("step_detector", stepRDetectorCount);
            intent.putExtra("distance", distance);
            intent.putExtra("calories",caloriesBurnt);
            intent.putExtra("pace", pace);
            intent.putExtra("time", formattedTime);
            intent.putExtra("current_date", currentDate);
            startActivity(intent);
            //stop timer
            stopTimer();
        }
    }


    //start timer activity
    private void startTimer() {
        mStartTime = System.currentTimeMillis();
        mTimerRunnable = new Runnable() {
            @Override
            public void run() {
                long millis = System.currentTimeMillis() - mStartTime;
                seconds = (int) (millis / 1000);
                hours = minutes /60;
                minutes = seconds / 60;
                seconds = seconds % 60;
                //show hours only if you get to 59 minutes and 59 seconds
                if(minutes==59 && seconds ==59 )
                {
                    txtRTimer.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
                }else{
                    txtRTimer.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
                }
                mHandler.postDelayed(this, 1000);
            }
        };
        mHandler.postDelayed(mTimerRunnable, 0);
    }
    //stop timer activity
    private void stopTimer() {
        mHandler.removeCallbacks(mTimerRunnable);
        //send the data to database or store it before txtRTimer is reset
        txtRTimer.setText("");
        mStartTime = 0;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_HEART_RATE){
            heartRate = "" + (int) event.values[0];
            txtRHeartRate.setText("" + heartRate);
            //calculating average heart rate
            if(Float.parseFloat(heartRate) !=0) {
                heartRateSum += Float.parseFloat(heartRate);
                numbersofHeartRate++;
            }
        }
        //step detector sensor
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            stepRDetectorCount = (int) (stepRDetectorCount + event.values[0]);
            txtRStepDetector.setText(""+ stepRDetectorCount);
            double strip =0;
            //check if the user is male or female
            if(gUser.equals("Male")){
                strip = Double.parseDouble(String.valueOf(hUser)) * 0.415;
            }else if(gUser.equals("Female")){
                strip = Double.parseDouble(String.valueOf(hUser)) * 0.413;
            }
            //calculating the distance
            double stepCountMile = 160934.4 / strip;
            distance =Double.parseDouble(String.format("%.2f",(stepRDetectorCount * strip) / 100000));
            txtRDistance.setText(""+distance);
            //check the gender of user
            if(gUser.equals("Male")) {
                // The Revised Harris-Benedict Equation
                BMR = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * Age);
            }else {
                // The Revised Harris-Benedict Equation
                BMR = 447.593 + (9.247* weight) + (3.098 * height) - (4.330* Age);
            }
            //calculating calories burnt
            caloriesBurnt = (minutes * 3.5 * met *weight)/200;
            txtRCalories.setText(""+Double.parseDouble(String.format("%.2f",caloriesBurnt)));
            //calculating pace
            pace = minutes / distance; // speed in minutes per kilometer
            pace = 60.0/pace;//convert the speed in kilometers per hour
       }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}