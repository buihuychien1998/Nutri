package com.scopic.fxp.ui.event;

import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.ResetRestoreRow;
import com.scopic.fxp.bean.WorkoutData;
import com.scopic.fxp.bean.WorkoutRow;
import com.scopic.fxp.ui.adapter.RestoreListAdapter;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.ActivityResetRestoreList;
import com.scopic.fxp.ui.screen.ActivitySettings;

public class ResetRestoreListScreenEventHandler implements OnClickListener, OnItemClickListener {

	private List<ResetRestoreRow> resetRestoreRows;

	private ActivityResetRestoreList activityResetRestoreList;

	private FragmentManager fragmentManager;

	public ResetRestoreListScreenEventHandler(ActivityResetRestoreList activityResetRestoreList) {
		super();

		this.activityResetRestoreList = activityResetRestoreList;

		fragmentManager = activityResetRestoreList.getActivity().getSupportFragmentManager();
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.imgBack:
			back(transaction);
			break;
			
		case R.id.btnReset:
			switchToReset();
			break;
			
		case R.id.btnRestore:
			switchToRestore();
			break;
		}

		// Commit the transaction
		transaction.commit();
	}

	private void back(FragmentTransaction transaction) {
		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getSettings());
		transaction.replace(R.id.fragment, new ActivitySettings(), Params.SETTINGS_SCREEN);
	}
	
	private void switchToReset() {


		
		List<WorkoutRow> workoutRows = new ArrayList<WorkoutRow>();

		
		List<ResetRestoreRow> resetRestoreRows = new ArrayList<ResetRestoreRow>();
		for (int i = 0; i < workoutRows.size(); i++) {
			ResetRestoreRow resetRestoreRow = new ResetRestoreRow(workoutRows.get(i).getName(), activityResetRestoreList.getString(R.string.reset));
			resetRestoreRows.add(resetRestoreRow);
		}
		
		//ResetListAdapter resetListAdapter = new ResetListAdapter(activityResetRestoreList.getActivity(), resetRestoreRows);
		//activityResetRestoreList.getExercisesList().setAdapter(resetListAdapter);
		
		activityResetRestoreList.getReset().setSelected(true);
		activityResetRestoreList.getRestore().setSelected(false);
	}
	
	private void switchToRestore() {
		List<ResetRestoreRow> resetRestoreRows = new ArrayList<ResetRestoreRow>();
		for (int i = 0; i < 1; i++) {
			ResetRestoreRow resetRestoreRow = new ResetRestoreRow("Premium Workouts", activityResetRestoreList.getString(R.string.restore));
			resetRestoreRows.add(resetRestoreRow);
		}
		
		RestoreListAdapter restoreListAdapter = new RestoreListAdapter(activityResetRestoreList.getActivity(), resetRestoreRows);
		activityResetRestoreList.getExercisesList().setAdapter(restoreListAdapter);
		
		activityResetRestoreList.getReset().setSelected(false);
		activityResetRestoreList.getRestore().setSelected(true);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		ResetRestoreRow resetRestoreRow = (ResetRestoreRow) resetRestoreRows.get(position);
	}

	public List<ResetRestoreRow> getResetRestoreRows() {
		return resetRestoreRows;
	}

	public void setResetRestoreRows(List<ResetRestoreRow> resetRestoreRows) {
		this.resetRestoreRows = resetRestoreRows;
	}
}
