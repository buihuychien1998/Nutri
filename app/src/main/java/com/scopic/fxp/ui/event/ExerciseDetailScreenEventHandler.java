package com.scopic.fxp.ui.event;

import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.ExerciseBean;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.ActivityExerciseDetail;
import com.scopic.fxp.ui.screen.ActivityExerciseList;
import com.scopic.fxp.ui.screen.ActivityVideoDemo;
import com.scopic.fxp.ui.screen.ActivityWorkoutDetail;

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
			FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getExercises());
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
