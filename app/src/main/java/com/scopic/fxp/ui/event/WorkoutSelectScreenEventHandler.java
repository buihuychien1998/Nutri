package com.scopic.fxp.ui.event;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.ActivityMainWorkoutList;
import com.scopic.fxp.ui.screen.ActivityPremiumWorkoutList;
import com.scopic.fxp.ui.screen.ActivityWorkoutDetail;
import com.scopic.fxp.ui.screen.ActivityWorkoutSelect;

public class WorkoutSelectScreenEventHandler implements OnClickListener {

	private FragmentManager fragmentManager;
	
	ActivityWorkoutSelect activityWorkoutSelect;

	public WorkoutSelectScreenEventHandler(ActivityWorkoutSelect activityWorkoutSelect) {
		super();
		
		this.activityWorkoutSelect = activityWorkoutSelect;

		fragmentManager = activityWorkoutSelect.getActivity().getSupportFragmentManager();
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.imgBack:
			back(transaction);
			break;
			
		case R.id.btnStartWorkout:
			startWorkout(transaction);
			break;
		}

		// Commit the transaction
		transaction.commit();
	}

	private void back(FragmentTransaction transaction) {
		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());
		if(FxpApp.isMainWorkout) {
			transaction.replace(R.id.fragment, new ActivityMainWorkoutList(), Params.MAIN_WORKOUT_LIST_SCREEN);
		} else {
			transaction.replace(R.id.fragment, new ActivityPremiumWorkoutList(), Params.PREMIUM_WORKOUT_LIST_SCREEN);
		}
	}

	private void startWorkout(FragmentTransaction transaction) {
		ActivityWorkoutDetail activityWorkoutDetail = new ActivityWorkoutDetail();
		transaction.replace(R.id.fragment, activityWorkoutDetail, Params.WORKOUT_DETAIL_SCREEN);
	}
}
