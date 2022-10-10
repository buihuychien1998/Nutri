package com.scopic.fxp.ui.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.ui.event.MenuBarEventHandler;

public class MenuBarFragment extends AbstractFragment  {

	private View mHome;
	
	private View mExercises;
	
	private View mNutrition;
	
	private View mSettings;
	
	private View mCurrentViewSelected;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menu_bar,
		        container, false);
		
		mHome = view.findViewById(R.id.btnHome);
		mExercises = view.findViewById(R.id.btnExercises);
		mNutrition = view.findViewById(R.id.btnNutrition);
		mSettings = view.findViewById(R.id.btnSettings);
		
		MenuBarEventHandler eventHandler = new MenuBarEventHandler(this); 
		mHome.setOnClickListener(eventHandler);
		mExercises.setOnClickListener(eventHandler);
		mNutrition.setOnClickListener(eventHandler);
		mSettings.setOnClickListener(eventHandler);
		
		View[] buttons = {mHome, mExercises, mNutrition, mSettings};
		if(FxpApp.menuBar != null && FxpApp.menuBar.getCurrentViewSelected() != null) {
			for (View button : buttons) {
				if(button.getId() == FxpApp.menuBar
						.getCurrentViewSelected().getId()) {
					button.setSelected(true);
					this.setCurrentViewSelected(button);
				}
			}
		}
		
		return view;
	}

	public View getHome() {
		return mHome;
	}

	public void setHome(View mHome) {
		this.mHome = mHome;
	}

	public View getExercises() {
		return mExercises;
	}

	public void setExercises(View mExercises) {
		this.mExercises = mExercises;
	}

	public View getNutrition() {
		return mNutrition;
	}

	public void setNutrition(View mNutrition) {
		this.mNutrition = mNutrition;
	}

	public View getSettings() {
		return mSettings;
	}

	public void setSettings(View mSettings) {
		this.mSettings = mSettings;
	}

	public View getCurrentViewSelected() {
		return mCurrentViewSelected;
	}

	public void setCurrentViewSelected(View currentViewSelected) {
		this.mCurrentViewSelected = currentViewSelected;
		this.mCurrentViewSelected.setSelected(true);
	}

}
