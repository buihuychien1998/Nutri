package com.scopic.fxp.ui.event;

import android.view.View;
import android.view.View.OnClickListener;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.ActivityMainWorkoutList;
import com.scopic.fxp.ui.screen.ActivityMenu;
import com.scopic.fxp.ui.screen.ActivityPremiumWorkoutList;

public class MenuScreenEventHandler implements OnClickListener {

	private FragmentManager fragmentManager;

	public MenuScreenEventHandler(ActivityMenu activityMenu) {
		super();

		fragmentManager = activityMenu.getActivity().getSupportFragmentManager();
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		
		switch (v.getId()) {
		case R.id.layoutMain:
			openMainWorkoutList(transaction);
			break;
		case R.id.layoutPremium:
			openPremiumWorkoutList(transaction);
			break;
		}
		
		// Commit the transaction
		transaction.commit();
	}

	private void openMainWorkoutList(FragmentTransaction transaction) {
		FxpApp.isMainWorkout = true;
		transaction.replace(R.id.fragment, new ActivityMainWorkoutList(), Params.MAIN_WORKOUT_LIST_SCREEN);
	}
	
	private void openPremiumWorkoutList(FragmentTransaction transaction) {
		FxpApp.isMainWorkout = false;
		transaction.replace(R.id.fragment, new ActivityPremiumWorkoutList(), Params.PREMIUM_WORKOUT_LIST_SCREEN);
	}

}
