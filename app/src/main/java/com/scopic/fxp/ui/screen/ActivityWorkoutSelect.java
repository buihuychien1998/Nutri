package com.scopic.fxp.ui.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import android.content.res.Configuration;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.WorkoutBean;
import com.scopic.fxp.bean.WorkoutData;
import com.scopic.fxp.bean.WorkoutDay;
import com.scopic.fxp.ui.adapter.DayPagerAdapter;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.components.NonSwipeableViewPager;
import com.scopic.fxp.ui.components.OnSwipeTouchListener;
import com.scopic.fxp.ui.event.WorkoutSelectScreenEventHandler;
import com.scopic.fxp.ui.utils.CommonUtils;

public class ActivityWorkoutSelect extends AbstractFragment {

	private TextView mTitle;
	
	private TextView m130;
	
	private TextView m3160;
	
	private TextView m6190;
	
	private SeekBar mProgress;
	
	private ViewPager mScrollDays;
	
	private View mBack;
	
	private TextView mWorkoutTitle;
	
	private TextView mWorkoutContents;

	private View mStart;
	
	private List<WorkoutDay> workoutDays;
	
	private List<String> completedDays = new ArrayList<String>();
	
	private RelativeLayout swipeLayout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_workout_select,
		        container, false);
		
		mTitle = (TextView) view.findViewById(R.id.title);
		mBack = view.findViewById(R.id.imgBack);
		m130 = (TextView) view.findViewById(R.id.txt130);
		m3160 = (TextView) view.findViewById(R.id.txt3160);
		m6190 = (TextView) view.findViewById(R.id.txt6190);
		mProgress = (SeekBar) view.findViewById(R.id.progressWorkout);
		mScrollDays = (ViewPager) view.findViewById(R.id.scrDays);
		mWorkoutTitle = (TextView) view.findViewById(R.id.txtWorkoutTitle);
		mWorkoutContents = (TextView) view.findViewById(R.id.txtWorkoutDes);
		mStart = view.findViewById(R.id.btnStartWorkout);
		swipeLayout = (RelativeLayout) view.findViewById(R.id.layoutSwipe);
		
		DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        if(CommonUtils.isTablet(getActivity())) {
        	mScrollDays.setPageMargin(-width + (width / 15));
        } else {
        	mScrollDays.setPageMargin(-width + (width / 7));
        }
		
		mScrollDays.setHorizontalFadingEdgeEnabled(true);
		mScrollDays.setFadingEdgeLength(10);
		mScrollDays.setOffscreenPageLimit(90);
		
//		mScrollDays.setOnPageChangeListener(new DayChangeListener());
		mProgress.setOnSeekBarChangeListener(new ProgressChangeListener());
		
		WorkoutSelectScreenEventHandler eventHandler = new WorkoutSelectScreenEventHandler(this);
		mBack.setOnClickListener(eventHandler);
		mStart.setOnClickListener(eventHandler);
		
		RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.VISIBLE);
		
		prepareData();
		
        swipeLayout.setOnTouchListener(new OnSwipeTouchListener(this));
        ((NonSwipeableViewPager)mScrollDays).setSwipeLayout(swipeLayout);
		
		return view;
	}
	
	private void prepareData() {
		//DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
		String currentWorkoutName = FxpApp.currentWorkoutName;
		String[] selectionArgs = { currentWorkoutName };
		//List<WorkoutData> workoutDatas = databaseHelper.select(WorkoutData.class, "workoutName=?", selectionArgs, null, null);
		
		WorkoutData workoutData = null;
//		if(workoutDatas.size() != 0) {
//			workoutData = workoutDatas.get(0);
//		}
		
		List<Fragment> fragments = new Vector<Fragment>();
		
		List<WorkoutBean> workoutBeans = FxpApp.isMainWorkout ? FxpApp.mainWorkoutsMap.get(currentWorkoutName) : FxpApp.premiumWorkoutsMap.get(currentWorkoutName);
		workoutDays = workoutBeans.get(0).getDayList();
		mProgress.setMax(workoutDays.size() - 1);
		
		if(FxpApp.currentDays.get(FxpApp.currentWorkoutName) == null) {
			FxpApp.currentDays.put(FxpApp.currentWorkoutName, 0);
		}
		
		if(workoutData != null) {
			FxpApp.currentDays.put(FxpApp.currentWorkoutName, workoutData.getCurrentDay());
			String completedDaysStr = workoutData.getCompletedDays();
			completedDays = Arrays.asList(completedDaysStr.split(";"));
		}
		
		DayFragment dayFragment;
		for (int i = 0; i < workoutDays.size(); i++) {
			dayFragment = new DayFragment();
			dayFragment.setName(i + 1 + "");
			dayFragment.setScrollDays(mScrollDays);
			dayFragment.setActivityWorkoutSelect(this);
			fragments.add(dayFragment);
		}
		
		DayPagerAdapter dayPagerAdapter  = new DayPagerAdapter(getActivity().getSupportFragmentManager(), fragments, getActivity());
		mScrollDays.setAdapter(dayPagerAdapter);
		
		int day = FxpApp.currentDays.get(FxpApp.currentWorkoutName);
		if(completedDays.contains(String.valueOf(day)) && day < workoutDays.size()) {
			mProgress.setProgress(day);
			mScrollDays.setCurrentItem(day);
			mWorkoutTitle.setText(workoutDays.get(day).getTitle());
			mWorkoutContents.setText(workoutDays.get(day).getDescription());
//			day += 1;
		} else {
			mProgress.setProgress(day);
			mScrollDays.setCurrentItem(day);
			mWorkoutTitle.setText(workoutDays.get(day).getTitle());
			mWorkoutContents.setText(workoutDays.get(day).getDescription());
		}
		
//		if(completedDays.contains(String.valueOf(day))) {
//			mStart.setVisibility(View.INVISIBLE);
//		} else {
//			mStart.setVisibility(View.VISIBLE);
//		}
		
		if(day < workoutDays.size()) {
			FxpApp.currentDays.put(FxpApp.currentWorkoutName, day);
		}
		
		// Dynamic set ruler
		if(workoutDays.size() % 2 == 0) {
			m130.setText("1");
			m3160.setText("");
			m6190.setText(workoutDays.size() + "");
		} else {
			m130.setText("1");
			m3160.setText(((workoutDays.size() / 2) + 1) + "");
			m6190.setText(workoutDays.size() + "");
		}
	}
	
	class ProgressChangeListener implements OnSeekBarChangeListener {

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if(progress <= workoutDays.size()) {
				
				if(fromUser) {
					mWorkoutTitle.setText(workoutDays.get(progress).getTitle());
					mWorkoutContents.setText(workoutDays.get(progress).getDescription());
					mScrollDays.setCurrentItem(progress);
					FxpApp.currentDays.put(FxpApp.currentWorkoutName, progress);
				}
				
//				if(completedDays.contains(String.valueOf(progress + 1))) {
//					mStart.setVisibility(View.INVISIBLE);
//				} else {
//					mStart.setVisibility(View.VISIBLE);
//				}
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mTitle.setTypeface(FxpApp.helveticaNeue);
		m130.setTypeface(FxpApp.helveticaNeue);
		m3160.setTypeface(FxpApp.helveticaNeue);
		m6190.setTypeface(FxpApp.helveticaNeue);
		mWorkoutTitle.setTypeface(FxpApp.helveticaNeue);
		mWorkoutContents.setTypeface(FxpApp.helveticaNeue);
		((Button)mStart).setTypeface(FxpApp.helveticaNeue);
	}

	public TextView getTitle() {
		return mTitle;
	}

	public TextView get130() {
		return m130;
	}

	public TextView get3160() {
		return m3160;
	}

	public TextView get6190() {
		return m6190;
	}

	public ProgressBar getProgress() {
		return mProgress;
	}

	public View getBack() {
		return mBack;
	}

	public TextView getWorkoutTitle() {
		return mWorkoutTitle;
	}

	public TextView getWorkoutContents() {
		return mWorkoutContents;
	}

	public View getStart() {
		return mStart;
	}

	public ViewPager getScrollDays() {
		return mScrollDays;
	}

	public List<WorkoutDay> getWorkoutDays() {
		return workoutDays;
	}

	public RelativeLayout getSwipeLayout() {
		return swipeLayout;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		transaction.replace(R.id.fragment, new ActivityWorkoutSelect(), Params.WORKOUT_SELECT_SCREEN);

		// Commit the transaction
		transaction.commitAllowingStateLoss();
	}
}
