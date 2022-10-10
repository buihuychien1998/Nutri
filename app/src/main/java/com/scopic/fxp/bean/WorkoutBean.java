package com.scopic.fxp.bean;

import java.util.List;

public class WorkoutBean {

	private List<WorkoutDay> dayList;
	
	private String purchaseId;
	
	private String purchaseDescription;
	
	public WorkoutBean (List<WorkoutDay> dayList) {
		this.dayList = dayList;
	}

	/**
	 * @return the dayList
	 */
	public List<WorkoutDay> getDayList() {
		return dayList;
	}

	/**
	 * @param dayList the dayList to set
	 */
	public void setDayList(List<WorkoutDay> dayList) {
		this.dayList = dayList;
	}

	/**
	 * @return the purchaseId
	 */
	public String getPurchaseId() {
		return purchaseId;
	}

	/**
	 * @param purchaseId the purchaseId to set
	 */
	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	/**
	 * @return the purchaseDescription
	 */
	public String getPurchaseDescription() {
		return purchaseDescription;
	}

	/**
	 * @param purchaseDescription the purchaseDescription to set
	 */
	public void setPurchaseDescription(String purchaseDescription) {
		this.purchaseDescription = purchaseDescription;
	}
}
