package com.scopic.fxp.ui.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.WorkoutBean;
import com.scopic.fxp.bean.WorkoutRow;
import com.scopic.fxp.ui.adapter.WorkoutListAdapter;
import com.scopic.fxp.ui.event.PremiumWorkoutListScreenEventHandler;

public class ActivityPremiumWorkoutList extends AbstractFragment {

	private TextView mTitle;
	
	private View mBack;

	private ListView mWorkoutList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_premium_workout_list,
		        container, false);
		
		mTitle = (TextView) view.findViewById(R.id.title);
		mWorkoutList = (ListView) view.findViewById(R.id.workouts);
		mBack = view.findViewById(R.id.imgBack);
		
		PremiumWorkoutListScreenEventHandler eventHandler = new PremiumWorkoutListScreenEventHandler(this);
		mBack.setOnClickListener(eventHandler);
		
		List<WorkoutRow> workoutRows = prepareData();
		
		WorkoutListAdapter workoutListAdapter = new WorkoutListAdapter(this.getActivity(), workoutRows);
		mWorkoutList.setAdapter(workoutListAdapter);
		mWorkoutList.setOnItemClickListener(eventHandler);
		eventHandler.setWorkoutRows(workoutRows);
		
		RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.VISIBLE);
		
		return view;
	}
	
	private List<WorkoutRow> prepareData() {
		Map<String, List<WorkoutBean>> workoutsMap = FxpApp.premiumWorkoutsMap;
		
		List<WorkoutRow> workoutRows = new ArrayList<WorkoutRow>();
		for (String workoutName : workoutsMap.keySet()) {
			WorkoutRow workoutRow = new WorkoutRow(workoutName, R.drawable.premium_workout_default_icon);
			
			String purchaseId = workoutsMap.get(workoutName).get(0).getPurchaseId();
			
			workoutRow.setPurchaseId(purchaseId);
			workoutRows.add(workoutRow);
		}
		
		return workoutRows;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mTitle.setTypeface(FxpApp.helveticaNeue);
	}
}