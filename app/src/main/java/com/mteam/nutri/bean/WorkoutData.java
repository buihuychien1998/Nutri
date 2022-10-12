package com.mteam.nutri.bean;

import androidx.room.PrimaryKey;

import com.google.firebase.database.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkoutData implements Serializable {
	private static final long serialVersionUID = -6954480707575231615L;
	
	@PrimaryKey
	@NotNull
	private String workoutName;
	
	private String completedDays;
	
	private int currentDay;
	
	private List<Long> fuelPoints;
	
	public WorkoutData() {
		super();
		this.completedDays = "";
		this.currentDay = 0;
		this.fuelPoints = new ArrayList<Long>();
	}

	public String getWorkoutName() {
		return workoutName;
	}

	public void setWorkoutName(String workoutName) {
		this.workoutName = workoutName;
	}

	public String getCompletedDays() {
		return completedDays;
	}

	public void setCompletedDays(String completedDays) {
		this.completedDays = completedDays;
	}

	public int getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}

	public List<Long> getFuelPoints() {
		return fuelPoints;
	}

	public void setFuelPoints(List<Long> fuelPoints) {
		this.fuelPoints = fuelPoints;
	}
}
