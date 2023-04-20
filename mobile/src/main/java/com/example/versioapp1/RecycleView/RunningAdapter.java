package com.example.versioapp1.RecycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.versioapp1.Database.Running;
import com.example.versioapp1.R;

import java.util.ArrayList;
import java.util.List;

public class RunningAdapter extends RecyclerView.Adapter<RunningAdapter.RunningHolder> {
    private List<Running> runningActivities = new ArrayList<>();

    @NonNull
    @Override
    public RunningHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activities_items, parent, false);
        return new RunningHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RunningHolder holder, int position) {
        if (runningActivities != null) {
            Running currentRunningAct = runningActivities.get(position);
            holder.txtCurrentDate.setText(currentRunningAct.getCurrentDate());
            holder.txtTime.setText(currentRunningAct.getTimer());
            holder.txtDistanceTimer.setText(String.valueOf(currentRunningAct.getDistance()) + " km");
            holder.txtNameActivity.setText("Running");
            holder.txtSteps.setText(String.valueOf(currentRunningAct.getSteps()) + " steps");
            holder.txtHeartRate.setText(String.valueOf(currentRunningAct.getHeartRate()) + " BMP");
            holder.txtCalories.setText(String.valueOf(Double.parseDouble(String.format("%.2f", currentRunningAct.getCaloriesBurnt()))) + " cal");
            holder.txtPace.setText(String.valueOf(Double.parseDouble(String.format("%.2f", currentRunningAct.getPace()))) + "Kmh");
            holder.imgActivity.setImageResource(R.drawable.ic_run);

        }


    }

    @Override
    public int getItemCount() {
        return runningActivities.size();
    }

    public void setRunningActivities(List<Running> running_act) {
        this.runningActivities = running_act;
        notifyDataSetChanged();

    }

    public void setFilteredList(List<Running> filteredList) {
        this.runningActivities = filteredList;
        notifyDataSetChanged();
    }

    class RunningHolder extends RecyclerView.ViewHolder {
        private TextView txtCurrentDate, txtTime, txtNameActivity, txtDistanceTimer;
        private TextView txtSteps, txtCalories, txtHeartRate, txtPace;
        private ImageView imgActivity;

        public RunningHolder(@NonNull View itemView) {
            super(itemView);
            txtCurrentDate = itemView.findViewById(R.id.txtCurrentDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtNameActivity = itemView.findViewById(R.id.txtNameActivity);
            txtDistanceTimer = itemView.findViewById(R.id.txtDistanceTimer);
            txtSteps = itemView.findViewById(R.id.txtSteps);
            txtCalories = itemView.findViewById(R.id.txtCalories);
            txtHeartRate = itemView.findViewById(R.id.txtHeartRate);
            txtPace = itemView.findViewById(R.id.txtPace);
            imgActivity = itemView.findViewById(R.id.imgActivity);


        }
    }
}
