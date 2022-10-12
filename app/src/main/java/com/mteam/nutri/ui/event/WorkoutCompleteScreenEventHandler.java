package com.mteam.nutri.ui.event;

import android.view.View;
import android.view.View.OnClickListener;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.bean.UserData;
import com.mteam.nutri.ui.commons.Params;
import com.mteam.nutri.ui.screen.ActivityMenu;
import com.mteam.nutri.ui.screen.ActivityWorkoutComplete;
import com.mteam.nutri.ui.screen.ActivityWorkoutSelect;
import com.mteam.nutri.ui.screen.ShareDialog;
import com.mteam.nutri.ui.utils.SocialUtils;
import com.mteam.nutri.R;

public class WorkoutCompleteScreenEventHandler implements OnClickListener {

	private FragmentManager fragmentManager;
	
	private ActivityWorkoutComplete activityWorkoutComplete;
	
	private String postMessage;

	public WorkoutCompleteScreenEventHandler(ActivityWorkoutComplete activityWorkoutComplete) {
		super();
		
		this.activityWorkoutComplete = activityWorkoutComplete;

		fragmentManager = activityWorkoutComplete.getActivity().getSupportFragmentManager();
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.imgBack:
			back(transaction);
			break;
			
		case R.id.btnDone:
			done(transaction);
			break;
			
		case R.id.btnShare:
			openSharePopup(transaction);
			break;
			
//		case R.id.btnFacebook:
//			shareFacebook();
//			break;
//
//		case R.id.btnTwitter:
//			shareTwitter();
//			break;
		}

		// Commit the transaction
		transaction.commit();
	}

	private void back(FragmentTransaction transaction) {
		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());
		
		ActivityWorkoutSelect activityWorkoutSelect = new ActivityWorkoutSelect();
		transaction.replace(R.id.fragment, activityWorkoutSelect, Params.WORKOUT_SELECT_SCREEN);
	}

	private void done(FragmentTransaction transaction) {

		
		UserData userData = null;

	    
	    if(userData == null) {
			userData = new UserData();
		}
	    
	    userData.setCurrentWeight(Integer.parseInt(activityWorkoutComplete.getWeight().getText().toString()));
		

//		ActivityMainWorkoutList activityMainWorkoutList = new ActivityMainWorkoutList();
//		ActivityPremiumWorkoutList activityPremiumWorkoutList = new ActivityPremiumWorkoutList();
//		
//		if(FxpApp.isMainWorkout) {
//			transaction.replace(R.id.fragment, activityMainWorkoutList, Params.MAIN_WORKOUT_LIST_SCREEN);
//		} else {
//			transaction.replace(R.id.fragment, activityPremiumWorkoutList, Params.PREMIUM_WORKOUT_LIST_SCREEN);
//		}
		transaction.replace(R.id.fragment, new ActivityMenu(), Params.HOME_SCREEN);
	}
	
	private void openSharePopup(FragmentTransaction transaction) {
		FragmentTransaction ft = fragmentManager.beginTransaction();
	    Fragment prev = fragmentManager.findFragmentByTag(Params.SHARE_DIALOG);
	    if (prev != null) {
	        ft.remove(prev);
	    }
	    ft.addToBackStack(null);

	    // Create and show the dialog.
	    ShareDialog shareDialog = ShareDialog.newInstance();
	    shareDialog.setPostMessage(postMessage);
	    shareDialog.show(ft, Params.SHARE_DIALOG);
	}
	
	private void shareFacebook() {
		SocialUtils.postMessage = postMessage;
		
		SocialUtils.shareOnFacebook();
	}
	
	private void shareTwitter() {
		SocialUtils.postMessage = postMessage;
		
		SocialUtils.shareOnTwitter();
	}

	public String getPostMessage() {
		return postMessage;
	}

	public void setPostMessage(String postMessage) {
		this.postMessage = postMessage;
	}
}
