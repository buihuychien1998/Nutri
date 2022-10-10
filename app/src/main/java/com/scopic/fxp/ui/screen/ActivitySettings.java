package com.scopic.fxp.ui.screen;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.SettingRow;
import com.scopic.fxp.ui.adapter.SettingListAdapter;
import com.scopic.fxp.ui.event.SettingsScreenEventHandler;

public class ActivitySettings extends AbstractFragment {

	private TextView mTitle;
	
	private View mBack;

	private ListView mSettingList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_settings, container, false);

		mTitle = (TextView) view.findViewById(R.id.title);
		mSettingList = (ListView) view.findViewById(R.id.settings);
		mBack = view.findViewById(R.id.imgBack);

		SettingsScreenEventHandler eventHandler = new SettingsScreenEventHandler(this);
		mBack.setOnClickListener(eventHandler);

		Resources res = getResources();
		String[] settingList = res.getStringArray(R.array.setting_list);

		List<SettingRow> settingRows = new ArrayList<SettingRow>();
		for (int i = 0; i < settingList.length; i++) {
			SettingRow settingRow = new SettingRow(settingList[i]);
			settingRows.add(settingRow);
		}

		SettingListAdapter appListAdapter = new SettingListAdapter(this.getActivity(), settingRows);
		mSettingList.setAdapter(appListAdapter);
		mSettingList.setOnItemClickListener(eventHandler);
		eventHandler.setSettingRows(settingRows);
		
		RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.VISIBLE);

		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mTitle.setTypeface(FxpApp.helveticaNeue);
	}
}
