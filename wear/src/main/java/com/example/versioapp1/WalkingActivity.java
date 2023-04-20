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


import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class WalkingActivity extends Activity implements SensorEventListener {

    private double BMR = 0.0;
    private ImageButton btnStart, btnStop;
    private TextView txtStartTitle,txtTimer, txtStopTitle,txtHeartRate,txtStepDetector;
    private TextView txtDistance, txtCalories;
    private TextView txtTimerLabel,txtHeartRateLabel,txtStepDetectorLabel,txtDistanceLabel, txtCaloriesLabel;
    private String steps = "";
    private int stepDetectorCount = 0;
    private int stepCount =0;
    private SharedPreferences sharedPreferences;
    private SharedPreferences sh;
     private SensorManagerHelper sensorManagerHelper;
     private String heartRate="0";
    private Handler mHandler = new Handler();
    private Runnable mTimerRunnable;
    private long mStartTime = 0;
    private  float numbersofHeartRate = 0.0f;
    private float heartRateSum= 0.0f;
    private float averageHeartRate= 0.0f;
    private String gUser,currentDate;
    private String wUser, hUser, aUser;
    private double weight, height;
    private int Age,seconds;
    private double met = 4.3;// the MET value for walking at a moderate pace
    private int minutes, hours;
    private double distance,caloriesBurnt,pace;
    private String formattedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walking);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //TextView
        txtStartTitle = findViewById(R.id.txtStartTitle);
        //txtTest = findViewById(R.id.txtTest);
        txtTimer = findViewById(R.id.txtTimer);
        txtStopTitle = findViewById(R.id.txtStopTitle);
        txtHeartRate = findViewById(R.id.txtHeartRate);
        txtStepDetector = findViewById(R.id.txtStepDetector);
        txtDistance = findViewById(R.id.txtDistance);
        txtCalories = findViewById(R.id.txtCalories);
        //TextView labels
        txtTimerLabel = findViewById(R.id.txtTimerLabel);
        txtHeartRateLabel = findViewById(R.id.txtHeartRateLabel);
        txtStepDetectorLabel = findViewById(R.id.txtStepDetectorLabel);
        txtDistanceLabel = findViewById(R.id.txtDistanceLabel);
        txtCaloriesLabel = findViewById(R.id.txtCaloriesLabel);
        //buttons
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        //sharedPreference
        //Setting up sharePreference
        sharedPreferences = this.getSharedPreferences("Step_Counter" , Context.MODE_PRIVATE);
        SharedPreferences.Editor ed;
        //getting user data from mainActivty
        sh = getSharedPreferences("userData" , Context.MODE_PRIVATE);
        gUser = sh.getString("gender","");
        wUser = sh.getString("Weight", "");
        hUser = sh.getString("Height","");
        aUser = sh.getString("Age" , "");
        //weight and height converted in double
        weight = Double.parseDouble(wUser);
        height  = Double.parseDouble(hUser);
        Age = Integer.parseInt(aUser);


        // ask permission and check if sensors are available
        AskPermission();
        //sensorManagerHelper class
        sensorManagerHelper = new SensorManagerHelper(this);
        //setting up two inner classes
        btnStart.setOnClickListener(new StartButtonClickListener(this));
        btnStop.setOnClickListener(new StopButtonClickListener(this));
    }

    //ask permission to user for sensors
    public void AskPermission(){
        //Ask for permission for step detector
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){ //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.BODY_SENSORS}, 0);
        }
    }
    //button start clicked
    private class StartButtonClickListener implements View.OnClickListener {
    private final WalkingActivity walkingActivity;

    public StartButtonClickListener(WalkingActivity walkingActivity) {
        this.walkingActivity = walkingActivity;
    }
    @Override
    public void onClick(View v) {
        sensorManagerHelper.registerHeartRateListener(walkingActivity);
        sensorManagerHelper.registerStepDetectorListener(walkingActivity);
        if (btnStart.getVisibility() == View.VISIBLE) {
            btnStart.setVisibility(View.GONE);
            btnStop.setVisibility(View.VISIBLE);
            txtStartTitle.setText("");
            txtStopTitle.setText("Stop");
            txtHeartRate.setText("-");
            txtStepDetector.setText("0");
            txtDistance.setText("0.00");
            txtCalories.setText("0.0");
            txtTimerLabel.setText("TIMER");
            txtStepDetectorLabel.setText("STEPS");
            txtHeartRateLabel.setText("HEART RATE");
            txtDistanceLabel.setText("DISTANCE");
            txtCaloriesLabel.setText("CALORIES");
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
            btnStart.setVisibility(View.VISIBLE);
            btnStop.setVisibility(View.GONE);
            txtStartTitle.setText("START");
            //reset labels and texts
            txtStopTitle.setText("");
            txtHeartRate.setText("");
            txtStepDetector.setText("");
            txtDistance.setText("");
            txtCalories.setText("");
            txtTimerLabel.setText("");
            txtStepDetectorLabel.setText("");
            txtHeartRateLabel.setText("");
            txtDistanceLabel.setText("");
            txtCaloriesLabel.setText("");
            stopTimer();
            //reset heart rate values
            averageHeartRate = 0.0f;
            numbersofHeartRate = 0.0f;
            heartRateSum = 0.0f;
        }

    }

    }
    // button stop clicked
    private class StopButtonClickListener implements View.OnClickListener {

        private final WalkingActivity walkingActivity;

        public StopButtonClickListener(WalkingActivity walkingActivity) {
            this.walkingActivity = walkingActivity;
        }

        @Override
        public void onClick(View v) {
            sensorManagerHelper.unregisterHeartRateListener(walkingActivity);
            sensorManagerHelper.unregisterStepCounterListener(walkingActivity);
            sensorManagerHelper.unregisterStepDetectorListener(walkingActivity);
            //calculating the average heart rate
            averageHeartRate = heartRateSum/numbersofHeartRate;
            Intent intent = new Intent(getApplicationContext(),ResultActivity.class);
            // Add data to the Intent
            intent.putExtra("minutes", minutes);
            intent.putExtra("hours", hours);
            intent.putExtra("seconds", seconds);
            intent.putExtra("heart_rate", averageHeartRate);
            intent.putExtra("step_detector", stepDetectorCount);
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
                    txtTimer.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
                }else{
                    txtTimer.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
                }
                mHandler.postDelayed(this, 1000);
            }
        };
        mHandler.postDelayed(mTimerRunnable, 0);
    }
    //stop timer activity
    private void stopTimer() {
        mHandler.removeCallbacks(mTimerRunnable);
        //send the data to database or store it before txtTimer will set it to 0
        txtTimer.setText("");
        mStartTime = 0;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        //heart rate sensor
        if(event.sensor.getType()==Sensor.TYPE_HEART_RATE){
            heartRate = "" + (int) event.values[0];
            txtHeartRate.setText("" + heartRate);
            //calculating average heart rate
            if(Float.parseFloat(heartRate) !=0) {
                heartRateSum += Float.parseFloat(heartRate);
                numbersofHeartRate++;
            }
        }
        //step detector sensor
        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            stepDetectorCount = (int) (stepDetectorCount + event.values[0]);
            txtStepDetector.setText(""+ stepDetectorCount);
            double strip =0;
            //check if the user is male or female
            if(gUser.equals("Male")){
                strip = Double.parseDouble(String.valueOf(hUser)) * 0.415;
            }else if(gUser.equals("Female")){
                strip = Double.parseDouble(String.valueOf(hUser)) * 0.413;
            }
            //calculating the distance
            double stepCountMile = 160934.4 / strip;
             distance =Double.parseDouble(String.format("%.2f",(stepDetectorCount * strip) / 100000));
             txtDistance.setText(""+distance);
            //check the gender of user
            if(gUser.equals("Male")) {
               // The Revised Harris-Benedict Equation
                BMR = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * Age);
            }else {
                // The Revised Harris-Benedict Equation
                BMR = 447.593 + (9.247* weight) + (3.098 * height) - (4.330* Age);
            }

             caloriesBurnt = minutes *( met * 3.5 *weight)/200;
            //caloriesBurnt = (met * BMR * minutes)/60;
            txtCalories.setText(""+Double.parseDouble(String.format("%.2f",caloriesBurnt)));
            //calculating pace
             pace = minutes / distance; // speed in minutes per kilometer
            pace = 60.0/pace;//convert the speed in kilometers per hour
            }
        //step counter
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            if(sharedPreferences.contains(currentDate)) {
                stepCount = sharedPreferences.getInt(currentDate, 1);
                stepCount+=1;
                sharedPreferences.edit().putInt(currentDate,stepCount).apply();
                Log.d("StepsC", ""+stepCount);
        }

    }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}