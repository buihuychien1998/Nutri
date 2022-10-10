package com.scopic.fxp.ui.screen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.facebook.FacebookConnector;
import com.scopic.fxp.ui.billing.PurchaseManager;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.utils.CommonUtils;
import com.scopic.fxp.youtube.DeveloperKey;

@SuppressLint("NewApi")
public class ActivityMain extends AbstractActivity {

///	private FacebookConnector facebookConnector;

	private AudioManager audio;

	@SuppressLint({ "NewApi", "InlinedApi" })
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FragmentManager fm = getSupportFragmentManager();

		FragmentTransaction transaction = fm.beginTransaction();

		MenuBarFragment menuBarFragment = new MenuBarFragment();
		FxpApp.menuBar = menuBarFragment;
		transaction.replace(R.id.menubarLayout, menuBarFragment, Params.MENU_TAB);
		transaction.replace(R.id.fragment, new ActivityMenu());
		transaction.commitAllowingStateLoss();

		FxpApp.activityMain = this;

		// load share pref
		FxpApp.prefs = PreferenceManager.getDefaultSharedPreferences(FxpApp.activityMain);

		audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		String isShowUpdateWeightDialog = CommonUtils.loadData(this, Params.IS_SHOW_UPDATE_WEIGHT_DIALOG);
		if (isShowUpdateWeightDialog.equals("")) {
			showUpdateWeightDialog();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

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

		if (FxpApp.menuBar != null && FxpApp.menuBar.getCurrentViewSelected() == null) {
			FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());
			FxpApp.menuBar.getHome().setSelected(true);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PurchaseManager.RC_REQUEST) {
			// Pass on the activity result to the helper for handling
			if (!PurchaseManager.getHelper().handleActivityResult(requestCode, resultCode, data)) {
				Log.d("PurchaseManager", "onActivityResult handled by yourself.");
				super.onActivityResult(requestCode, resultCode, data);
			} else {
				Log.d("PurchaseManager", "onActivityResult handled by IABUtil.");
			}
		} else {
//			if (this.facebookConnector != null && this.facebookConnector.getFacebook() != null) {
//				this.facebookConnector.getFacebook().authorizeCallback(requestCode, resultCode, data);
//			}
			FragmentManager fragmentManager = getSupportFragmentManager();
			ActivityVideoDemo activityVideoDemo = (ActivityVideoDemo) fragmentManager.findFragmentByTag(Params.VIDEO_DEMO_SCREEN);
			if (activityVideoDemo != null) {
				YouTubePlayerSupportFragment youTubeView = (YouTubePlayerSupportFragment) fragmentManager.findFragmentById(R.id.youtube_fragment);
				if (youTubeView != null) {
					youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, activityVideoDemo);
				}
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		YouTubePlayerSupportFragment youTubeView = (YouTubePlayerSupportFragment) fragmentManager.findFragmentById(R.id.youtube_fragment);
		if (youTubeView != null) {
			transaction.remove(youTubeView);
		}

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ActivityVideoDemo activityVideoDemo = (ActivityVideoDemo) fragmentManager.findFragmentByTag(Params.VIDEO_DEMO_SCREEN);
			ActivityMenu activityMenu = (ActivityMenu) fragmentManager.findFragmentByTag(Params.HOME_SCREEN);

			if (activityVideoDemo != null) {
				if (activityVideoDemo.isFromWorkoutDetail()) {
					transaction.replace(R.id.fragment, new ActivityWorkoutDetail(), Params.WORKOUT_DETAIL_SCREEN);
				} else {
					transaction.replace(R.id.fragment, new ActivityExerciseDetail(), Params.EXERCISES_DETAIL_SCREEN);
				}

				if (getString(R.string.screen_size).equals("7")) {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				} else {
					activityVideoDemo.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
				}

				// Commit the transaction
				transaction.commit();
				return true;
			} else if (activityMenu != null) {
				finish();
				return true;
			} else {
				transaction.replace(R.id.fragment, new ActivityMenu(), Params.HOME_SCREEN);

				FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
				FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());

				// Commit the transaction
				transaction.commit();
				return true;
			}
		}

		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			return true;
		default:
			return false;
		}
	}

//	public FacebookConnector getFacebookConnector() {
//		return facebookConnector;
//	}

//	public void setFacebookConnector(FacebookConnector facebookConnector) {
//		this.facebookConnector = facebookConnector;
//	}

	@Override
	public void onBackPressed() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		ActivityVideoDemo activityVideoDemo = (ActivityVideoDemo) fragmentManager.findFragmentByTag(Params.VIDEO_DEMO_SCREEN);

		if (activityVideoDemo != null) {
			if (activityVideoDemo.isFullScreen()) {
				activityVideoDemo.getVideoPlayer().setFullscreen(false);
				YouTubePlayerSupportFragment youTubeView = (YouTubePlayerSupportFragment) fragmentManager.findFragmentById(R.id.youtube_fragment);
				if (youTubeView != null) {
					transaction.remove(youTubeView);
				}

				if (FxpApp.isFromExerciseList) {
					transaction.replace(R.id.fragment, new ActivityExerciseDetail(), Params.EXERCISES_DETAIL_SCREEN);
				} else {
					transaction.replace(R.id.fragment, new ActivityWorkoutDetail(), Params.WORKOUT_DETAIL_SCREEN);
				}

				if (getString(R.string.screen_size).equals("7")) {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				} else {
					activityVideoDemo.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
				}

				// Commit the transaction
				transaction.commit();
			} else {
				super.onBackPressed();
			}
		}
	}

	private void showUpdateWeightDialog() {
		FragmentManager fragmentManager = getSupportFragmentManager();

		FragmentTransaction ft = fragmentManager.beginTransaction();
		Fragment prev = fragmentManager.findFragmentByTag(Params.UPDATE_WEIGHT_DIALOG);
		if (prev != null) {
			ft.remove(prev);
		}
		ft.addToBackStack(null);

		// Create and show the dialog.
		WeightUpdateDialog newFragment = WeightUpdateDialog.newInstance();
		newFragment.setCancelable(false);
		newFragment.show(ft, Params.UPDATE_WEIGHT_DIALOG);

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

		CommonUtils.saveData(this, Params.IS_SHOW_UPDATE_WEIGHT_DIALOG, "false");
	}
}
