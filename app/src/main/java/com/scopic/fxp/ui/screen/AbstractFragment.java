package com.scopic.fxp.ui.screen;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import androidx.fragment.app.Fragment;

import com.scopic.fxp.ui.utils.CommonUtils;

public abstract class AbstractFragment extends Fragment {

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if(!CommonUtils.isTablet(requireActivity())) {
			requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
	}
	
}
