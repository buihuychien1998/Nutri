package com.scopic.fxp.ui.event;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.WorkoutRow;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.ActivityMenu;
import com.scopic.fxp.ui.screen.ActivityMainWorkoutList;
import com.scopic.fxp.ui.screen.ActivityWorkoutSelect;
import com.scopic.fxp.ui.screen.Item;
import com.scopic.fxp.ui.screen.PatientPhotoDetailFragment;

public class MainWorkoutListScreenEventHandler implements OnClickListener, OnItemClickListener {

	private List<Item> workoutRows;

	private FragmentManager fragmentManager;

	private  Context context;

	public MainWorkoutListScreenEventHandler(ActivityMainWorkoutList activityWorkoutList, Context context) {
		super();

		fragmentManager = activityWorkoutList.getActivity().getSupportFragmentManager();
		this.context = context;
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
		
//		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
//		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());
//
//		FragmentTransaction transaction = fragmentManager.beginTransaction();
//		ActivityWorkoutSelect activityWorkoutSelect = new ActivityWorkoutSelect();
//		FxpApp.currentWorkoutName = workoutRow.getTitle();
//
//		transaction.replace(R.id.fragment, activityWorkoutSelect, Params.WORKOUT_SELECT_SCREEN);
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
