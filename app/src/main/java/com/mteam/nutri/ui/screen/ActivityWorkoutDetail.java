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
import com.mteam.nutri.bean.ExerciseBean;
import com.mteam.nutri.bean.ExerciseRow;
import com.mteam.nutri.bean.WorkoutBean;
import com.mteam.nutri.bean.WorkoutDay;
import com.mteam.nutri.ui.adapter.WorkoutDetailListAdapter;
import com.mteam.nutri.ui.event.WorkoutDetailScreenEventHandler;
import com.mteam.nutri.ui.utils.CommonUtils;
import com.mteam.nutri.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityWorkoutDetail extends AbstractFragment {

	private TextView mTitle;
	
	private View mBack;

	private ListView mExercisesList;
	
	private View mComplete;
	
	public static final String ICON_PREFIX = "icon";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_workout_detail,
		        container, false);
		
		mTitle = (TextView) view.findViewById(R.id.title);
		mExercisesList = (ListView) view.findViewById(R.id.exercises);
		mBack = view.findViewById(R.id.imgBack);
		mComplete = view.findViewById(R.id.btnComplete);
		
		WorkoutDetailScreenEventHandler eventHandler = new WorkoutDetailScreenEventHandler(this);
		mBack.setOnClickListener(eventHandler);
		mComplete.setOnClickListener(eventHandler);
		
		List<ExerciseRow> workoutDetailRows = prepareData();
		
		WorkoutDetailListAdapter workoutListAdapter = new WorkoutDetailListAdapter(this.getActivity(), workoutDetailRows);
		mExercisesList.setAdapter(workoutListAdapter);
		mExercisesList.setOnItemClickListener(eventHandler);
		eventHandler.setExerciseRows(workoutDetailRows);
		
		RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.VISIBLE);
		
		FxpApp.isLastDay = false;
		
		return view;
	}
	
	private List<ExerciseRow> prepareData() {
		List<WorkoutBean> workoutBeans = FxpApp.isMainWorkout ? FxpApp.mainWorkoutsMap.get(FxpApp.currentWorkoutName) : FxpApp.premiumWorkoutsMap.get(FxpApp.currentWorkoutName);
		List<WorkoutDay> workoutDays = workoutBeans.get(0).getDayList();
		Map<String, ExerciseBean> exercisesMap = FxpApp.exercisesMap;
		
		int currentDay = FxpApp.currentDays.get(FxpApp.currentWorkoutName);
		WorkoutDay currentWorkoutDay = workoutDays.get(currentDay);
		
		List<ExerciseRow> exerciseRows = new ArrayList<ExerciseRow>();
		List<ExerciseBean> exerciseBeans = currentWorkoutDay.getExerciseList();
		for (int i = 0; i < exerciseBeans.size(); i++) {
			ExerciseBean exerciseBean = exerciseBeans.get(i);
			ExerciseBean exerciseDetailBean = exercisesMap.get(exerciseBean.getName());
			
//			ExerciseBean exerciseBeanDetail = FxpApp.exercisesMap.get(exerciseBean.getName());
//			String exerciseDes = "";
//			if(exerciseBeanDetail != null) {
//				exerciseDes = FxpApp.exercisesMap.get(exerciseBean.getName()).getDescription();
//			}
			
			int imageId = R.drawable.icon1;
			try {
				imageId = CommonUtils.getId(ICON_PREFIX + exerciseDetailBean.getId(), R.drawable.class);
			} catch (SecurityException e) {
			} catch (IllegalArgumentException e) {
			} catch (NoSuchFieldException e) {
			} catch (IllegalAccessException e) {
			}
			
			ExerciseRow exerciseRow = new ExerciseRow(exerciseBean.getName(), imageId, exerciseBean.getNote() + "\n\n" + exerciseBean.getSetOrRep() + "\n");
			exerciseRows.add(exerciseRow);
		}
		
		mTitle.setText(currentWorkoutDay.getTitle());
		
		return exerciseRows;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mTitle.setTypeface(FxpApp.helveticaNeue);
		((Button)mComplete).setTypeface(FxpApp.helveticaNeue);
	}
}
