package com.mteam.nutri.ui.event;

import android.view.View;
import android.view.View.OnClickListener;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.ui.commons.Params;
import com.mteam.nutri.ui.screen.ActivityNutritionDetail;
import com.mteam.nutri.ui.screen.ActivityNutritionList;
import com.mteam.nutri.R;

public class NutritionDetailScreenEventHandler implements OnClickListener {

	private FragmentManager fragmentManager;

	public NutritionDetailScreenEventHandler(ActivityNutritionDetail activityNutritionDetail) {
		super();

		fragmentManager = activityNutritionDetail.getActivity().getSupportFragmentManager();
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
		FxpApp.menuBar.setCurrentViewSelected(FxpApp.menuBar.getNutrition());
		transaction.replace(R.id.fragment, new ActivityNutritionList(), Params.NUTRITION_SCREEN);
	}

}
