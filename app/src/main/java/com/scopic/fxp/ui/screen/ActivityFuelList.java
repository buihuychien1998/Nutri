package com.scopic.fxp.ui.screen;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.FuelRow;
import com.scopic.fxp.bean.UserData;
import com.scopic.fxp.ui.adapter.FuelListAdapter;
import com.scopic.fxp.ui.event.FuelListScreenEventHandler;
import com.scopic.fxp.ui.utils.CommonUtils;

public class ActivityFuelList extends AbstractFragment {

	private TextView mTitle;
	
	private TextView mDes;
	
	private View mBack;

	private View mFuelList;
	
	private String[] pointList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_fuel_list,
		        container, false);
		
		mTitle = (TextView) view.findViewById(R.id.title);
		mDes = (TextView) view.findViewById(R.id.txtDes);
		mBack = view.findViewById(R.id.imgBack);
		
		FuelListScreenEventHandler eventHandler = new FuelListScreenEventHandler(this);
		mBack.setOnClickListener(eventHandler);
		
		Resources res = getResources();
		String[] fuelList = res.getStringArray(R.array.fuel_list);
		
		prepareData();
		
		List<FuelRow> fuelRows = new ArrayList<FuelRow>();
		
		try {
			mFuelList = (ListView) view.findViewById(R.id.fuels);
			
			for (int i = 0; i < fuelList.length; i++) {
				FuelRow fuelRow = new FuelRow(fuelList[i] + " " + res.getString(R.string.days), pointList[i]);
				fuelRows.add(fuelRow);
			}
			
			FuelListAdapter workoutListAdapter = new FuelListAdapter(this.getActivity(), fuelRows);
			((ListView)mFuelList).setAdapter(workoutListAdapter);
			((ListView)mFuelList).setOnItemClickListener(eventHandler);
		} catch (Exception e) {
			//mFuelList = (GridView) view.findViewById(R.id.fuels);
			
			for (int i = 0; i < fuelList.length; i++) {
				FuelRow fuelRow = new FuelRow(fuelList[i], pointList[i]);
				fuelRows.add(fuelRow);
			}
			
		////	FuelGridAdapter workoutListAdapter = new FuelGridAdapter(this.getActivity(), fuelRows);
			//((GridView)mFuelList).setAdapter(workoutListAdapter);
			//((GridView)mFuelList).setOnItemClickListener(eventHandler);
		}
		
		eventHandler.setFuelRows(fuelRows);
		
		RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.VISIBLE);
		
		return view;
	}
	
	private void prepareData() {
//		DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
//		List<UserData> userDatas = databaseHelper.select(UserData.class, null, null, null, null);
		
		UserData userData = null;

	    
	    if(userData == null) {
			userData = new UserData();
		}
	    
	    try {
			Map<String, Long> totalFuelPoints = userData.getFuelPoints();
			pointList = new String[3];
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String currentDateStr = CommonUtils.getCurrentDate();
			Date currentDate = dateFormat.parse(currentDateStr);
			Long point30days = 0L, point60days = 0L, point90days = 0L;
			
			for (String date : totalFuelPoints.keySet()) {
				Date passedDate = dateFormat.parse(date);
				long diff = Math.abs(currentDate.getTime() - passedDate.getTime());
				long diffDays = diff / (24 * 60 * 60 * 1000);
				
				if(totalFuelPoints.get(date) != null) {
					if(diffDays < 31) {
						point30days += totalFuelPoints.get(date);
					}
					if(diffDays < 61) {
						point60days += totalFuelPoints.get(date);
					}
					if(diffDays < 91) {
						point90days += totalFuelPoints.get(date);
					}
					if(diffDays >= 91) {
						totalFuelPoints.remove(date);
					}
				}
			}
			
			if(point30days == 0) {
				pointList[0] = "0 " + getString(R.string.points);;
				pointList[1] = "0 " + getString(R.string.points);;
				pointList[2] = "0 " + getString(R.string.points);;
			} else {
				String point30daysDisplay = point30days + " " + getString(R.string.points);
				String point60daysDisplay = point60days + " " + getString(R.string.points);
				String point90daysDisplay = point90days + " " + getString(R.string.points);
				pointList[0] = point30daysDisplay;
				pointList[1] = point60daysDisplay;
				pointList[2] = point90daysDisplay;
			}
		} catch (ParseException e) {
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mTitle.setTypeface(FxpApp.helveticaNeue);
		mDes.setTypeface(FxpApp.helveticaNeue);
	}
}
