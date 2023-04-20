package com.example.versioapp1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleClient;
    DataMap dataMap;
    private int hours, minutes, seconds;
    private float heartRate;
    private int stepDetector;
    private double distance, calories, pace;
    private String time, currentDate;
    private int hoursR, minutesR, secondsR;
    private float heartRateR;
    private int stepDetectorR;
    private double distanceR, caloriesR, paceR;
    private String timeR, currentDateR;
    private String Age, Height, Weight, gender;
    SharedPreferences sharedPreferences;
    private String stepDate;
    private int stepCount = 0;
    private double met = 3.5;
    private double BMR = 0;
    private double caloriesBurnt = 0;
    private TextView txtTime,txtErrorProfile;
    private Handler handler;
    private Runnable runnable;
    private Button btnWalking, btnRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //to keep the watch on while using the app
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //TextView
        txtErrorProfile = findViewById(R.id.txtErrorProfile);
        //ask permission
        askPermission();
        //Button
        WalkActivity();
        RunningActivity();
        //dataMap to store data to be sent
        dataMap = new DataMap();
        // Build a new GoogleApiClient for the the Wearable API
        googleClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // Register the local broadcast receiver
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);
        //getting data from ResultActivity
        Intent intent1 = getIntent();
        hours = intent1.getIntExtra("hours", 0);
        minutes = intent1.getIntExtra("minutes", 0);
        seconds = intent1.getIntExtra("seconds", 0);
        stepDetector = intent1.getIntExtra("stepDetector", 0);
        heartRate = intent1.getFloatExtra("heartRate", 0);
        distance = intent1.getDoubleExtra("distance", 0.0);
        calories = intent1.getDoubleExtra("calories", 0.0);
        pace = intent1.getDoubleExtra("pace", 0.0);
        time = intent1.getStringExtra("time");
        currentDate = intent1.getStringExtra("current_date");
        //getting data from ResultRunningActivity
        Intent intent2 = getIntent();
        hoursR = intent2.getIntExtra("hoursR", 0);
        minutesR = intent2.getIntExtra("minutesR", 0);
        secondsR = intent2.getIntExtra("secondsR", 0);
        stepDetectorR = intent2.getIntExtra("stepDetectorR", 0);
        heartRateR = intent2.getFloatExtra("heartRateR", 0);
        distanceR = intent2.getDoubleExtra("distanceR", 0.0);
        caloriesR = intent2.getDoubleExtra("caloriesR", 0.0);
        paceR = intent2.getDoubleExtra("paceR", 0.0);
        timeR = intent2.getStringExtra("timeR");
        currentDateR = intent2.getStringExtra("current_dateR");
        //display current time every second
        txtTime = findViewById(R.id.txtTime);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateTime();
                handler.postDelayed(this, 1000); // run every second
            }
        };


    }
    public  void askPermission(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BODY_SENSORS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.BODY_SENSORS}, 0);
        }
    }

    //start timer
    private void startUpdateTimer() {
        handler.postDelayed(runnable, 0);
    }

    //stop timer
    private void stopUpdateTimer() {
        handler.removeCallbacks(runnable);
    }

    //update timer
    private void updateTime() {
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        String formattedTime = sdf.format(currentTime);
        txtTime.setText(formattedTime);
    }

    //Button walking
    public void WalkActivity() {
        btnWalking = findViewById(R.id.btnWalking);
        btnWalking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if the user has entered at least once their details in the profile page
              SharedPreferences  sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
               String gender= sharedPreferences.getString("gender","");
                String height = sharedPreferences.getString("Height","");
                String weight = sharedPreferences.getString("Weight","");
                String age = sharedPreferences.getString("Age","");
                if(!gender.isEmpty() && !height.isEmpty() && !weight.isEmpty() && !age.isEmpty()) {
                    startActivity(new Intent(MainActivity.this, WalkingActivity.class));
                    stopUpdateTimer();
                }else{
                    //Toast.makeText(MainActivity.this, "Please fill you details in the profile page", Toast.LENGTH_SHORT).show();
                    txtErrorProfile.setVisibility(View.VISIBLE);
                    txtErrorProfile.setText("Please fill your details at least once in the phone's profile page.");
                }




            }
        });
    }

    //button running
    public void RunningActivity() {
        btnRunning = findViewById(R.id.btnRunning);
        btnRunning.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                //check if the user has entered at least once their details in the profile page
                SharedPreferences  sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
                String gender= sharedPreferences.getString("gender","");
                String height = sharedPreferences.getString("Height","");
                String weight = sharedPreferences.getString("Weight","");
                String age = sharedPreferences.getString("Age","");
                if(!gender.isEmpty() && !height.isEmpty() && !weight.isEmpty() && !age.isEmpty()) {
                startActivity(new Intent(MainActivity.this, RunningActivity.class));
                stopUpdateTimer();
                }else{
                    //Toast.makeText(MainActivity.this, "Please fill you details in the profile page", Toast.LENGTH_SHORT).show();
                    txtErrorProfile.setVisibility(View.VISIBLE);
                    txtErrorProfile.setText("Please fill your details at least once in the phone's profile page.");
                }
            }
        });

    }

    // Connect to the data layer when the Activity starts
    @Override
    protected void onStart() {
        super.onStart();
        googleClient.connect();
        startUpdateTimer();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        String options = "";
        //path
        String WEARABLE_DATA_PATH = "/wearable_data";
        //dataMap for sending data back to the phone
        if (currentDate != null && time != null && heartRate != 0) {
            //walking activity data that are sent to the phone app
            dataMap.putString("hours", String.valueOf(hours));
            dataMap.putString("minutes", String.valueOf(minutes));
            dataMap.putString("seconds", String.valueOf(seconds));
            dataMap.putString("stepDetector", String.valueOf(stepDetector));
            dataMap.putString("heartRate", String.valueOf(heartRate));
            dataMap.putString("distance", String.valueOf(distance));
            dataMap.putString("calories", String.valueOf(calories));
            dataMap.putString("pace", String.valueOf(pace));
            dataMap.putString("time", String.valueOf(time));
            dataMap.putString("current_date", String.valueOf(currentDate));
            //Requires a new thread to avoid blocking the UI
            new sendMessage(WEARABLE_DATA_PATH, dataMap).start();
        }
        //running activity
        if (currentDateR != null && timeR != null && heartRateR != 0) {
            dataMap.putString("hoursR", String.valueOf(hoursR));
            dataMap.putString("minutesR", String.valueOf(minutesR));
            dataMap.putString("secondsR", String.valueOf(secondsR));
            dataMap.putString("stepDetectorR", String.valueOf(stepDetectorR));
            dataMap.putString("heartRateR", String.valueOf(heartRateR));
            dataMap.putString("distanceR", String.valueOf(distanceR));
            dataMap.putString("caloriesR", String.valueOf(caloriesR));
            dataMap.putString("paceR", String.valueOf(paceR));
            dataMap.putString("timeR", String.valueOf(timeR));
            dataMap.putString("current_dateR", String.valueOf(currentDateR));
            //Requires a new thread to avoid blocking the UI
            new sendMessage(WEARABLE_DATA_PATH, dataMap).start();

        }


    }

    // Disconnect from the data layer when the Activity stops
    @Override
    protected void onStop() {
        if (null != googleClient && googleClient.isConnected()) {
            googleClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    class sendMessage extends Thread {
        String path;
        DataMap dataMap;

        // Constructor for sending data objects to the data layer
        sendMessage(String p, DataMap data) {
            path = p;
            dataMap = data;
        }

        public void run() {
            // Construct a DataRequest and send over the data layer
            PutDataMapRequest putDMR = PutDataMapRequest.create(path);
            putDMR.getDataMap().putAll(dataMap);
            PutDataRequest request = putDMR.asPutDataRequest();
            DataApi.DataItemResult result = Wearable.DataApi.putDataItem(googleClient, request).await();
            if (result.getStatus().isSuccess()) {
                Log.v("myTag", "DataMap: " + dataMap + " sent successfully to data layer ");
            } else {
                // Log an error
                Log.v("myTag", "ERROR: failed to send DataMap to data layer");
            }
        }
    }

    //receiving the message
    public class MessageReceiver extends BroadcastReceiver {
        SharedPreferences sharedPreferences;

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle data = intent.getBundleExtra("dataMap");
            // Display received data in UI
            gender = data.getString("gender");
            Height = data.getString("Height");
            Weight = data.getString("Weight");
            Age = data.getString("Age");
            //sharedPreference
            sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sharedPreferences.edit();
            if (gender != null && Height != null && Weight != null && Age != null) {
                ed.putString("gender", gender);
                ed.putString("Height", Height);
                ed.putString("Weight", Weight);
                ed.putString("Age", Age);
                ed.commit();
            }

        }
    }
}
