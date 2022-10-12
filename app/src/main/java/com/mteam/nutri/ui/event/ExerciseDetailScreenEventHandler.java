package com.mteam.nutri.ui.event;

import android.view.View;
import android.view.View.OnClickListener;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.bean.ExerciseBean;
import com.mteam.nutri.ui.commons.Params;
import com.mteam.nutri.ui.screen.ActivityExerciseDetail;
import com.mteam.nutri.ui.screen.ActivityExerciseList;
import com.mteam.nutri.ui.screen.ActivityVideoDemo;
import com.mteam.nutri.ui.screen.ActivityWorkoutDetail;
import com.mteam.nutri.R;

import java.util.List;

public class ExerciseDetailScreenEventHandler implements OnClickListener {

	private FragmentManager fragmentManager;
	
	private ActivityExerciseDetail activityExerciseDetail;

	public ExerciseDetailScreenEventHandler(
			ActivityExerciseDetail activityExerciseDetail) {
		super();
		
		this.activityExerciseDetail = activityExerciseDetail;

		fragmentManager = activityExerciseDetail.getActivity()
				.getSupportFragmentManager();
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.imgBack:
			back(transaction);
			break;

		case R.id.btnVideo:
			openVideo(transaction);
			break;
			
		case R.id.imgPrev:
			prevExercises();
			break;
			
		case R.id.imgNext:
			nextExercises();
			break;
		}

		// Commit the transaction
		transaction.commit();
	}

	private void back(FragmentTransaction transaction) {
		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		
		if(FxpApp.isFromExerciseList) {
			//FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getExercises());
			transaction.replace(R.id.fragment, new ActivityExerciseList(),
					Params.EXERCISES_SCREEN);
		} else {
			FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());
			transaction.replace(R.id.fragment, new ActivityWorkoutDetail(),
					Params.WORKOUT_DETAIL_SCREEN);
		}
	}

	private void openVideo(FragmentTransaction transaction) {
		ActivityVideoDemo activityVideoDemo = new ActivityVideoDemo();
		activityVideoDemo.setVideoUrl(FxpApp.exercisesMap.get(FxpApp.currentExerciseName).getYoutubeURL());
		transaction.replace(R.id.fragment, activityVideoDemo, Params.VIDEO_DEMO_SCREEN);
	}
	
	private void prevExercises() {
		List<ExerciseBean> exercisesList = FxpApp.exercisesList;
		int prevIndex = 0;
		for (int i = 0; i < exercisesList.size(); i++) {
			if(exercisesList.get(i).getName().equals(FxpApp.currentExerciseName)) {
				prevIndex = i - 1;
				break;
			}
		}
		
		ExerciseBean prevExerciseBean = exercisesList.get(prevIndex);
		FxpApp.currentExerciseName = prevExerciseBean.getName();
		activityExerciseDetail.prepareData();
		
		if(prevIndex == 0) {
			activityExerciseDetail.getPrev().setVisibility(View.INVISIBLE);
		}
		
		activityExerciseDetail.getNext().setVisibility(View.VISIBLE);
	}
	
	private void nextExercises() {
		List<ExerciseBean> exercisesList = FxpApp.exercisesList;
		int nextIndex = 0;
		for (int i = 0; i < exercisesList.size(); i++) {
			if(exercisesList.get(i).getName().equals(FxpApp.currentExerciseName)) {
				nextIndex = i + 1;
				break;
			}
		}
		
		ExerciseBean nextExerciseBean = exercisesList.get(nextIndex);
		FxpApp.currentExerciseName = nextExerciseBean.getName();
		activityExerciseDetail.prepareData();
		
		if(nextIndex == exercisesList.size() - 1) {
			activityExerciseDetail.getPrev().setVisibility(View.INVISIBLE);
		}
		
		activityExerciseDetail.getPrev().setVisibility(View.VISIBLE);
	}
}
