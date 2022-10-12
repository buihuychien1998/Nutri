package com.mteam.nutri.bean;

import java.util.List;

public class ExerciseBean {

	private String name;
	private String id;
	private String block;
	private String note;
	private String setOrRep;
	private String description;
	private List<String> muscleGroupItTargets;
	private String youtubeURL;
	private String level;
	private String focus;
	private String time;
	
	public ExerciseBean ( String name, String block, String setOrRep, String note, String time) {
		this.name = name;
		this.block = block;
		this.setOrRep = setOrRep;
		this.note = note;
		this.time = time;
	}
	
	public ExerciseBean ( String id, String name, String description, String youtubeURL, String note, String block, String level, String focus, List<String> muscleGroupItTargets) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.youtubeURL = youtubeURL;
		this.note = note;
		this.block = block;
		this.level = level;
		this.focus = focus;
		this.muscleGroupItTargets = muscleGroupItTargets;
		
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
	 * @return the block
	 */
	public String getBlock() {
		return block;
	}
	/**
	 * @param block the block to set
	 */
	public void setBlock(String block) {
		this.block = block;
	}
	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}
	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * @return the setOrRep
	 */
	public String getSetOrRep() {
		return setOrRep;
	}
	/**
	 * @param setOrRep the setOrRep to set
	 */
	public void setSetOrRep(String setOrRep) {
		this.setOrRep = setOrRep;
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
	 * @return the muscleGroupItTargets
	 */
	public List<String> getMuscleGroupItTargets() {
		return muscleGroupItTargets;
	}
	/**
	 * @param muscleGroupItTargets the muscleGroupItTargets to set
	 */
	public void setMuscleGroupItTargets(List<String> muscleGroupItTargets) {
		this.muscleGroupItTargets = muscleGroupItTargets;
	}
	/**
	 * @return the youtubeURL
	 */
	public String getYoutubeURL() {
		return youtubeURL;
	}
	/**
	 * @param youtubeURL the youtubeURL to set
	 */
	public void setYoutubeURL(String youtubeURL) {
		this.youtubeURL = youtubeURL;
	}
	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @return the focus
	 */
	public String getFocus() {
		return focus;
	}
	/**
	 * @param focus the focus to set
	 */
	public void setFocus(String focus) {
		this.focus = focus;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
