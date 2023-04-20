package com.example.versioapp1;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;
import android.content.BroadcastReceiver;


import com.example.versioapp1.Database.Running;
import com.example.versioapp1.Database.RunningViewModel;
import com.example.versioapp1.Database.User;
import com.example.versioapp1.Database.UserWithRunning;
import com.example.versioapp1.Database.UserWithWalking;
import com.example.versioapp1.Database.Walking;
import com.example.versioapp1.Database.WalkingViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextInputLayout layoutGender;
    private Button btnApply;
    SharedPreferences sharedPreferences;
    GoogleApiClient googleClient;
    DataMap dataMap;
    String[] genderList = {"Male", "Female"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterGenderItems;
    private EditText datePickerBtn, edtxtWeight, edtxtGoalSteps;
    private EditText edtxtHeight;
    private DatePickerDialog datePickerDialog;
    String age = "0";
    String gender;
    private TextView txtTest, txtErrors;
    SharedPreferences sh;
    WalkingViewModel walkingViewModel;
    List<User> users = new ArrayList<>();
    private String weight;
    private int Min_Value = 4;
    private long walkingId = 0;
    private long runningId = 0;
    private int stepGoals = 0;
    private float Height = 0.0f;
    private float Weight = 0.0f;
    private int Age = 0;
    private String hours, minutes, seconds, stepDetector, distance, caloriesBurnt, heartRate;
    private String pace, time, currentDate, timer;
    private double Distance, Calories, Pace;
    private int HeartRate, StepDetector;
    private String hoursRun, minutesRun, secondsRun, stepDetectorRun, distanceRun, caloriesBurntRun, heartRateRun;
    private String paceRun, timeRun, currentDateRun, timerRun;
    private double DistanceRun, CaloriesRun, PaceRun;
    private int HeartRateRun, StepDetectorRun;
    User user;
    UserWithWalking userWithWalking;
    UserWithRunning userWithRunning;
    RunningViewModel runningViewModel;
    private boolean clicked = false;
    private String options = "";
    private int userId = 0;
    private List<Walking> walkingList;
    private List<Running> runningList;


    public ThirdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        // TextInputLayout
        layoutGender = view.findViewById(R.id.txtInputLayout);
        edtxtGoalSteps = view.findViewById(R.id.edtxtGoalSteps);
        //TextView
        txtErrors = view.findViewById(R.id.txtErrors);
        // Register the local broadcast receiver
        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(messageReceiver, messageFilter);
        //AutoCompleteTextView and ArrayAdapter
        autoCompleteTxt = view.findViewById(R.id.auto_complete_txt);
        adapterGenderItems = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_gender_items, genderList);
        autoCompleteTxt.setAdapter(adapterGenderItems);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) adapterView.getItemAtPosition(i).toString();
                gender = item;
            }
        });
        //DatePicker Age
        DatePicker();
        datePickerBtn = view.findViewById(R.id.datePickerBtn);
        openDatePicker();
        txtTest = view.findViewById(R.id.txtTest);
        //weight Picker
        edtxtWeight = view.findViewById(R.id.edtxtWeight);
        showWeight();
        //height
        edtxtHeight = view.findViewById(R.id.edtxtHeight);


        //SharePreference
        sharedPreferences = this.getActivity().getSharedPreferences("userPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        // Build a new GoogleApiClient for the the Wearable API
        googleClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        //dataMap to store data to be sent
        dataMap = new DataMap();
        //initialising viewModels

        walkingViewModel = new ViewModelProvider(ThirdFragment.this).get(WalkingViewModel.class);
        runningViewModel = new ViewModelProvider(ThirdFragment.this).get(RunningViewModel.class);
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        //observe the database
        walkingViewModel.getAllUser().observe(getActivity(), users -> {
            for (User user1 : users) {
                //values collected from the database
                if (!users.isEmpty()) {
                    gender = user1.getGender();
                    Age = user1.getAge();
                    Weight = user1.getWeight();
                    Height = user1.getHeight();
                    stepGoals = user1.getCountGoal();
                    //getting the data
                    editor.putString("gender", gender);
                    editor.putInt("age", Age);
                    editor.putFloat("weight", Weight);
                    editor.putFloat("height", Height);
                    editor.putInt("stepGoals", stepGoals);
                    editor.apply();
                }
                //getting the data from the sharedPreference
                gender = sharedPref.getString("gender", "");
                Age = sharedPref.getInt("age", 0);
                Weight = sharedPref.getFloat("weight", 0.0f);
                Height = sharedPref.getFloat("height", 0.0f);
                stepGoals = sharedPref.getInt("stepGoals", 0);
                Log.d("gender", "" + Age);

                //if those values are not null
                if (gender != null && Age != 0 && Weight != 0 && Height != 0) {
                    user = new User(gender, Age, Weight, Height, stepGoals);
                    //store previous user data in the profile page
                    edtxtGoalSteps.setText(String.valueOf(stepGoals));
                    edtxtHeight.setText(String.valueOf(Height));
                }
            }

        });

        //Button
        btnApply = view.findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //declare data
                String height = edtxtHeight.getText().toString();
                String goals = edtxtGoalSteps.getText().toString();
                txtErrors.setText("Apply Changed");
                txtErrors.setTextColor(Color.GREEN);
                //path
                String WEARABLE_DATA_PATH = "/wearable_data";
                //send data
                dataMap.putString("gender", gender);
                dataMap.putString("Weight", weight);
                dataMap.putString("Height", height);
                dataMap.putString("Age", age);

                if (!TextUtils.isEmpty(goals) && !TextUtils.isEmpty(height) && !TextUtils.isEmpty(age)
                        && !TextUtils.isEmpty(gender) && !TextUtils.isEmpty(weight)) {
                    Height = Float.parseFloat(height);
                    stepGoals = Integer.parseInt(goals);
                    Weight = Float.parseFloat(weight);
                    Age = Integer.parseInt(age);
                    //walking and user field
                    stepGoals = Integer.parseInt(edtxtGoalSteps.getText().toString().trim());
                    user = new User(gender, Age, Weight, Height, stepGoals);
                    //add user in the arrayList
                    users.add(user);
                    clicked = true;
                    //Requires a new thread to avoid blocking the UI
                    new sendMessage(WEARABLE_DATA_PATH, dataMap).start();

                }
                //check if steps field and height are empty
                else if (TextUtils.isEmpty(goals) && TextUtils.isEmpty(height)) {
                    txtErrors.setText("Please enter all the field");
                } else {
                    txtErrors.setText("Please enter all the field");
                }
            }
        });

        return view;
    }
    //getting today date for the date picker dialog
    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);

    }

    //initialising datePicker
    private void DatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                datePickerBtn.setText(date);
                // age
                Calendar dobCalendar = Calendar.getInstance();
                dobCalendar.set(Calendar.YEAR, year);
                dobCalendar.set(Calendar.MONTH, month);
                dobCalendar.set(Calendar.DAY_OF_MONTH, day);
                age = Integer.toString(getAge(dobCalendar.getTimeInMillis()));
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    //String for date
    private String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    //month format for the date
    private String getMonthFormat(int month) {
        Calendar calendarObject = Calendar.getInstance();
        calendarObject.set(Calendar.MONTH, month);
        String monthName = calendarObject.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
        return monthName;
    }

    //show the datPickerDialog
    public void openDatePicker() {
        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
    }

    //calculating the age
    public int getAge(long date) {
        Calendar dobCalendar = Calendar.getInstance();
        dobCalendar.setTimeInMillis(date);
        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) == dobCalendar.get(Calendar.MONTH)) {
            if (today.get(Calendar.DAY_OF_MONTH) < dobCalendar.get(Calendar.DAY_OF_MONTH)) {

                age--;
            }
        } else if (today.get(Calendar.MONTH) < dobCalendar.get(Calendar.MONTH)) {

            age--;

        }


        return age;


    }

    //show the weight
    public void showWeight() {
        //weight
        edtxtWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogWeightFragment dialogWeightFragment = new DialogWeightFragment();
                dialogWeightFragment.show(getActivity().getSupportFragmentManager(), "WeightFragment");
                edtxtWeight.setClickable(true);
                sh = getActivity().getSharedPreferences("userInfoPref", MODE_PRIVATE);
                String firstKg = sh.getString("firstkg", "");
                String secondKg = sh.getString("secondKg", "");
                //heightCm = sh.getString("heightCm", "");
                edtxtWeight.setText(firstKg + "." + secondKg);
                weight = firstKg;


            }
        });

    }

    // Connect to the data layer when the Fragment starts
    @Override
    public void onStart() {
        super.onStart();
        googleClient.connect();
    }

    // Disconnect from the data layer when the Fragment stops
    @Override
    public void onStop() {
        if (null != googleClient && googleClient.isConnected()) {
            googleClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //receiving data from watch
    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle data = intent.getBundleExtra("datamap");
            // Display received data in UI
            //walking activity
            hours = data.getString("hours");
            minutes = data.getString("minutes");
            seconds = data.getString("seconds");
            distance = data.getString("distance");
            caloriesBurnt = data.getString("calories");
            heartRate = data.getString("heartRate");
            stepDetector = data.getString("stepDetector");
            pace = data.getString("pace");
            time = data.getString("time");
            currentDate = data.getString("current_date");
            // converting data types
            timer = hours + ":" + minutes + ":" + seconds;
            //running activity
            hoursRun = data.getString("hoursR");
            minutesRun = data.getString("minutesR");
            secondsRun = data.getString("secondsR");
            distanceRun = data.getString("distanceR");
            caloriesBurntRun = data.getString("caloriesR");
            heartRateRun = data.getString("heartRateR");
            stepDetectorRun = data.getString("stepDetectorR");
            paceRun = data.getString("paceR");
            timeRun = data.getString("timeR");
            currentDateRun = data.getString("current_dateR");
            timerRun = hoursRun + ":" + minutesRun + ":" + secondsRun;

            //arraylist
            walkingList = new ArrayList<>();
            runningList = new ArrayList<>();

            //Running Activity
            if (distanceRun != null && caloriesBurntRun != null && stepDetectorRun != null && paceRun != null) {
                DistanceRun = Double.parseDouble(distanceRun);
                CaloriesRun = Double.parseDouble(caloriesBurntRun);
                HeartRateRun = Math.round(Float.parseFloat(heartRateRun));
                StepDetectorRun = Integer.parseInt(stepDetectorRun);
                PaceRun = Double.parseDouble(paceRun);
                Running running = new Running(StepDetectorRun, DistanceRun, HeartRateRun, CaloriesRun, PaceRun, timerRun, currentDateRun, userId);
                runningList.add(running);
                userWithRunning = new UserWithRunning(user, runningList);
                runningViewModel.insertRunningWithUser(userWithRunning);
            } else {
                Log.d("errors1", "Something is null");
            }
            //walking Activity
            if (distance != null && caloriesBurnt != null && stepDetector != null && pace != null) {
                //Distance = Double.parseDouble(distance);
                Distance = Double.parseDouble(distance);
                Calories = Double.parseDouble(caloriesBurnt);
                HeartRate = Math.round(Float.parseFloat(heartRate));
                StepDetector = Integer.parseInt(stepDetector);
                Pace = Double.parseDouble(pace);
                Walking walking = new Walking(StepDetector, Distance, HeartRate, Calories, Pace, timer, currentDate, userId);
                walkingList.add(walking);
                userWithWalking = new UserWithWalking(user, walkingList);
                //insert inside the database the walking and user table
                walkingViewModel.insertWalkingWithUser(userWithWalking);
            } else {
                Log.d("error", "there is an error");
            }


        }

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


}