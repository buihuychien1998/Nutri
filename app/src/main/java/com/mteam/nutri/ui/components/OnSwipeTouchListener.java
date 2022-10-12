package com.mteam.nutri.ui.components;

import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.ui.screen.ActivityWorkoutSelect;

public class OnSwipeTouchListener implements OnTouchListener {

	private ActivityWorkoutSelect activityWorkoutSelect;
	
	private final GestureDetector gestureDetector;

	public OnSwipeTouchListener(ActivityWorkoutSelect activityWorkoutSelect) {
		super();
		this.activityWorkoutSelect = activityWorkoutSelect;
		gestureDetector = new GestureDetector(activityWorkoutSelect.getActivity(), new GestureListener());
	}

	public boolean onTouch(final View v, final MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	private final class GestureListener extends SimpleOnGestureListener {

		private static final int SWIPE_THRESHOLD = 50;
		private static final int SWIPE_VELOCITY_THRESHOLD = 50;

		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			onTouch(e);
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			boolean result = false;
			try {
				float diffY = e2.getY() - e1.getY();
				float diffX = e2.getX() - e1.getX();
				if (Math.abs(diffX) > Math.abs(diffY)) {
					if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
						if (diffX > 0) {
							onSwipeRight();
						} else {
							onSwipeLeft();
						}
					}
				} else {
					// onTouch(e);
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return result;
		}
	}

	public void onTouch(MotionEvent e) {
	}

	public void onSwipeRight() {
		int currentDay = FxpApp.currentDays.get(FxpApp.currentWorkoutName);
		if (currentDay == 0) {
			return;
		}
		currentDay = currentDay - 1;
		activityWorkoutSelect.getScrollDays().setCurrentItem(currentDay);
		activityWorkoutSelect.getWorkoutTitle().setText(activityWorkoutSelect.getWorkoutDays().get(currentDay).getTitle());
		activityWorkoutSelect.getWorkoutContents().setText(activityWorkoutSelect.getWorkoutDays().get(currentDay).getDescription());
		activityWorkoutSelect.getProgress().setProgress(currentDay);
		FxpApp.currentDays.put(FxpApp.currentWorkoutName, currentDay);
	}

	public void onSwipeLeft() {
		int currentDay = FxpApp.currentDays.get(FxpApp.currentWorkoutName);
		if (currentDay == activityWorkoutSelect.getWorkoutDays().size()) {
			return;
		}
		activityWorkoutSelect.getScrollDays().setCurrentItem(currentDay);
		activityWorkoutSelect.getWorkoutTitle().setText(activityWorkoutSelect.getWorkoutDays().get(currentDay).getTitle());
		activityWorkoutSelect.getWorkoutContents().setText(activityWorkoutSelect.getWorkoutDays().get(currentDay).getDescription());
		activityWorkoutSelect.getProgress().setProgress(currentDay);
		FxpApp.currentDays.put(FxpApp.currentWorkoutName, currentDay + 1);
	}

	public void onSwipeTop() {
	}

	public void onSwipeBottom() {
	}
}
