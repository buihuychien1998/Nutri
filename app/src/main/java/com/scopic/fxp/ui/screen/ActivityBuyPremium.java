package com.scopic.fxp.ui.screen;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.WorkoutBean;
import com.scopic.fxp.ui.billing.PurchaseManager;
import com.scopic.fxp.ui.event.BuyPremiumScreenEventHandler;

public class ActivityBuyPremium extends AbstractFragment {

	private TextView mTitle;
	
	private TextView mContents;
	
	private View mBack;
	
	private View mBuy;
	
	private String purchaseId;
	
	public static final String THUMB_PREFIX = "thumb";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_buy_premium,
		        container, false);
		
		mTitle = (TextView) view.findViewById(R.id.title);
		mBack = view.findViewById(R.id.imgBack);
		mBuy = view.findViewById(R.id.btnBuy);
		mContents = (TextView) view.findViewById(R.id.txtContents);
		
		BuyPremiumScreenEventHandler eventHandler = new BuyPremiumScreenEventHandler(this);
		mBack.setOnClickListener(eventHandler);
		mBuy.setOnClickListener(eventHandler);
		
		RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.VISIBLE);
		
		prepareData();
		
		try {
			// build connection to Google Play
			PurchaseManager.buildConnectionToGooglePlay(getActivity());
		} catch (Exception e) {
		}
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mTitle.setTypeface(FxpApp.helveticaNeue);
		mContents.setTypeface(FxpApp.helveticaNeue);
		((Button)mBuy).setTypeface(FxpApp.helveticaNeue);
	}

	public void prepareData() {
		String workoutName = FxpApp.currentWorkoutName;
		
		WorkoutBean workoutBean = FxpApp.premiumWorkoutsMap.get(workoutName).get(0);
		String contents = workoutName + "\n" 
				+ workoutBean.getPurchaseDescription();
				
		SpannableString ss1=  new SpannableString(contents);
		ss1.setSpan(new RelativeSizeSpan(1.5f), 0, workoutName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // set size
		mContents.setText(ss1);
		
		purchaseId = workoutBean.getPurchaseId();
	}

	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	public View getBuy() {
		return mBuy;
	}

	public void setBuy(View mBuy) {
		this.mBuy = mBuy;
	}
}
