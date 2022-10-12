package com.mteam.nutri.ui.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.bean.WorkoutBean;
import com.mteam.nutri.bean.WorkoutRow;
import com.mteam.nutri.ui.event.MainWorkoutListScreenEventHandler;
import com.mteam.nutri.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
				"https://www.onceuponachef.com/images/2021/12/Shrimp-Cocktail-3.jpg",
				getString(R.string.edđ)));

		items.add(new Item(getString(R.string.crab),
				"https://image.cnbcfm.com/api/v1/image/101100237-113240502.jpg?v=1532564619",
				getString(R.string.sadasd)));

		items.add(new Item(getString(R.string.broccoli),
				"https://media.istockphoto.com/photos/cauliflower-and-broccoli-isolated-on-white-background-with-clipping-picture-id1139968770?k=20&m=1139968770&s=170667a&w=0&h=jPTsnqSUnHpEzCs_ywycqb3Vf5XrhJwysG6tx1-75-g=",
				getString(R.string.ádas)));

		items.add(new Item(getString(R.string.chicken),
				"https://redlandmarketvillage.com/wp-content/uploads/checken-wings.jpg",
				getString(R.string.a_hh)));

		items.add(new Item(getString(R.string.potato),
				"https://www.foodnetwork.com/content/dam/images/food/fullset/2003/9/29/0/ig1a07_roasted_potatoes.jpg",
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
