package com.scopic.fxp.ui.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.ui.event.MenuScreenEventHandler;

public class ActivityMenu extends Fragment {
	
	private ImageView mImgMain;
	
	private ImageView mImgPremium;

	private RelativeLayout mLayoutMain;
	
	private RelativeLayout mLayoutPremium;

	private RelativeLayout mLayoutSport;

	private TextView mTxtNameMain;
	
	//private TextView mNameMainSub;
	
	private TextView mNamePremium;
	
	private TextView mNamePremiumSub;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_menu,
		        container, false);
		
		mImgMain = (ImageView) view.findViewById(R.id.imgMain);
		mImgPremium = (ImageView) view.findViewById(R.id.imgPremium);
		mLayoutMain = (RelativeLayout) view.findViewById(R.id.layoutMain);
		mLayoutPremium = (RelativeLayout) view.findViewById(R.id.layoutPremium);
		mLayoutSport = (RelativeLayout) view.findViewById(R.id.layoutSport);
		mTxtNameMain = (TextView) view.findViewById(R.id.txtNameMain);
		//mNameMainSub = (TextView) view.findViewById(R.id.txtNameMainSub);
		mNamePremium = (TextView) view.findViewById(R.id.txtNamePremium);
		//mNamePremiumSub = (TextView) view.findViewById(R.id.txtNamePremiumSub);
		
	//	mImgMain.setImageResource(R.drawable.main_workout_default_icon);
	//	mImgPremium.setImageResource(R.drawable.premium_workout_default_icon);
		
		MenuScreenEventHandler eventHandler = new MenuScreenEventHandler(this);
		mLayoutMain.setOnClickListener(eventHandler);
		mLayoutPremium.setOnClickListener(eventHandler);
		mLayoutSport.setOnClickListener(eventHandler);

		RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.VISIBLE);
		
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		
		if(FxpApp.menuBar != null) {
        	FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getHome());
        	FxpApp.menuBar.getHome().setSelected(true);
        }
		
		mTxtNameMain.setTypeface(FxpApp.helveticaNeue);
		//mNameMainSub.setTypeface(FxpApp.helveticaNeue);
		mNamePremium.setTypeface(FxpApp.helveticaNeue);
	//	mNamePremiumSub.setTypeface(FxpApp.helveticaNeue);
	}
}
