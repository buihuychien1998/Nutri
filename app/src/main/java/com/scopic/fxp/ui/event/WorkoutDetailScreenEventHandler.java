package com.scopic.fxp.ui.event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.ExerciseBean;
import com.scopic.fxp.bean.ExerciseRow;
import com.scopic.fxp.bean.UserData;
import com.scopic.fxp.bean.WorkoutBean;
import com.scopic.fxp.bean.WorkoutData;
import com.scopic.fxp.bean.WorkoutDay;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.ActivityExerciseDetail;
import com.scopic.fxp.ui.screen.ActivityWorkoutComplete;
import com.scopic.fxp.ui.screen.ActivityWorkoutDetail;
import com.scopic.fxp.ui.screen.ActivityWorkoutSelect;
import com.scopic.fxp.ui.utils.CommonUtils;

public class WorkoutDetailScreenEventHandler implements OnClickListener, OnItemClickListener {

	private List<ExerciseRow> exerciseRows;

	private ActivityWorkoutDetail activityWorkoutDetail;

	private FragmentManager fragmentManager;

	public WorkoutDetailScreenEventHandler(ActivityWorkoutDetail activityWorkoutDetail) {
		super();

		this.activityWorkoutDetail = activityWorkoutDetail;

		fragmentManager = activityWorkoutDetail.getActivity().getSupportFragmentManager();
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.imgBack:
			back(transaction);
			break;
			
		case R.id.btnComplete:
			complete(transaction);
			break;
		}

		// Commit the transaction
		transaction.commit();
	}

	private void back(FragmentTransaction transaction) {
		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());
		
		ActivityWorkoutSelect activityWorkoutSelect = new ActivityWorkoutSelect();
		transaction.replace(R.id.fragment, activityWorkoutSelect, Params.WORKOUT_SELECT_SCREEN);
	}
	
	private void complete(FragmentTransaction transaction) {
		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());
		

		String currentWorkoutName = FxpApp.currentWorkoutName;
		String[] selectionArgs = { currentWorkoutName };

		WorkoutData workoutData = null;

		
		if(workoutData == null) {
			workoutData = new WorkoutData();
			workoutData.setWorkoutName(currentWorkoutName);
		}
		

		
		UserData userData = null;

	    
	    if(userData == null) {
			userData = new UserData();
		}
		
		String completedDaysStr = workoutData.getCompletedDays();
		List<String> completedDays = Arrays.asList(completedDaysStr.split(";"));
		int currentDay = FxpApp.currentDays.get(currentWorkoutName);
		if(completedDaysStr.equals("")) {
			completedDaysStr = completedDaysStr + currentDay;
		} else if(!completedDays.contains(String.valueOf(currentDay))) {
			completedDaysStr = completedDaysStr + ";" + currentDay;
		}
		workoutData.setCompletedDays(completedDaysStr);
		
		List<WorkoutBean> workoutBeans = FxpApp.isMainWorkout ? FxpApp.mainWorkoutsMap.get(FxpApp.currentWorkoutName) : FxpApp.premiumWorkoutsMap.get(FxpApp.currentWorkoutName);
		List<WorkoutDay> workoutDays = workoutBeans.get(0).getDayList();
		if(FxpApp.currentDays.get(FxpApp.currentWorkoutName) == workoutDays.size() - 1) {
			FxpApp.currentDays.put(FxpApp.currentWorkoutName, 0);
			workoutData.setCurrentDay(0);
			FxpApp.isLastDay = true;
		} else {
			FxpApp.currentDays.put(FxpApp.currentWorkoutName, currentDay + 1);
			workoutData.setCurrentDay(currentDay + 1);
		}
		
		List<ExerciseBean> exerciseBeans = getData(currentWorkoutName, currentDay);
		float timeInHours = 0;
		for (ExerciseBean exerciseBean : exerciseBeans) {
			if(exerciseBean.getTime() != null) {
				try {
					timeInHours += Float.valueOf(exerciseBean.getTime()) / 60;
				} catch (NumberFormatException e) {
				}
			}
		}
		
		double weightInKg = userData.getCurrentWeight() / 2.2046;
		long fuelPoints = Math.round(weightInKg * 3.5 * timeInHours);
		
		ActivityWorkoutComplete activityWorkoutComplete = new ActivityWorkoutComplete();
		FxpApp.currentFuelPoints = fuelPoints;
		transaction.replace(R.id.fragment, activityWorkoutComplete, Params.WORKOUT_COMPLETE_SCREEN);
		
		workoutData.getFuelPoints().add(fuelPoints);

		
		String currentDate = CommonUtils.getCurrentDate();
		if(userData.getFuelPoints().get(currentDate) != null) {
			long fuelPointsInDay = userData.getFuelPoints().get(currentDate) + fuelPoints;
			userData.getFuelPoints().put(currentDate, fuelPointsInDay);
		} else {
			userData.getFuelPoints().put(currentDate, fuelPoints);
		}
		

	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ExerciseRow exerciseRow = (ExerciseRow) exerciseRows.get(position);

		if(FxpApp.exercisesMap.get(exerciseRow.getName()) != null) {
			FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
			FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getExercises());
			
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			ActivityExerciseDetail activityExerciseDetail = new ActivityExerciseDetail();
			FxpApp.currentExerciseName = exerciseRow.getName();
			FxpApp.isFromExerciseList = false;
			transaction.replace(R.id.fragment, activityExerciseDetail, Params.EXERCISES_DETAIL_SCREEN);
			// Commit the transaction
			transaction.commit();
		}
	}
	
	private List<ExerciseBean> getData(String workoutName, int currentDay) {
		List<WorkoutBean> workoutBeans = FxpApp.isMainWorkout ? FxpApp.mainWorkoutsMap.get(workoutName) : FxpApp.premiumWorkoutsMap.get(workoutName);
		List<WorkoutDay> workoutDays = workoutBeans.get(0).getDayList();
		
		WorkoutDay currentWorkoutDay = workoutDays.get(currentDay);
		
		List<ExerciseBean> exerciseBeans = currentWorkoutDay.getExerciseList();
		List<ExerciseBean> exerciseDetailBeans = new ArrayList<ExerciseBean>();
		for (int i = 0; i < exerciseBeans.size(); i++) {
			ExerciseBean exerciseBean = exerciseBeans.get(i);
			
			ExerciseBean exerciseBeanDetail = FxpApp.exercisesMap.get(exerciseBean.getName());
			if(exerciseBeanDetail != null) {
				if(exerciseBean.getTime() != null) {
					exerciseBeanDetail.setTime(exerciseBean.getTime());
				}
				exerciseDetailBeans.add(exerciseBeanDetail);
			}
		}
		
		return exerciseDetailBeans;
	}
	
	public List<ExerciseRow> getExerciseRows() {
		return exerciseRows;
	}

	public void setExerciseRows(List<ExerciseRow> exerciseRows) {
		this.exerciseRows = exerciseRows;
	}

}
