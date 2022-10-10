package com.scopic.fxp.ui.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.NutritionCategoryBean;
import com.scopic.fxp.bean.NutritionRow;
import com.scopic.fxp.ui.adapter.NutritionListAdapter;
import com.scopic.fxp.ui.event.NutritionListScreenEventHandler;
import com.scopic.fxp.ui.utils.CommonUtils;

public class ActivityNutritionList extends AbstractFragment {

	private TextView mTitle;
	
	private View mBack;

	private ListView mNutritionList;
	
	public static final String ICON_PREFIX = "icon";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_nutritions_list,
		        container, false);
		
		mTitle = (TextView) view.findViewById(R.id.title);
		mNutritionList = (ListView) view.findViewById(R.id.nutritions);
		mBack = view.findViewById(R.id.imgBack);
		
		NutritionListScreenEventHandler eventHandler = new NutritionListScreenEventHandler(this);
		mBack.setOnClickListener(eventHandler);
		
		List<NutritionRow> nutritionRows = prepareData();
		
		NutritionListAdapter workoutListAdapter = new NutritionListAdapter(this.getActivity(), nutritionRows);
		mNutritionList.setAdapter(workoutListAdapter);
		mNutritionList.setOnItemClickListener(eventHandler);
		eventHandler.setNutritionRows(nutritionRows);
		
		RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.VISIBLE);
		
		return view;
	}
	
	private List<NutritionRow> prepareData() {
		Map<String, NutritionCategoryBean> nutritionsMap = FxpApp.nutritionsMap;
		if(nutritionsMap == null){
			return new ArrayList<>();
		}
		
		List<NutritionRow> nutritionRows = new ArrayList<>();
		for (String nutritionName : nutritionsMap.keySet()) {
			NutritionCategoryBean nutritionCategoryBean = nutritionsMap.get(nutritionName);

			assert nutritionCategoryBean != null;
			String des = nutritionCategoryBean.getDescription();
			
			int imageId = R.drawable.breakfast_icon;
			try {
				imageId = CommonUtils.getId(nutritionCategoryBean.getIconId() + "_" + ICON_PREFIX, R.drawable.class);
			} catch (SecurityException e) {
			} catch (IllegalArgumentException e) {
			} catch (NoSuchFieldException e) {
			} catch (IllegalAccessException e) {
			}
			
			NutritionRow nutritionRow = new NutritionRow(nutritionName, imageId, des);
			nutritionRows.add(nutritionRow);
		}
		
		NutritionRow[] nutritionRowsArr = new NutritionRow[4];
		for (NutritionRow nutritionRow : nutritionRows) {
			if(nutritionRow.getName().equals("Breakfast")) {
				nutritionRowsArr[0] = nutritionRow;
			} else if(nutritionRow.getName().equals("Snack")) {
				nutritionRowsArr[1] = nutritionRow;
			} else if(nutritionRow.getName().equals("Lunch")) {
				nutritionRowsArr[2] = nutritionRow;
			} else {
				nutritionRowsArr[3] = nutritionRow;
			}
		}
		
		return Arrays.asList(nutritionRowsArr);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mTitle.setTypeface(FxpApp.helveticaNeue);
	}
}
