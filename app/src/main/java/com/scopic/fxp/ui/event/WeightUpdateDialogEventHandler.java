package com.scopic.fxp.ui.event;

import java.util.List;

import android.content.Context;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

import com.scopic.fxp.R;
import com.scopic.fxp.bean.UserData;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.AbstractDialog;
import com.scopic.fxp.ui.screen.WeightUpdateDialog;

public class WeightUpdateDialogEventHandler implements OnClickListener {

	private FragmentManager fragmentManager;
	
	private WeightUpdateDialog weightUpdateDialog;

	public WeightUpdateDialogEventHandler(WeightUpdateDialog weightUpdateDialog) {
		super();

		this.weightUpdateDialog = weightUpdateDialog;
		fragmentManager = weightUpdateDialog.getActivity().getSupportFragmentManager();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnCancel:
			cancel();
			break;

		case R.id.btnOk:
			done();
			break;

		}
	}

	private void cancel() {
		closeDialog(Params.UPDATE_WEIGHT_DIALOG);
	}

	private void done() {

		
		UserData userData = null;

	    
	    if(userData == null) {
			userData = new UserData();
		}
	    
	    try {
			userData.setCurrentWeight(Integer.parseInt(weightUpdateDialog.getWeight().getText().toString()));
		} catch (NumberFormatException e) {
			userData.setCurrentWeight(125);
		}
		

		
		closeDialog(Params.UPDATE_WEIGHT_DIALOG);
	}

	public void closeDialog(String tag) {
		FragmentTransaction ft = fragmentManager.beginTransaction();
		AbstractDialog prev = (AbstractDialog) fragmentManager.findFragmentByTag(tag);
		if (prev != null) {
			prev.dismiss();
			ft.remove(prev);
		}
		
		InputMethodManager imm = (InputMethodManager) weightUpdateDialog.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		
		ft.commitAllowingStateLoss();
	}
}
