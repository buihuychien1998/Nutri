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
import com.scopic.fxp.bean.SettingRow;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.ActivityFuelList;
import com.scopic.fxp.ui.screen.ActivityMenu;
import com.scopic.fxp.ui.screen.ActivityResetRestoreList;
import com.scopic.fxp.ui.screen.ActivitySettings;

public class SettingsScreenEventHandler implements OnClickListener, OnItemClickListener {

	private List<SettingRow> settingRows;

	private FragmentManager fragmentManager;

	public SettingsScreenEventHandler(ActivitySettings activitySettings) {
		super();

		fragmentManager = activitySettings.getActivity().getSupportFragmentManager();
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
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		
		switch (position) {
		case 0:
			transaction.replace(R.id.fragment, new ActivityFuelList(), Params.FUEL_LIST_SCREEN);
			break;

		case 1:
			transaction.replace(R.id.fragment, new ActivityResetRestoreList(), Params.RESET_RESTORE_SCREEN);
			break;
		}
		
		// Commit the transaction
		transaction.commit();
	}

	public List<SettingRow> getSettingRows() {
		return settingRows;
	}

	public void setSettingRows(List<SettingRow> settingRows) {
		this.settingRows = settingRows;
	}

}
