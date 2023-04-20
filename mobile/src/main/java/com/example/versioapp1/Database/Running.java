package com.example.versioapp1.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "running_activity", foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = {"user_id"},
                childColumns = {"user_fId"},
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
})
public class Running {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "running_id")
    public long runningId;
    @ColumnInfo(name = "steps_number")
    public int steps;
    @ColumnInfo(name = "distance")
    public double distance;
    @ColumnInfo(name = "heart_rate")
    public int heartRate;
    @ColumnInfo(name = "calories_burnt")
    public double caloriesBurnt;
    @ColumnInfo(name = "pace")
    public double pace;
    @ColumnInfo(name = "running_timer")
    public String timer;
    @ColumnInfo(name = "current_date")
    public String currentDate;
    @ColumnInfo(name = "user_fId")
    public long userId;

    public Running( int steps, double distance, int heartRate, double caloriesBurnt, double pace, String timer, String currentDate,long userId) {
        this.runningId = 0;
        this.steps = steps;
        this.distance = distance;
        this.heartRate = heartRate;
        this.caloriesBurnt = caloriesBurnt;
        this.pace = pace;
        this.timer = timer;
        this.currentDate = currentDate;
        this.userId = userId;
    }


    public long getRunningId() {
        return runningId;
    }

    public void setRunningId(long runningId) {
        this.runningId = runningId;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public double getCaloriesBurnt() {
        return caloriesBurnt;
    }

    public void setCaloriesBurnt(double caloriesBurnt) {
        this.caloriesBurnt = caloriesBurnt;
    }

    public double getPace() {
        return pace;
    }

    public void setPace(double pace) {
        this.pace = pace;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
