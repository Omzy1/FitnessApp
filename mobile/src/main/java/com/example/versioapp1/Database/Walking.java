package com.example.versioapp1.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "walking_activity",foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = {"user_id"},
                childColumns = {"user_fId"},
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        )
})
public class Walking {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "walking_id")
    public long walkingId;
    @ColumnInfo(name = "number_steps")
    public int StepsNumber;
    @ColumnInfo(name = "distance")
    public double Distance;
    @ColumnInfo(name = "heart_rate")
    public int HeartRate;
    @ColumnInfo(name = "calories_burnt")
    public double CaloriesBurnt;
    @ColumnInfo(name = "pace")
    public double pace;
    @ColumnInfo(name = "activity_timer")
    public String timer;
    @ColumnInfo(name = "current_date")
    public String currentDate;
    @ColumnInfo(name = "user_fId")
    public long userId;

    public Walking(int stepsNumber, double distance, int heartRate, double caloriesBurnt,double pace, String timer,String currentDate,long userId ) {
        this.walkingId = 0;
        StepsNumber = stepsNumber;
        Distance = distance;
        HeartRate = heartRate;
        CaloriesBurnt = caloriesBurnt;
        this.pace = pace;
        this.timer = timer;
        this.currentDate = currentDate;
        this.userId = userId;

    }
    public Walking(){}

    public long getWalkingId() {
        return walkingId;
    }

    public void setWalkingId(long walkingId) {
        this.walkingId = walkingId;
    }

    public int getStepsNumber() {
        return StepsNumber;
    }

    public void setStepsNumber(int stepsNumber) {
        StepsNumber = stepsNumber;
    }

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

    public int getHeartRate() {
        return HeartRate;
    }

    public void setHeartRate(int heartRate) {
        HeartRate = heartRate;
    }

    public double getCaloriesBurnt() {
        return CaloriesBurnt;
    }

    public void setCaloriesBurnt(double caloriesBurnt) {
        CaloriesBurnt = caloriesBurnt;
    }

    public double getPace() {
        return pace;
    }

    public void setPace(double pace) {this.pace = pace;}

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {this.currentDate = currentDate;}

    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
}