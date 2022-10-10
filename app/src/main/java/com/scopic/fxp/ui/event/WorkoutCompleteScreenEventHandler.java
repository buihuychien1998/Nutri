package com.scopic.fxp.ui.event;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.UserData;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.ActivityMainWorkoutList;
import com.scopic.fxp.ui.screen.ActivityMenu;
import com.scopic.fxp.ui.screen.ActivityPremiumWorkoutList;
import com.scopic.fxp.ui.screen.ActivityWorkoutComplete;
import com.scopic.fxp.ui.screen.ActivityWorkoutSelect;
import com.scopic.fxp.ui.screen.ShareDialog;
import com.scopic.fxp.ui.utils.SocialUtils;

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
			
		case R.id.btnFacebook:
			shareFacebook();
			break;
			
		case R.id.btnTwitter:
			shareTwitter();
			break;
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
