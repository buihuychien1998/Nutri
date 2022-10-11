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

	final List<Item> items = new ArrayList<>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_premium_workout_list,
		        container, false);
		
		mTitle = (TextView) view.findViewById(R.id.title);
		mWorkoutList = (ListView) view.findViewById(R.id.workouts);
		mBack = view.findViewById(R.id.imgBack);
		initData();
		PremiumWorkoutListScreenEventHandler eventHandler = new PremiumWorkoutListScreenEventHandler(this,getContext());
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
		items.add(new Item(getString(R.string.oange),
				"https://i0.wp.com/images-prod.healthline.com/hlcmsresource/images/AN_images/orange-juice-1296x728-feature.jpg?w=1155&h=1528",
				"A drink made from or flavored with oranges."));

		items.add(new Item(getString(R.string.milk),
				"https://upload.wikimedia.org/wikipedia/commons/c/c8/Oat_milk_glass_and_bottles.jpg",
				"Milk is a white liquid food produced by the mammary glands of mammals"));

		items.add(new Item(getString(R.string.smoothie),
				"https://www.sunglowkitchen.com/wp-content/uploads/2021/07/smoothie-recipe-with-avocado-15-1.jpg",
				"This avocado smoothie is rich and creamy thanks to a combination of vanilla yogurt, avocado, and honey"));

		items.add(new Item(getString(R.string.energy),
				"https://cdn-amz.woka.io/images/I/812EcnH+txL.jpg",
				"An energy drink is a type of drink containing stimulant"));

		items.add(new Item(getString(R.string.juice),
				"https://www.goodnature.com/wp-content/uploads/2021/07/apple-juice-hero-500x500.jpg",
				"Apple juice is a fruit juice made by the maceration and pressing of an apple"));

		items.add(new Item(getString(R.string.Beer),
				"https://www.eatthis.com/wp-content/uploads/sites/4/2022/01/beer-glasses.jpg?quality=82&strip=1",
				"An alcoholic drink made from yeast-fermented malt flavored with hops"));

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
