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
import com.scopic.fxp.ui.event.MainWorkoutListScreenEventHandler;

public class ActivityMainWorkoutList extends AbstractFragment {

	private TextView mTitle;
	
	private View mBack;

	private ListView mWorkoutList;

	final List<Item> items = new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_main_workout_list,
		        container, false);
		
		mTitle = (TextView) view.findViewById(R.id.title);
		mWorkoutList = (ListView) view.findViewById(R.id.workouts);
		mBack = view.findViewById(R.id.imgBack);
		initData();
		MainWorkoutListScreenEventHandler eventHandler = new MainWorkoutListScreenEventHandler(this,getContext());
		mBack.setOnClickListener(eventHandler);
		
		List<WorkoutRow> workoutRows = prepareData();

		FoodAdapter workoutListAdapter = new FoodAdapter(this.requireActivity(), items);
		mWorkoutList.setAdapter(workoutListAdapter);
		mWorkoutList.setOnItemClickListener(eventHandler);
		eventHandler.setWorkoutRows(items);
		
		RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.VISIBLE);
		
		return view;
	}

	private void initData() {
		items.add(new Item(getString(R.string.beef),
				"https://upload.wikimedia.org/wikipedia/commons/a/a3/Rostas_%28ready_and_served%29.JPG",
				getString(R.string.uuuuu)));

		items.add(new Item(getString(R.string.shrimp),
				R.drawable.shrimp,
				getString(R.string.edđ)));

		items.add(new Item(getString(R.string.crab),
				R.drawable.crab,
				getString(R.string.sadasd)));

		items.add(new Item(getString(R.string.broccoli),
				R.drawable.broccori,
				getString(R.string.ádas)));

		items.add(new Item(getString(R.string.chicken),
				R.drawable.chicken,
				getString(R.string.a_hh)));

		items.add(new Item(getString(R.string.potato),
				R.drawable.potato,
				getString(R.string.vegetable)));

	}

	private List<WorkoutRow> prepareData() {
		Map<String, List<WorkoutBean>> workoutsMap = FxpApp.mainWorkoutsMap;
		
		List<WorkoutRow> workoutRows = new ArrayList<WorkoutRow>();
		for (String workoutName : workoutsMap.keySet()) {
			WorkoutRow workoutRow = new WorkoutRow(workoutName, R.drawable.main_workout_default_icon);
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
