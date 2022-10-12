package com.mteam.nutri.bean;

import java.util.List;

public class NutritionCategoryBean {

	private String name;
	private String iconId;
	private String description;
	private List<String> healthyChoices;
	
	public NutritionCategoryBean (String name, String iconId, String description, List<String> healthyChoices) {
		this.name = name;
		this.iconId = iconId;
		this.description = description;
		this.healthyChoices = healthyChoices;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the iconId
	 */
	public String getIconId() {
		return iconId;
	}

	/**
	 * @param iconId the iconId to set
	 */
	public void setIconId(String iconId) {
		this.iconId = iconId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the healthyChoices
	 */
	public List<String> getHealthyChoices() {
		return healthyChoices;
	}

	/**
	 * @param healthyChoices the healthyChoices to set
	 */
	public void setHealthyChoices(List<String> healthyChoices) {
		this.healthyChoices = healthyChoices;
	}

	
}
