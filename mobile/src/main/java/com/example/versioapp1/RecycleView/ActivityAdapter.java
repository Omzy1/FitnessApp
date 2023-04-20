package com.example.versioapp1.RecycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.versioapp1.Database.Walking;
import com.example.versioapp1.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivtyHolder> {
    private List<Walking> walkingActivities = new ArrayList<>();

    @NonNull
    @Override
    public ActivtyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activities_items, parent, false);
        return new ActivtyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivtyHolder holder, int position) {

        if (walkingActivities != null) {
            Walking currentWalkingAct = walkingActivities.get(position);

            holder.txtCurrentDate.setText(currentWalkingAct.getCurrentDate());
            holder.txtTime.setText(currentWalkingAct.getTimer());
            holder.txtDistanceTimer.setText(String.valueOf(currentWalkingAct.getDistance()) + " km");
            holder.txtNameActivity.setText("Walking");
            holder.txtSteps.setText(String.valueOf(currentWalkingAct.getStepsNumber()) + " steps");
            holder.txtHeartRate.setText(String.valueOf(currentWalkingAct.getHeartRate()) + " BMP");
            holder.txtCalories.setText(String.valueOf(Double.parseDouble(String.format("%.2f", currentWalkingAct.getCaloriesBurnt()))) + " cal");
            holder.txtPace.setText(String.valueOf(Double.parseDouble(String.format("%.2f", currentWalkingAct.getPace()))) + "Kmh");

        }


    }


    @Override
    public int getItemCount() {
        return walkingActivities.size();
    }

    public void setWalkingActivities(List<Walking> walking_act) {
        this.walkingActivities = walking_act;
        notifyDataSetChanged();

    }

    public void setFilteredList(List<Walking> filteredList) {
        this.walkingActivities = filteredList;
        notifyDataSetChanged();
    }

    class ActivtyHolder extends RecyclerView.ViewHolder {
        private TextView txtCurrentDate, txtTime, txtNameActivity, txtDistanceTimer;
        private TextView txtSteps, txtCalories, txtHeartRate, txtPace;

        public ActivtyHolder(@NonNull View itemView) {
            super(itemView);
            txtCurrentDate = itemView.findViewById(R.id.txtCurrentDate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtNameActivity = itemView.findViewById(R.id.txtNameActivity);
            txtDistanceTimer = itemView.findViewById(R.id.txtDistanceTimer);
            txtSteps = itemView.findViewById(R.id.txtSteps);
            txtCalories = itemView.findViewById(R.id.txtCalories);
            txtHeartRate = itemView.findViewById(R.id.txtHeartRate);
            txtPace = itemView.findViewById(R.id.txtPace);


        }
    }
}
