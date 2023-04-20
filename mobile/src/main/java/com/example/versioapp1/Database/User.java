package com.example.versioapp1.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    public long userId;
    @ColumnInfo(name = "gender")
    public String gender;
    @ColumnInfo(name = "age")
    public int age;
    @ColumnInfo(name = "weight")
    public float weight;
    @ColumnInfo(name = "height")
    public float height;
    @ColumnInfo(name = "count_goal")
    public int countGoal;

    public User(String gender, int age , float weight, float height, int countGoal){
        this.userId = 0;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.countGoal = countGoal;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getCountGoal() {return countGoal;}

    public void setCountGoal(int countGoal) {this.countGoal = countGoal;}
}
