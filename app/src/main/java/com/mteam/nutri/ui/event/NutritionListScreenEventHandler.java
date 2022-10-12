package com.mteam.nutri.ui.event;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.ui.commons.Params;
import com.mteam.nutri.ui.screen.ActivityMenu;
import com.mteam.nutri.ui.screen.ActivityNutritionList;
import com.mteam.nutri.ui.screen.Items;
import com.mteam.nutri.R;

import java.util.List;

public class NutritionListScreenEventHandler implements OnClickListener, OnItemClickListener {

	private List<Items> nutritionRows;

	private FragmentManager fragmentManager;

	public NutritionListScreenEventHandler(ActivityNutritionList activityNutritionList) {
		super();

		fragmentManager = activityNutritionList.getActivity().getSupportFragmentManager();
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
		Items nutritionRow = (Items) nutritionRows.get(position);
		if(nutritionRow == null) return;
		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getNutrition());

//		FragmentTransaction transaction = fragmentManager.beginTransaction();
//		ActivityNutritionDetail activityNutritionDetail = new ActivityNutritionDetail();
//		activityNutritionDetail.setNutritionName(nutritionRow.getName());
//		transaction.replace(R.id.fragment, activityNutritionDetail, Params.NUTRITION_DETAIL_SCREEN);
//		// Commit the transaction
//		transaction.commit();
	}

	public List<Items> getNutritionRows() {
		return nutritionRows;
	}

	public void setNutritionRows(List<Items> nutritionRows) {
		this.nutritionRows = nutritionRows;
	}
}
