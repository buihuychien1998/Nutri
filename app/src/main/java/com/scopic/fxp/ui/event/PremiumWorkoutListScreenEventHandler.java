package com.scopic.fxp.ui.event;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.UserData;
import com.scopic.fxp.bean.WorkoutRow;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.ActivityBuyPremium;
import com.scopic.fxp.ui.screen.ActivityMenu;
import com.scopic.fxp.ui.screen.ActivityPremiumWorkoutList;
import com.scopic.fxp.ui.screen.ActivityWorkoutSelect;

public class PremiumWorkoutListScreenEventHandler implements OnClickListener, OnItemClickListener {

	private List<WorkoutRow> workoutRows;
	
	private FragmentManager fragmentManager;
	
	private ActivityPremiumWorkoutList activityWorkoutList;

	public PremiumWorkoutListScreenEventHandler(
			ActivityPremiumWorkoutList activityWorkoutList) {
		super();
		
		this.activityWorkoutList = activityWorkoutList;
		
		fragmentManager = activityWorkoutList.getActivity().getSupportFragmentManager();
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		
		switch (v.getId()) {
		case R.id.imgBack:
			back(transaction);
			break;
		}
		
		// Commit the transaction
		transaction.commit();
	}
	
	private void back(FragmentTransaction transaction) {
		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());
		transaction.replace(R.id.fragment, new ActivityMenu(), Params.HOME_SCREEN);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		WorkoutRow workoutRow = (WorkoutRow) workoutRows.get(position);
		

		UserData userData = null;

		List<String> boughtPremiumWorkouts = new ArrayList<String>();
		if(userData != null) {
			boughtPremiumWorkouts = userData.getBoughtPremiumWorkouts();
		}
		
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());
		
		FxpApp.currentWorkoutName = workoutRow.getName();
		
		if(boughtPremiumWorkouts.contains(workoutRow.getPurchaseId())) {
			ActivityWorkoutSelect activityWorkoutSelect = new ActivityWorkoutSelect();
			transaction.replace(R.id.fragment, activityWorkoutSelect, Params.WORKOUT_SELECT_SCREEN);
		} else {
			ActivityBuyPremium activityBuyPremium = new ActivityBuyPremium();
			transaction.replace(R.id.fragment, activityBuyPremium, Params.BUY_PREMIUM_SCREEN);
		}
		
		// Commit the transaction
		transaction.commit();
	}

	public List<WorkoutRow> getWorkoutRows() {
		return workoutRows;
	}

	public void setWorkoutRows(List<WorkoutRow> workoutRows) {
		this.workoutRows = workoutRows;
	}
}
