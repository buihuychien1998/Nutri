package com.scopic.fxp.ui.event;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
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
import com.scopic.fxp.ui.screen.Item;
import com.scopic.fxp.ui.screen.PatientPhotoDetailFragment;

public class PremiumWorkoutListScreenEventHandler implements OnClickListener, OnItemClickListener {

	private List<Item> workoutRows;
	
	private FragmentManager fragmentManager;
	private Context context;

	private ActivityPremiumWorkoutList activityWorkoutList;

	public PremiumWorkoutListScreenEventHandler(
			ActivityPremiumWorkoutList activityWorkoutList, Context context) {
		super();
		
		this.activityWorkoutList = activityWorkoutList;
		this.context = context;
		
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
		Item workoutRow = (Item) workoutRows.get(position);
		

	//		UserData userData = null;
	//
	//		List<Item> boughtPremiumWorkouts = new ArrayList<String>();
//		if(userData != null) {
//			boughtPremiumWorkouts = userData.getBoughtPremiumWorkouts();
//		}
//
//		FragmentTransaction transaction = fragmentManager.beginTransaction();
//		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
//		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());
//
//		FxpApp.currentWorkoutName = workoutRow.getName();
//
//		if(boughtPremiumWorkouts.contains(workoutRow.getPurchaseId())) {
//			ActivityWorkoutSelect activityWorkoutSelect = new ActivityWorkoutSelect();
//			transaction.replace(R.id.fragment, activityWorkoutSelect, Params.WORKOUT_SELECT_SCREEN);
//		} else {
//			ActivityBuyPremium activityBuyPremium = new ActivityBuyPremium();
//			transaction.replace(R.id.fragment, activityBuyPremium, Params.BUY_PREMIUM_SCREEN);
//		}
//
//		// Commit the transaction
//		transaction.commit();
		Intent itemDetailIntent = new Intent(context, PatientPhotoDetailFragment.class);
		itemDetailIntent.putExtra("PLACE_TITLE",
				workoutRow.getImageUrl());
		context.startActivity(itemDetailIntent);
	}

	public List<Item> getWorkoutRows() {
		return workoutRows;
	}

	public void setWorkoutRows(List<Item> workoutRows) {
		this.workoutRows = workoutRows;
	}
}
