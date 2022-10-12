package com.mteam.nutri.bean;

public class FuelRow {
	private String day;
	
	private String point;
	
	public FuelRow(String day, String point) {
		super();
		this.day = day;
		this.point = point;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

}
