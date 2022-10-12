package com.mteam.nutri.ui.event;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.bean.ExerciseBean;
import com.mteam.nutri.bean.ExerciseRow;
import com.mteam.nutri.bean.UserData;
import com.mteam.nutri.bean.WorkoutBean;
import com.mteam.nutri.bean.WorkoutData;
import com.mteam.nutri.bean.WorkoutDay;
import com.mteam.nutri.ui.commons.Params;
import com.mteam.nutri.ui.screen.ActivityExerciseDetail;
import com.mteam.nutri.ui.screen.ActivityWorkoutComplete;
import com.mteam.nutri.ui.screen.ActivityWorkoutDetail;
import com.mteam.nutri.ui.screen.ActivityWorkoutSelect;
import com.mteam.nutri.ui.utils.CommonUtils;
import com.mteam.nutri.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
			//FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getExercises());
			
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
