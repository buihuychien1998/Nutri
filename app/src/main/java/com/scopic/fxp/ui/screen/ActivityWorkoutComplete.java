package com.scopic.fxp.ui.screen;

import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.UserData;
import com.scopic.fxp.bean.WorkoutBean;
import com.scopic.fxp.bean.WorkoutDay;
import com.scopic.fxp.facebook.Constants;
import com.scopic.fxp.facebook.FacebookConnector;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.event.WorkoutCompleteScreenEventHandler;

public class ActivityWorkoutComplete extends AbstractFragment {

	private TextView mTitle;
	
	private TextView mCongrats;

	private TextView mDay;
	
	private TextView mComplete;
	
	private TextView mCompleteNote;
	
	private TextView mFuel;
	
	private TextView mPoint;
	
	private TextView mUpdate;
	
	private TextView mLbs;
	
	private TextView mTxtShare;
	
	private EditText mWeight;
	
	private View mBack;

	private View mBtnDone;
	
	private View mShare;
	
	private View mFacebook;
	
	private View mTwitter;
	
	private List<WorkoutDay> workoutDays;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_workout_complete,
		        container, false);

		mTitle = (TextView) view.findViewById(R.id.title);
		mCongrats = (TextView) view.findViewById(R.id.txtCongratulation);
		mDay = (TextView) view.findViewById(R.id.txtDay);
		mComplete = (TextView) view.findViewById(R.id.txtComplete);
		mCompleteNote = (TextView) view.findViewById(R.id.txtCompleteNote);
		mFuel = (TextView) view.findViewById(R.id.txtFuel);
		mPoint = (TextView) view.findViewById(R.id.txtPoint);
		mUpdate = (TextView) view.findViewById(R.id.txtUpdate);
		mLbs = (TextView) view.findViewById(R.id.txtLbs);
		mWeight = (EditText) view.findViewById(R.id.edtWeight);
		mBack = view.findViewById(R.id.imgBack);
		mBtnDone = view.findViewById(R.id.btnDone);
		mShare = view.findViewById(R.id.btnShare);
		
		mTxtShare = (TextView) view.findViewById(R.id.txtShare);
		mFacebook = view.findViewById(R.id.btnFacebook);
		mTwitter = view.findViewById(R.id.btnTwitter);
		
		WorkoutCompleteScreenEventHandler eventHandler = new WorkoutCompleteScreenEventHandler(this);
		mBack.setOnClickListener(eventHandler);
		mBtnDone.setOnClickListener(eventHandler);
		if(mShare != null) {
			mShare.setOnClickListener(eventHandler);
		}
		if(mFacebook != null) {
			mFacebook.setOnClickListener(eventHandler);
		}
		if(mTwitter != null) {
			mTwitter.setOnClickListener(eventHandler);
		}
		
		RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.VISIBLE);
		
		prepareData(eventHandler);
		
		return view;
	}
	
	private void prepareData(WorkoutCompleteScreenEventHandler eventHandler) {
		String currentWorkoutName = FxpApp.currentWorkoutName;
		int currentDay = FxpApp.currentDays.get(currentWorkoutName);
		long fuelPoint = FxpApp.currentFuelPoints;
		

		UserData userData = null;

		
		if(userData == null) {
			mWeight.setText(getString(R.string.default_weight));
		} else {
			mWeight.setText(String.valueOf(userData.getCurrentWeight()));
		}
		
		List<WorkoutBean> workoutBeans = FxpApp.isMainWorkout ? FxpApp.mainWorkoutsMap.get(currentWorkoutName) : FxpApp.premiumWorkoutsMap.get(currentWorkoutName);
		workoutDays = workoutBeans.get(0).getDayList();
		
		if(FxpApp.isLastDay) {
			currentDay = workoutDays.size();
		}
		
		mCompleteNote.setText(workoutDays.get(currentDay - 1).getCompleteDescription());
		
		String day = getString(R.string.day_complete);
		
		String result = String.format(day, currentDay + "");
		mDay.setText(result);
		
		String fuelPointStr = fuelPoint + "";
		fuelPointStr = fuelPointStr.equals("0.0") ? "0" : fuelPointStr;
		mPoint.setText(fuelPointStr);
		
		eventHandler.setPostMessage("Yes! I completed my FXP " + currentWorkoutName 
				+ " workout and I burned " + fuelPointStr + " fuel points."
				+ " Download this awesome FXP Fitness App!");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mTitle.setTypeface(FxpApp.helveticaNeue);
		mCongrats.setTypeface(FxpApp.helveticaNeue);
		mDay.setTypeface(FxpApp.helveticaNeue);
		mComplete.setTypeface(FxpApp.helveticaNeue);
		mCompleteNote.setTypeface(FxpApp.helveticaNeue);
		mFuel.setTypeface(FxpApp.helveticaNeue);
		mPoint.setTypeface(FxpApp.helveticaNeue);
		mUpdate.setTypeface(FxpApp.helveticaNeue);
		mLbs.setTypeface(FxpApp.helveticaNeue);
		if(mTxtShare != null) {
			mTxtShare.setTypeface(FxpApp.helveticaNeue);
		}
		if(mFacebook != null) {
			((Button)mFacebook).setTypeface(FxpApp.helveticaNeue);
		}
		if(mTwitter != null) {
			((Button)mTwitter).setTypeface(FxpApp.helveticaNeue);
		}
		mWeight.setTypeface(FxpApp.helveticaNeue);
		((Button)mBtnDone).setTypeface(FxpApp.helveticaNeue);
		((Button)mBtnDone).setTypeface(FxpApp.helveticaNeue);
	}

	public EditText getWeight() {
		return mWeight;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fragment, new ActivityWorkoutComplete(), Params.WORKOUT_COMPLETE_SCREEN);

		// Commit the transaction
		transaction.commitAllowingStateLoss();
	}
}
