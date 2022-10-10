package com.scopic.fxp.ui.event;

import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.NutritionRow;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.ActivityMenu;
import com.scopic.fxp.ui.screen.ActivityNutritionDetail;
import com.scopic.fxp.ui.screen.ActivityNutritionList;

public class NutritionListScreenEventHandler implements OnClickListener, OnItemClickListener {

	private List<NutritionRow> nutritionRows;

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
		NutritionRow nutritionRow = (NutritionRow) nutritionRows.get(position);
		if(nutritionRow == null) return;
		FxpApp.menuBar.getCurrentViewSelected().setSelected(false);
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getNutrition());

		FragmentTransaction transaction = fragmentManager.beginTransaction();
		ActivityNutritionDetail activityNutritionDetail = new ActivityNutritionDetail();
		activityNutritionDetail.setNutritionName(nutritionRow.getName());
		transaction.replace(R.id.fragment, activityNutritionDetail, Params.NUTRITION_DETAIL_SCREEN);
		// Commit the transaction
		transaction.commit();
	}

	public List<NutritionRow> getNutritionRows() {
		return nutritionRows;
	}

	public void setNutritionRows(List<NutritionRow> nutritionRows) {
		this.nutritionRows = nutritionRows;
	}
}
