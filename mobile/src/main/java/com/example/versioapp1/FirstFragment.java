package com.example.versioapp1;

import android.app.Dialog;
import android.app.UiModeManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.versioapp1.Database.Running;
import com.example.versioapp1.Database.RunningViewModel;
import com.example.versioapp1.Database.User;
import com.example.versioapp1.Database.Walking;
import com.example.versioapp1.Database.WalkingViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    WalkingViewModel walkingViewModel;
    RunningViewModel runningViewModel;
    private int steps = 0;
    private int runSteps = 0;
    private int countGoal = 0;
    String TodayDate;
    ProgressBar progressBar1;
    ProgressBar progressBar2;
    User lastUser;
    private TextView txtSteps,txtPercentuage,txtDisplaySteps;
    private TextView txtDateWalk, txtTimerWalking,txtCalWalking,txtStepsWalk;
    private TextView txtDistanceWalk,txtHeartRateWalk,txtPaceWalk;
    private TextView txtDateRun,txtTimerRunning,txtCalRunning,txtStepsRun;
    private TextView txtDistanceRun,txtHeartRateRun,txtPaceRun;
    private TextView minutesDate,txtMinutes;
    private ImageView imgFaces,imgFaceNHS;
    private ImageView ImgInstruction,imgLogo;
    private Dialog InstructionDialog;
    private int result = 0;
    private int result1=0;
    private ImageView imgFace,imgShoes,imgWalk,imgRun;
    private String date, timer;
    private double calories;
    private double pace;
    private int walkSteps;
    private double distanceWalk;
    private int heartRateWalk;
    private String dateRun,timerRun;
    private double caloriesRun, paceRun, distanceRun;
    private int stepsRunning,heartRateRun;
    private PieChart pieChart;
    private int minutes=0;
    private int hours =0;
    private int seconds = 0;
    private int minutesRun=0;
    private int hoursRun =0;
    private int secondsRun = 0;
    private UiModeManager uiModeManager;
    private int percentageSteps =0;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
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
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //ImageView
        imgFace= view.findViewById(R.id.imgFace);
        imgFaceNHS = view.findViewById(R.id.imgFaces);
        imgShoes = view.findViewById(R.id.imgShoes);
        imgRun = view.findViewById(R.id.imgRun);
        imgWalk = view.findViewById(R.id.imgWalk);
        imgLogo = view.findViewById(R.id.imgLogo);
        //TextView
        txtSteps = view.findViewById(R.id.txtCurrentSteps);
        txtPercentuage = view.findViewById(R.id.txtPercentuage);
        txtDisplaySteps = view.findViewById(R.id.txtDisplaySteps);
        //latest walk activity
        txtDateWalk = view.findViewById(R.id.txtDateWalk);
        txtTimerWalking = view.findViewById(R.id.txtTimerWalking);
        txtCalWalking  = view.findViewById(R.id.txtCalWalking);
        txtStepsWalk = view.findViewById(R.id.txtStepsWalk);
        txtDistanceWalk = view.findViewById(R.id.txtDistanceWalk);
        txtHeartRateWalk = view.findViewById(R.id.txtHeartRateWalk);
        txtPaceWalk = view.findViewById(R.id.txtPaceWalk);
        //latest run activity
        txtDateRun = view.findViewById(R.id.txtDateRun);
        txtCalRunning = view.findViewById(R.id.txtCalRunning);
        txtTimerRunning = view.findViewById(R.id.txtTimerRunning);
        txtPaceRun = view.findViewById(R.id.txtPaceRun);
        txtDistanceRun = view.findViewById(R.id.txtDistanceRun);
        txtHeartRateRun = view.findViewById(R.id.txtHeartRateRun);
        txtStepsRun = view.findViewById(R.id.txtStepsRun);
        //NHS recommendation
        //TextView
        minutesDate = view.findViewById(R.id.minutesDate);
        txtMinutes = view.findViewById(R.id.txtMinutes);
        //ImageView
        imgFaces = view.findViewById(R.id.imgFace);
         uiModeManager = (UiModeManager)requireActivity().getSystemService(Context.UI_MODE_SERVICE);
        //ProgressBar
        progressBar1 = view.findViewById(R.id.progressBar1);
        progressBar2 = view.findViewById(R.id.progressBar2);
        //BarChart
        pieChart = view.findViewById(R.id.chart1);
        //ImageView;
        ImgInstruction = view.findViewById(R.id.ImgInstruction);
        //Dialog
        InstructionDialog = new Dialog(getContext());
       //show instruction popup window
        ImgInstruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InstructionDialog.setContentView(R.layout.instruction_popup);
                ImageView imgExit;
                imgExit = InstructionDialog.findViewById(R.id.imgExit);
                imgExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        InstructionDialog.dismiss();
                    }
                });
                InstructionDialog.show();
            }
        });


        //initialising viewModels
        walkingViewModel = new ViewModelProvider(FirstFragment.this).get(WalkingViewModel.class);
        runningViewModel = new ViewModelProvider(FirstFragment.this).get(RunningViewModel.class);

        //today date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        TodayDate = dateFormat.format(calendar.getTime());
        walkingViewModel.getAllWalking().observe(getViewLifecycleOwner(), new Observer<List<Walking>>() {
            @Override
            public void onChanged(List<Walking> walkings){
                if (!walkings.isEmpty()) {
                    result=0;
                    minutes=0;
                    hours=0;
                    seconds=0;
                    Walking lastWalking = walkings.get(0);
                    // check if the date of the last activity is the same as today
                    if(lastWalking.getCurrentDate().equals(TodayDate))
                    {
                        for(Walking walking: walkings){
                            if(walking.getCurrentDate().equals(TodayDate))
                            {
                                steps= walking.getStepsNumber();
                                result+=steps;
                                Log.d("steps",""+steps);
                                //minutes
                                String sMinute = walking.getTimer();
                                String [] timeParts = sMinute.split(":");
                                //check if the hours is bigger then 0
                                if(Integer.parseInt(timeParts[0])>0){
                                   hours = Integer.parseInt(timeParts[0])*60;
                                   Log.d("hours", ""+hours);
                                }
                                //check if minutes is bigger then 0
                                if(Integer.parseInt(timeParts[2])>0){
                                    seconds = Integer.parseInt(timeParts[2])/60;
                                }
                                minutes += Integer.parseInt(timeParts[1])+ hours + seconds;

                            }
                    }

                    }
                    else if(!lastWalking.getCurrentDate().equals(TodayDate)){
                        steps = 0;
                        result=0;
                    }
                    //getting the latest activity to display
                   date = lastWalking.getCurrentDate();
                    timer = lastWalking.getTimer();
                    walkSteps = lastWalking.getStepsNumber();
                    calories = lastWalking.getCaloriesBurnt();
                    pace = lastWalking.getPace();
                    distanceWalk = lastWalking.getDistance();
                    heartRateWalk = lastWalking.getHeartRate();


                }
            }

        });
        runningViewModel.getAllRunning().observe(getViewLifecycleOwner(), new Observer<List<Running>>() {
            @Override
            public void onChanged(List<Running> runningList) {
                if (!runningList.isEmpty()) {
                    result1=0;
                    minutesRun=0;
                    secondsRun=0;
                    hoursRun=0;
                    Running lastRunning = runningList.get(0);
                    if (lastRunning.getCurrentDate().equals(TodayDate)) {
                        for (Running running : runningList) {
                            if (running.getCurrentDate().equals(TodayDate)) {
                                runSteps = running.getSteps();
                                result1 += runSteps;
                                //minutes
                                String sMinute = running.getTimer();
                                String [] timeParts = sMinute.split(":");
                                //check if the hours is bigger then 0
                                if(Integer.parseInt(timeParts[0])>0){
                                    hoursRun = Integer.parseInt(timeParts[0])*60;
                                    Log.d("hours", ""+hoursRun);
                                }
                                //check if minutes is bigger then 0
                                if(Integer.parseInt(timeParts[2])>0){
                                    secondsRun = Integer.parseInt(timeParts[2])/60;
                                }
                                minutesRun += Integer.parseInt(timeParts[1])+ hoursRun + secondsRun;

                            }
                        }
                    }
                    else if(!lastRunning.getCurrentDate().equals(TodayDate)){
                            runSteps = 0;
                            result1 = runSteps;

                        }
                    //getting the latest activity data
                    dateRun=lastRunning.getCurrentDate();
                    timerRun= lastRunning.getTimer();
                    stepsRunning = lastRunning.getSteps();
                    caloriesRun = lastRunning.getCaloriesBurnt();
                    paceRun = lastRunning.getPace();
                    distanceRun = lastRunning.getDistance();
                    heartRateRun = lastRunning.getHeartRate();

                    }
                }
        });
        //observing table user
        walkingViewModel.getAllUser().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if(!users.isEmpty()) {
                    lastUser = users.get(users.size() - 1);
                    countGoal = lastUser.getCountGoal();
                }
            }
        });

        // Retrieve the value of "steps" after a short delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //add inside the result walking ,the result running
                 result+= result1;

                Log.d("hello", "" + result);
                Log.d("count",""+ countGoal);
                progressBar1.setProgress(result);
                progressBar1.setMax(countGoal);
                txtSteps.setText(String.valueOf(result));
                txtDisplaySteps.setText(result+ "/" + countGoal);
                //calculate the percentage value
                if(result!=0 && countGoal!=0) {
                    percentageSteps = (result / countGoal) * 100;
                }
                //display steps percentage
                if(percentageSteps<1)
                {
                    txtPercentuage.setText("<1%");
                }else {
                    txtPercentuage.setText(percentageSteps + "%");
                }
                //show state face
                if(percentageSteps<20){
                    imgFace.setImageResource(R.drawable.ic_sad_face);
                }else if(percentageSteps <60)
                {
                    imgFace.setImageResource(R.drawable.ic_normal_face);
                }else {
                    imgFace.setImageResource(R.drawable.ic_smiley_face);
                }
                //setting the values in the second progress bar
                progressBar2.setProgress(percentageSteps);
                progressBar2.setMax(100);
                //displaying latest walking activity
                txtDateWalk.setText(date);
                txtTimerWalking.setText(timer);
                txtCalWalking.setText(String.valueOf((Double.parseDouble(String.format("%.2f",calories)))) + " cal");
                txtDistanceWalk.setText(String.valueOf(distanceWalk) + " km");
                txtHeartRateWalk.setText(String.valueOf(heartRateWalk) + " BMP");
                txtPaceWalk.setText(String.valueOf(Double.parseDouble(String.format("%.2f",pace))) +" kmh");
                txtStepsWalk.setText(String.valueOf(walkSteps)+" steps");

                //displaying latest running activity
                txtDateRun.setText(dateRun);
                txtTimerRunning.setText(timerRun);
                txtCalRunning.setText(String.valueOf((Double.parseDouble(String.format("%.2f",caloriesRun)))) + " cal");
                txtDistanceRun.setText(String.valueOf(distanceRun) + " km");
                txtHeartRateRun.setText(String.valueOf(heartRateRun) + " BMP");
                txtPaceRun.setText(String.valueOf(Double.parseDouble(String.format("%.2f",paceRun))) +" kmh");
                txtStepsRun.setText(String.valueOf(stepsRunning)+" steps");

                // Bar chart
                ArrayList<PieEntry> pieEntries = new ArrayList<>();
                float numberSteps = Float.parseFloat(String.valueOf(result));
                PieEntry pieEntry= new PieEntry(numberSteps);
                PieEntry pieEntry2= new PieEntry(countGoal);
                pieEntries.add(pieEntry);
                pieEntries.add(pieEntry2);
                PieDataSet pieDataSet = new PieDataSet(pieEntries,"Steps");
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                pieChart.setData(new PieData(pieDataSet));
                pieChart.animateXY(5000,5000);
                pieChart.getDescription().setEnabled(false);

                //NHS Recommendation
                minutesDate.setText(String.valueOf(TodayDate));
                int minutesActivity = minutes + (minutesRun*2);
                txtMinutes.setText(""+minutesActivity+ " / " + "10 min");
                //change face mood based your daily walking achievement
                if(minutesActivity<10){
                    imgFaceNHS.setImageResource(R.drawable.ic_sad_face);
                }else {
                    imgFaceNHS.setImageResource(R.drawable.ic_smiley_face);
                    Toast.makeText(getContext(), "Congratulation!", Toast.LENGTH_SHORT).show();
                }

                //reset the steps to 0
                result=0;
                //change the colour of imageView on Dark Mode
                DarkModeColours();
            }
        }, 100);



        return view;
    }
    public void DarkModeColours(){
        if (uiModeManager.getNightMode() == UiModeManager.MODE_NIGHT_YES) {
            //imageView.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            imgFaceNHS.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            imgFace.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            imgWalk.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            imgShoes.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            imgRun.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            imgLogo.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            ImgInstruction.setColorFilter(Color.WHITE,PorterDuff.Mode.SRC_IN);



        }
    }




   @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

}