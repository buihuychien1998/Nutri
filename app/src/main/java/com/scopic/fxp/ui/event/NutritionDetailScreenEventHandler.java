package com.scopic.fxp.ui.event;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.ActivityNutritionDetail;
import com.scopic.fxp.ui.screen.ActivityNutritionList;

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
