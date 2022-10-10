package com.scopic.fxp.bean;

public class WorkoutRow {
	private String name;
	
	private int logo;
	
	private String purchaseId;
	
	public WorkoutRow(String name, int logo) {
		super();
		this.name = name;
		this.logo = logo;
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

	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}
}
