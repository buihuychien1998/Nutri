package com.mteam.nutri.bean;

import java.util.List;

public class WorkoutDay {

	private String name;
	private String title;
	private String description;
	private String completeDescription;
	private List<ExerciseBean> exerciseList;

	public WorkoutDay (String name, String title, String description, String completeDescription) {
		this.name = name;
		this.title = title;
		this.description = description;
		this.completeDescription = completeDescription;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the completeDescription
	 */
	public String getCompleteDescription() {
		return completeDescription;
	}

	/**
	 * @param completeDescription the completeDescription to set
	 */
	public void setCompleteDescription(String completeDescription) {
		this.completeDescription = completeDescription;
	}

	/**
	 * @return the exerciseList
	 */
	public List<ExerciseBean> getExerciseList() {
		return exerciseList;
	}

	/**
	 * @param exerciseList the exerciseList to set
	 */
	public void setExerciseList(List<ExerciseBean> exerciseList) {
		this.exerciseList = exerciseList;
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
	
	
}
