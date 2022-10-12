package com.mteam.nutri.ui.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.bean.ResetRestoreRow;
import com.mteam.nutri.bean.WorkoutRow;
import com.mteam.nutri.ui.billing.PurchaseManager;
import com.mteam.nutri.ui.event.ResetRestoreListScreenEventHandler;
import com.mteam.nutri.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityResetRestoreList extends AbstractFragment {

	private TextView mTitle;
	
	private View mBack;
	
	private View mReset;
	
	private View mRestore;

	private ListView mExercisesList;
	
	private ResetRestoreListScreenEventHandler eventHandler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_reset_restore,
		        container, false);
		
		mTitle = (TextView) view.findViewById(R.id.title);
		mExercisesList = (ListView) view.findViewById(R.id.resetRestores);
		mBack = view.findViewById(R.id.imgBack);
		mReset = view.findViewById(R.id.btnReset);
		mRestore = view.findViewById(R.id.btnRestore);
		
		eventHandler = new ResetRestoreListScreenEventHandler(this);
		mBack.setOnClickListener(eventHandler);
		mReset.setOnClickListener(eventHandler);
		mRestore.setOnClickListener(eventHandler);
		
		mReset.setSelected(true);
		
		List<WorkoutRow> workoutRows = prepareData();
		List<ResetRestoreRow> resetRestoreRows = new ArrayList<ResetRestoreRow>();
		for (int i = 0; i < workoutRows.size(); i++) {
			ResetRestoreRow resetRestoreRow = new ResetRestoreRow(workoutRows.get(i).getName(), "Reset");
			resetRestoreRows.add(resetRestoreRow);
		}
		
		mExercisesList.setOnItemClickListener(eventHandler);
		eventHandler.setResetRestoreRows(resetRestoreRows);
		
		RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.VISIBLE);
		
		try {
			// build connection to Google Play
			PurchaseManager.buildConnectionToGooglePlay(getActivity());
		} catch (Exception e) {
		}
		
		return view;
	}

	public ListView getExercisesList() {
		return mExercisesList;
	}
	
	private List<WorkoutRow> prepareData() {

		List<WorkoutRow> workoutRows = new ArrayList<WorkoutRow>();
//		for (WorkoutData workout : userDatas) {
//			WorkoutRow workoutRow = new WorkoutRow(workout.getWorkoutName(), R.drawable.photo2_icon_nutrition_guide_scr);
//			workoutRows.add(workoutRow);
//		}
		
		return workoutRows;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mTitle.setTypeface(FxpApp.helveticaNeue);
		((Button)mReset).setTypeface(FxpApp.helveticaNeue);
		((Button)mRestore).setTypeface(FxpApp.helveticaNeue);
	}

	public View getReset() {
		return mReset;
	}

	public View getRestore() {
		return mRestore;
	}

	public ResetRestoreListScreenEventHandler getEventHandler() {
		return eventHandler;
	}
}
