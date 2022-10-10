package com.scopic.fxp.ui.billing;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.scopic.fxp.R;
import com.scopic.fxp.bean.UserData;
import com.scopic.fxp.ui.screen.ActivityBuyPremium;

public class PurchaseManager {
	
	/** the helper communicate with Google Play */
	private static IabHelper mHelper;
	
	/** the activity which is source of buy event */
	private static Activity activity;
	
	/** (arbitrary) request code for the purchase flow */
    public static final int RC_REQUEST = 10001;
    
    static final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhv0g44AlZGLOU26sD9bUQt4ZspQf3hXrv3J7kNAR1sSV4mKk4c2wMXKp6MJyudMJapHVZZOf4KVhz9lDJHKRU3+27pd/eD8MrxSx/QadbWDwErUVljCfuDlktRapCWX322yqeoVjKvaHC0F0opqgJRK6TRQ05CwU8eljp57vxUDWJ+hQ5a9tVLiw5JoO7G8bVEj2WK1jgB205Wk2+5umR/69tBBdf3k++OD8RezlVgS9dqCeBqNrc8CpzmApgPV1tmYHpe1+7Sj21Wqs+Dq3kYAa/BDLJsbQRXPc/JdUtUoqIGeRz5Fhh3Ehwj0xH9Pm65/5t0W9XEcjJL0q0sj8vwIDAQAB";
	
	public static void buildConnectionToGooglePlay(Activity activity){		
		PurchaseManager.activity = activity;
		PurchaseManager.mHelper = new IabHelper(PurchaseManager.activity, base64EncodedPublicKey);
		// create connection
		PurchaseManager.mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			   public void onIabSetupFinished(IabResult result) {
			      if (!result.isSuccess()) {
			         // there was a problem.
			    	  Log.d("PurchaseManager", "Problem setting up In-app Billing: " + result);
			    	  Toast.makeText(PurchaseManager.activity, "Cannot connect to Google Play, may be due to your internet connection. Please verify your connection is work and try again.", Toast.LENGTH_SHORT).show();
			    	  System.exit(0);
			      }
			      // IAB is fully set up!
			      Log.d("PurchaseManager", "Connect to Google Play is success");
			   }
			});
	}
	
	public static void buildConnectionToGooglePlay(final Context context) {
		PurchaseManager.mHelper = new IabHelper(context, base64EncodedPublicKey);
		// create connection
		PurchaseManager.mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			   public void onIabSetupFinished(IabResult result) {
			      if (!result.isSuccess()) {
			         // there was a problem.
			    	  Log.d("PurchaseManager", "Problem setting up In-app Billing: " + result);
			    	  Toast.makeText(context, "Cannot connect to Google Play, may be due to your internet connection. Please verify your connection is work and try again.", Toast.LENGTH_SHORT).show();
			    	  System.exit(0);
			      }
			      // IAB is fully set up!
			      Log.d("PurchaseManager", "Connect to Google Play is success");
			   }
			});
	}
	
	public static void processPurchase(String signatureWorkoutId){
		// create a listener for handling purchase events
		IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener 
		   = new IabHelper.OnIabPurchaseFinishedListener() {			
					
		   public void onIabPurchaseFinished(IabResult result, Purchase purchase) 
		   {
		      if (result.isFailure()) {
		    	  Log.d("PurchaseManager", "Error purchasing: " + result);
		    	  if(result.mResponse == IabHelper.IABHELPER_USER_CANCELLED) {
		    		  Toast.makeText(activity, activity.getResources().getString(R.string.purchase_cancelled), Toast.LENGTH_SHORT).show();
		    	  } else {
		    		  Toast.makeText(activity, activity.getResources().getString(R.string.purchase_failed), Toast.LENGTH_SHORT).show();
		    	  }
		    	  return;
		      }      
		      else if (result.isSuccess()) {
		    	  Log.d("PurchaseManager", "The purchase is successful: " + result);
		    	  
		    	  FragmentManager fragmentManager = ((FragmentActivity)activity).getSupportFragmentManager();
		    	  ActivityBuyPremium activityBuyPremium = (ActivityBuyPremium) fragmentManager.findFragmentById(R.id.fragment);
		    	  activityBuyPremium.getBuy().setVisibility(View.GONE);
		    	  
		    	  // Purchase successful
//		    	  DatabaseHelper databaseHelper = new DatabaseHelper(activity);
//		  		  List<UserData> purchaseDatas = databaseHelper.select(UserData.class, null, null, null, null);
		  		  
		  		  UserData purchaseData = null;
//			      if(purchaseDatas.size() != 0) {
//			    	  purchaseData = purchaseDatas.get(0);
//				  }
					
				  if(purchaseData == null) {
					  purchaseData = new UserData();
				  }
				  
				  purchaseData.getBoughtPremiumWorkouts().add(activityBuyPremium.getPurchaseId());
		    	  
//				  if(databaseHelper.update(purchaseData) == 0) {
//					  databaseHelper.insert(purchaseData);
//				  }
				  
		    	  return;
		      } 
		   }
		};
		
		// launch purchase
		mHelper.launchPurchaseFlow(activity, signatureWorkoutId, RC_REQUEST, mPurchaseFinishedListener);
	}
	
	public static ArrayList<String> getOwnedProductIds(final Context context) {
		ArrayList<String> ownedProductIds = new ArrayList<String>();
		try {
			Bundle ownedItems = PurchaseManager.mHelper.getService().getPurchases(3, context.getPackageName(), "inapp", null);
			 
			int response = ownedItems.getInt("RESPONSE_CODE");

			if (response == 0) {
				ownedProductIds = ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
			} else {
				Log.d("PurchaseManager", "An error occurs when get owned productIds");
				return null;
			}
		} catch (RemoteException e) {
			Log.d("PurchaseManager", "An exception occurs when get owned productIds");
			return null;
		}
		return ownedProductIds;
	}
	
	public static void dispose() {
		// release all resource of connection
		if (mHelper != null) mHelper.dispose();
		mHelper = null;
		activity = null;
	}
	
	public static IabHelper getHelper(){
		return mHelper;
	}

}
