package com.mteam.nutri.ui.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.ui.event.DayFragmentEventHandler;
import com.mteam.nutri.R;

public class DayFragment extends PageFragment {
	
	TextView txtDay;
	
	private ViewPager mScrollDays;
	
	private ActivityWorkoutSelect activityWorkoutSelect;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.day_fragment, container, false);
		txtDay = (TextView) view.findViewById(R.id.txtDay);
		
		txtDay.setText(this.name);
		
		DayFragmentEventHandler eventHandler = new DayFragmentEventHandler(this, pageNumber);
		txtDay.setOnClickListener(eventHandler);
		
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		
		txtDay.setTypeface(FxpApp.helveticaNeue);
	}

	public ViewPager getScrollDays() {
		return mScrollDays;
	}

	public void setScrollDays(ViewPager mScrollDays) {
		this.mScrollDays = mScrollDays;
	}

	public ActivityWorkoutSelect getActivityWorkoutSelect() {
		return activityWorkoutSelect;
	}

	public void setActivityWorkoutSelect(ActivityWorkoutSelect activityWorkoutSelect) {
		this.activityWorkoutSelect = activityWorkoutSelect;
	}
}
