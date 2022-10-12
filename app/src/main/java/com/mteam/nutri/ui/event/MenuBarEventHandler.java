package com.mteam.nutri.ui.event;

import android.view.View;
import android.view.View.OnClickListener;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mteam.nutri.ui.commons.Params;
import com.mteam.nutri.ui.screen.ActivityMenu;
import com.mteam.nutri.ui.screen.ActivityNutritionList;
import com.mteam.nutri.ui.screen.ActivitySettings;
import com.mteam.nutri.ui.screen.MenuBarFragment;
import com.mteam.nutri.R;

public class MenuBarEventHandler implements OnClickListener {

	private MenuBarFragment menuBarFragment;
	
	private FragmentManager fragmentManager;
	
	public MenuBarEventHandler(MenuBarFragment menuBarFragment) {
		super();
		this.menuBarFragment = menuBarFragment;
		
		fragmentManager = menuBarFragment.getActivity().getSupportFragmentManager();
	}

	@Override
	public void onClick(View v) {
		menuBarFragment.getCurrentViewSelected().setSelected(false);
		menuBarFragment.setCurrentViewSelected(v);
		
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		switch (v.getId()) {
		case R.id.btnHome:
			openHome(transaction);
			break;
//		case R.id.btnExercises:
//			openExercises(transaction);
//			break;
		case R.id.btnNutrition:
			openNutrition(transaction);
			break;
		case R.id.btnSettings:
			openSettings(transaction);
			break;
		}
		
		// Commit the transaction
		transaction.commit();
	}

	private void openHome(FragmentTransaction transaction) {
		transaction.replace(R.id.fragment, new ActivityMenu(), Params.HOME_SCREEN);
		menuBarFragment.getHome().setSelected(true);
	}

//	private void openExercises(FragmentTransaction transaction) {
//		transaction.replace(R.id.fragment, new ActivityExerciseList(), Params.EXERCISES_SCREEN);
//		menuBarFragment.getExercises().setSelected(true);
//	}
	
	private void openNutrition(FragmentTransaction transaction) {
		transaction.replace(R.id.fragment, new ActivityNutritionList(), Params.NUTRITION_SCREEN);
		menuBarFragment.getNutrition().setSelected(true);
	}

	private void openSettings(FragmentTransaction transaction) {
		transaction.replace(R.id.fragment, new ActivitySettings(), Params.SETTINGS_SCREEN);
		menuBarFragment.getSettings().setSelected(true);
	}
}
