package com.scopic.fxp.ui.event;

import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.ExerciseRow;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.ActivityExerciseDetail;
import com.scopic.fxp.ui.screen.ActivityExerciseList;
import com.scopic.fxp.ui.screen.ActivityMenu;

public class ExerciseListScreenEventHandler implements OnClickListener, OnItemClickListener {

	private List<ExerciseRow> exerciseRows;

	private FragmentManager fragmentManager;

	public ExerciseListScreenEventHandler(ActivityExerciseList activityExercisesList) {
		super();

		fragmentManager = activityExercisesList.getActivity().getSupportFragmentManager();
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.imgBack:
			back(transaction);
			break;
		}

		// Commit the transaction
		transaction.commit();
	}

	private void back(FragmentTransaction transaction) {
		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());
		transaction.replace(R.id.fragment, new ActivityMenu(), Params.HOME_SCREEN);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ExerciseRow exerciseRow = (ExerciseRow) exerciseRows.get(position);

		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getExercises());

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		ActivityExerciseDetail activityExerciseDetail = new ActivityExerciseDetail();
		FxpApp.currentExerciseName = exerciseRow.getName();
		FxpApp.isFromExerciseList = true;
		transaction.replace(R.id.fragment, activityExerciseDetail, Params.EXERCISES_DETAIL_SCREEN);
		// Commit the transaction
		transaction.commit();
	}

	public List<ExerciseRow> getExerciseRows() {
		return exerciseRows;
	}

	public void setExerciseRows(List<ExerciseRow> exerciseRows) {
		this.exerciseRows = exerciseRows;
	}

}
