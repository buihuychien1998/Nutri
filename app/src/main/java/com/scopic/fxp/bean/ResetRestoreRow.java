package com.scopic.fxp.bean;


public class ResetRestoreRow {
	private String name;
	
	private String buttonName;
	
	public ResetRestoreRow(String name, String buttonName) {
		super();
		this.name = name;
		this.buttonName = buttonName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

}
