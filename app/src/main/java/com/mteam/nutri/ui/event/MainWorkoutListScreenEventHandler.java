package com.mteam.nutri.ui.event;

import static com.mteam.nutri.ui.billing.PurchaseManagerV2.productIds;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.mteam.nutri.FxpApp;
import com.mteam.nutri.ui.commons.Params;
import com.mteam.nutri.ui.screen.ActivityMainWorkoutList;
import com.mteam.nutri.ui.screen.ActivityMenu;
import com.mteam.nutri.ui.screen.Item;
import com.mteam.nutri.ui.screen.PatientPhotoDetailFragment;
import com.mteam.nutri.ui.utils.CommonUtils;
import com.mteam.nutri.R;

import java.util.ArrayList;
import java.util.List;

public class MainWorkoutListScreenEventHandler extends BaseEventHandler implements OnClickListener, OnItemClickListener {

	private List<Item> workoutRows;

	private FragmentManager fragmentManager;

	private  Context context;

	private BillingClient billingClient;

	private int selectedIndex;

	final List<SkuDetails> skuDetailsList = new ArrayList<>();

	public MainWorkoutListScreenEventHandler(ActivityMainWorkoutList activityWorkoutList, Context context) {
		super(activityWorkoutList);

		fragmentManager = activityWorkoutList.getActivity().getSupportFragmentManager();
		this.context = context;
		billingClient = BillingClient.newBuilder(context)
				.enablePendingPurchases()
				.setListener((billingResult, list) -> {
					if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
						for (Purchase purchase : list) {
							if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
								Log.d(MainWorkoutListScreenEventHandler.class.getSimpleName(), "PURCHARED");
								handlePurchase(purchase);
								String signatureWorkouts = CommonUtils.loadData(activityWorkoutList.getActivity(), Params.SIGNATURE_WORKOUT_BOUGHT);
								signatureWorkouts = signatureWorkouts.equals("") ? signatureWorkouts : signatureWorkouts + ";";
								//signatureWorkouts += GorillaApp.currentWorkout;
								CommonUtils.saveData(activityWorkoutList.getActivity(), Params.SIGNATURE_WORKOUT_BOUGHT, signatureWorkouts);
								///event change color
								Item workoutRow = (Item) workoutRows.get(selectedIndex);
								Intent itemDetailIntent = new Intent(context, PatientPhotoDetailFragment.class);
								itemDetailIntent.putExtra("PLACE_TITLE",
										workoutRow.getImageUrl());
								context.startActivity(itemDetailIntent);

							}
						}
						// Query for existing user purchases
						// Query for products for sale
						Log.d(MainWorkoutListScreenEventHandler.class.getSimpleName(), "Billing client successfully set up!");
					}
					Log.d(MainWorkoutListScreenEventHandler.class.getSimpleName(), "billingResult: " + billingResult.getResponseCode());
				})
				.build();

		//TODO: Connect ???ng d???ng c???a b???n v???i Google Billing
		billingClient.startConnection(new BillingClientStateListener() {
			@Override
			public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
				//TODO: Sau khi connect th??nh c??ng, th??? l???y th??ng tin c??c s???n ph???m
				if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
					Log.d(MainWorkoutListScreenEventHandler.class.getSimpleName(), "Billing client successfully set up!");
					// Query for existing user purchases
					// Query for products for sale
				}
				queryProducts();
			}

			@Override
			public void onBillingServiceDisconnected() {
				//TODO: Connect Google Play not success
				Log.d(MainWorkoutListScreenEventHandler.class.getSimpleName(), "Billing service disconnected");
			}
		});
	}
	private void queryProducts() {
		if (!billingClient.isReady()) {
			Log.d(MainWorkoutListScreenEventHandler.class.getSimpleName(), "queryPurchases: BillingClient is not ready");
		}
		// TODO: t???o list c??c product id (ch??nh l?? product id b???n ???? nh???p ??? b?????c tr?????c) ????? l???y th??ng tin
//        List<String> productIds = new ArrayList<>();
//        productIds.add("thanh_hoa");
//        productIds.add("nghe_an");
//        productIds.add("ha_tinh");
//        productIds.add("quang_binh");
//        productIds.add("quang_tri");
//        productIds.add("thua_thien_hue");
		List<String> skusList = new ArrayList<>(productIds.values());
		SkuDetailsParams skuDetailsParams = SkuDetailsParams.newBuilder()
				.setSkusList(skusList)
				.setType(BillingClient.SkuType.INAPP)
				//TODO: S??? d???ng INAPP v???i one-time product v?? SUBS v???i c??c g??i subscriptions.
				.build();

		// TODO: Th???c hi???n query
		// Query for existing in app products that have been purchased. This does NOT include subscriptions.

		billingClient.querySkuDetailsAsync(skuDetailsParams, (billingResult, list) -> {
			Log.d(MainWorkoutListScreenEventHandler.class.getSimpleName(), skusList.size() + " skusList");
			if (list != null && !list.isEmpty()) {
				Log.d(MainWorkoutListScreenEventHandler.class.getSimpleName(), list.size() + "");
				//???? l???y ???????c th??ng tin c??c s???n ph???m ??ang b??n theo c??c product id ??? tr??n
				skuDetailsList.clear();
				skuDetailsList.addAll(list);
//                for (SkuDetails skuDetails : list) {
//                    // matches the last text surrounded by parentheses at the end of the SKU title
//                    items.get(getItemIndexByTitle(skuDetails.getSku())).setCost(skuDetails.getPrice());
//                }
//                adapter.notifyDataSetChanged();
			} else {
				Log.d(MainWorkoutListScreenEventHandler.class.getSimpleName(), "No existing in app purchases found.");
			}
		});
	}


	void handlePurchase(Purchase purchase) {
		// Purchase retrieved from BillingClient#queryPurchasesAsync or your PurchasesUpdatedListener.
//        Purchase purchase = ...;

		// Verify the purchase.
		// Ensure entitlement was not already granted for this purchaseToken.
		// Grant entitlement to the user.

		ConsumeParams consumeParams =
				ConsumeParams.newBuilder()
						.setPurchaseToken(purchase.getPurchaseToken())
						.build();

		ConsumeResponseListener listener = (billingResult, purchaseToken) -> {
			if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
				// Handle the success of the consume operation.
			}
		};

		billingClient.consumeAsync(consumeParams, listener);
	}
	@Override
	public void onClick(View v) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		switch (v.getId()) {
		case R.id.imgBack:
			back(transaction);
			break;
		}

		// Commit the transaction
		transaction.commit();
	}

	private void back(FragmentTransaction transaction) {
		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());
		transaction.replace(R.id.fragment, new ActivityMenu(), Params.HOME_SCREEN);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Item workoutRow = (Item) workoutRows.get(position);
		
//		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
//		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());
//
//		FragmentTransaction transaction = fragmentManager.beginTransaction();
//		ActivityWorkoutSelect activityWorkoutSelect = new ActivityWorkoutSelect();
//		FxpApp.currentWorkoutName = workoutRow.getTitle();
//
//		transaction.replace(R.id.fragment, activityWorkoutSelect, Params.WORKOUT_SELECT_SCREEN);
//		// Commit the transaction
//		transaction.commit();
		selectedIndex = position;
		if (skuDetailsList.isEmpty()) {
			Toast.makeText(context, "This feature is coming soon", Toast.LENGTH_SHORT).show();
			return;
		}
		int skuIndex = -1;
		for (int index = 0; index < skuDetailsList.size(); index++) {
			if (skuDetailsList.get(index).getSku().equals(productIds.get(workoutRow.getTitle()))) {
				skuIndex = index;
				break;
			}
		}
		if (skuIndex == -1) {
			Toast.makeText(context, "Price not found for this item!", Toast.LENGTH_SHORT).show();
			return;
		}
		BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
				.setSkuDetails(skuDetailsList.get(skuIndex))
				.build();

		BillingResult result = billingClient.launchBillingFlow(activity, billingFlowParams);


	}

	public List<Item> getWorkoutRows() {
		return workoutRows;
	}

	public void setWorkoutRows(List<Item> workoutRows) {
		this.workoutRows = workoutRows;
	}
}
