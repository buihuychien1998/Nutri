package com.scopic.fxp.ui.event;

import java.util.ArrayList;
import java.util.List;


import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.ui.billing.PurchaseManager;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.ActivityBuyPremium;
import com.scopic.fxp.ui.screen.ActivityPremiumWorkoutList;

public class BuyPremiumScreenEventHandler implements OnClickListener {

	private FragmentManager fragmentManager;

	private ActivityBuyPremium activityBuyPremium;

	public BuyPremiumScreenEventHandler(ActivityBuyPremium activityBuyPremium) {
		super();

		this.activityBuyPremium = activityBuyPremium;

		fragmentManager = activityBuyPremium.getActivity()
				.getSupportFragmentManager();
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.imgBack:
			back(transaction);
			break;

		case R.id.btnBuy:
			buyWorkout(transaction);
			break;

		}

		// Commit the transaction
		transaction.commit();
	}

	private void back(FragmentTransaction transaction) {
		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getExercises());
		transaction.replace(R.id.fragment, new ActivityPremiumWorkoutList(),
				Params.PREMIUM_WORKOUT_LIST_SCREEN);
	}

	private void buyWorkout(FragmentTransaction transaction) {
		ArrayList<String> ownedSignatureWorkout = PurchaseManager.getOwnedProductIds(activityBuyPremium.getActivity());
		
		if(ownedSignatureWorkout == null) {
			Toast.makeText(activityBuyPremium.getActivity(), "An error occurs when purchasing premium workout!", Toast.LENGTH_SHORT).show();
			return;
		}
		
		// Get productId
		String productId = activityBuyPremium.getPurchaseId();
		
		if(ownedSignatureWorkout.contains(productId)) {
			Toast.makeText(activityBuyPremium.getActivity(), "You have already purchased this signature workout and do not need to buy anymore. Please use Restore function for getting it back!", Toast.LENGTH_SHORT).show();
		} else {
			// Perform purchase
			PurchaseManager.processPurchase(productId);
		}
		
//		FragmentManager fragmentManager = ((FragmentActivity)activityBuyPremium.getActivity()).getSupportFragmentManager();
//	  	  ActivityBuyPremium activityBuyPremium = (ActivityBuyPremium) fragmentManager.findFragmentById(R.id.fragment);
//	  	  activityBuyPremium.getBuy().setVisibility(View.GONE);
//	  	  
//	  	  // Purchase successful
//	  	  DatabaseHelper databaseHelper = new DatabaseHelper(activityBuyPremium.getActivity());
//		  List<UserData> purchaseDatas = databaseHelper.select(UserData.class, null, null, null, null);
//		  
//		  UserData purchaseData = null;
//	      if(purchaseDatas.size() != 0) {
//	    	  purchaseData = purchaseDatas.get(0);	
//		  }
//			
//		  if(purchaseData == null) {
//			  purchaseData = new UserData();
//		  }
//		  
//		  purchaseData.getBoughtPremiumWorkouts().add(activityBuyPremium.getPurchaseId());
//  	  
//		  if(databaseHelper.update(purchaseData) == 0) {
//			  databaseHelper.insert(purchaseData);
//		  }
	}
}
