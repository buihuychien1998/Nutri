package com.mteam.nutri.ui.event;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.ui.commons.Params;
import com.mteam.nutri.ui.screen.ActivityMenu;
import com.mteam.nutri.ui.screen.ActivityPremiumWorkoutList;
import com.mteam.nutri.ui.screen.Item;
import com.mteam.nutri.ui.screen.PatientPhotoDetailFragment;
import com.mteam.nutri.R;

import java.util.List;

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
