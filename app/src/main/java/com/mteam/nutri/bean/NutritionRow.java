package com.mteam.nutri.bean;

public class NutritionRow {
	private String name;
	
	private String subName;
	
	private int logo;
	
	public NutritionRow(String name, int logo, String subName) {
		super();
		this.name = name;
		this.logo = logo;
		this.subName = subName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLogo() {
		return logo;
	}

	public void setLogo(int logo) {
		this.logo = logo;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

}
