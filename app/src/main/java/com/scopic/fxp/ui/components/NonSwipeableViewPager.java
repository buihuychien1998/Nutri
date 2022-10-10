package com.scopic.fxp.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

public class NonSwipeableViewPager extends ViewPager {
	
	private RelativeLayout swipeLayout;

	public NonSwipeableViewPager(Context context) {
		super(context);
	}

	public NonSwipeableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		// Never allow swiping to switch between pages
		swipeLayout.dispatchTouchEvent(event);
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Never allow swiping to switch between pages
		return false;
	}

	public void setSwipeLayout(RelativeLayout swipeLayout) {
		this.swipeLayout = swipeLayout;
	}
	
}