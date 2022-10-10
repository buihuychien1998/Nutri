package com.scopic.fxp.bean;

import androidx.room.PrimaryKey;

import com.google.firebase.database.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserData implements Serializable {
	private static final long serialVersionUID = -6954480707575231615L;
	
	@PrimaryKey
	@NotNull
	private Integer id;
	
	private List<String> boughtPremiumWorkouts;
	
	private int currentWeight;
	
	private Map<String, Long> fuelPoints;
	
	public UserData() {
		super();
		this.currentWeight = 125;
		this.boughtPremiumWorkouts = new ArrayList<String>();
		this.fuelPoints = new HashMap<String, Long>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<String> getBoughtPremiumWorkouts() {
		return boughtPremiumWorkouts;
	}

	public void setBoughtPremiumWorkouts(List<String> boughtPremiumWorkouts) {
		this.boughtPremiumWorkouts = boughtPremiumWorkouts;
	}
	
	public int getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(int currentWeight) {
		this.currentWeight = currentWeight;
	}

	public Map<String, Long> getFuelPoints() {
		return fuelPoints;
	}

	public void setFuelPoints(Map<String, Long> fuelPoints) {
		this.fuelPoints = fuelPoints;
	}
}
