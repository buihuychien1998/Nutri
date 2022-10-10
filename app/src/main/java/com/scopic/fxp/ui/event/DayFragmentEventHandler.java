package com.scopic.fxp.ui.event;

import android.view.View;
import android.view.View.OnClickListener;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.ui.screen.AbstractFragment;
import com.scopic.fxp.ui.screen.ActivityWorkoutSelect;
import com.scopic.fxp.ui.screen.DayFragment;

public class DayFragmentEventHandler implements OnClickListener {

	private FragmentManager fragmentManager;
	
	private AbstractFragment fragment;
	
	public DayFragmentEventHandler(AbstractFragment fragment, int pageNumber) {
		super();
		
		this.fragment = fragment;
		fragmentManager = fragment.getActivity().getSupportFragmentManager();
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		
		switch (v.getId()) {
		case R.id.txtDay:
			selectDay(v, transaction);
			break;
		}
		
		// Commit the transaction
		transaction.commit();
	}

	private void selectDay(View v, FragmentTransaction transaction) {
		DayFragment dayFragment = (DayFragment) fragment;
		int currentDay = Integer.parseInt(dayFragment.getName()) - 1;
		dayFragment.getScrollDays().setCurrentItem(currentDay);
		
		ActivityWorkoutSelect activityWorkoutSelect = dayFragment.getActivityWorkoutSelect();
		
		activityWorkoutSelect.getWorkoutTitle().setText(activityWorkoutSelect.getWorkoutDays().get(currentDay).getTitle());
		activityWorkoutSelect.getWorkoutContents().setText(activityWorkoutSelect.getWorkoutDays().get(currentDay).getDescription());
		activityWorkoutSelect.getProgress().setProgress(currentDay);
		FxpApp.currentDays.put(FxpApp.currentWorkoutName, currentDay);
	}

}
