package com.scopic.fxp.ui.event;

import java.util.List;

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

public class MainWorkoutListScreenEventHandler implements OnClickListener, OnItemClickListener {

	private List<WorkoutRow> workoutRows;

	private FragmentManager fragmentManager;

	public MainWorkoutListScreenEventHandler(ActivityMainWorkoutList activityWorkoutList) {
		super();

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
		
		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		ActivityWorkoutSelect activityWorkoutSelect = new ActivityWorkoutSelect();
		FxpApp.currentWorkoutName = workoutRow.getName();
		
		transaction.replace(R.id.fragment, activityWorkoutSelect, Params.WORKOUT_SELECT_SCREEN);
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
