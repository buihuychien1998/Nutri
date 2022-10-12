package com.mteam.nutri.ui.screen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.bean.ExerciseBean;
import com.mteam.nutri.ui.utils.CommonUtils;
import com.mteam.nutri.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@SuppressLint("NewApi")
public class ActivitySplash extends AbstractActivity {

	private TextView mLoading;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);

		mLoading = (TextView) findViewById(R.id.txtLoading);
		Intent intent = new Intent(ActivitySplash.this, ActivityMain.class);

		ActivitySplash.this.startActivity(intent);

		//new DataLoadingTask().execute();
	}

	@Override
	public void onResume() {
		super.onResume();

		mLoading.setTypeface(FxpApp.helveticaNeue);

		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			View decorView = getWindow().getDecorView();
			// Hide both the navigation bar and the status bar.
			// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and
			// higher, but as
			// a general rule, you should design your app to hide the status bar
			// whenever you
			// hide the navigation bar.
			int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
		            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
		            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
		            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
		            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
		            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

			decorView.setSystemUiVisibility(uiOptions);
		}
	}

	class DataLoadingTask extends AsyncTask<Void, Long, Boolean> {

		@Override
		protected Boolean doInBackground(Void... params) {
			// Load mainWorkoutsMap
			FxpApp.mainWorkoutsMap = CommonUtils.loadMainWorkoutsMap(getApplicationContext());

			// Load premiumWorkoutsMap
			FxpApp.premiumWorkoutsMap = CommonUtils.loadPremiumWorkoutsMap(getApplicationContext());

			// Load exercisesMap
			FxpApp.exercisesMap = CommonUtils.loadAvaiableExerices(getApplicationContext());

			// Load nutritionsMap
			FxpApp.nutritionsMap = CommonUtils.loadNutritionGuide(getApplicationContext());

			FxpApp.exercisesList = new ArrayList<ExerciseBean>(FxpApp.exercisesMap.values());
			if (FxpApp.exercisesList.size() > 0) {
				Collections.sort(FxpApp.exercisesList, new Comparator<ExerciseBean>() {
					@Override
					public int compare(final ExerciseBean object1, final ExerciseBean object2) {
						return object1.getName().compareTo(object2.getName());
					}
				});
			}

			// load font
			FxpApp.helveticaNeue = Typeface.createFromAsset(getAssets(), "fonts/HelveticaLight.otf");
			// load font
			FxpApp.helveticaNeueThin = Typeface.createFromAsset(getAssets(), "fonts/HelveticaLight.otf");
			// load font
			FxpApp.helveticaNeueBold = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue.ttf");

			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// make sure we close the splash screen so the user won't come
			// back when it presses back key
			finish();

			// start the home screen
			Intent intent = new Intent(ActivitySplash.this, ActivityMain.class);

			ActivitySplash.this.startActivity(intent);
		}

	}
}
