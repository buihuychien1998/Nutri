package com.mteam.nutri.ui.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.ui.event.WeightUpdateDialogEventHandler;
import com.mteam.nutri.R;

public class WeightUpdateDialog extends AbstractDialog {

	private TextView mNote;
	
	private Button mCancel;
	
	private Button mOk;
	
	private EditText mWeight;
	
	public static WeightUpdateDialog newInstance() {
		WeightUpdateDialog saveAsNewProjectDialog = new WeightUpdateDialog();

		return saveAsNewProjectDialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_update_weight, container, false);

		mNote = (TextView) v.findViewById(R.id.txtNote);
		mCancel = (Button) v.findViewById(R.id.btnCancel);
		mOk = (Button) v.findViewById(R.id.btnOk);
		mWeight = (EditText) v.findViewById(R.id.edtWeight);
		
		mWeight.setSelection(mWeight.getText().length());
		
		WeightUpdateDialogEventHandler eventHandler = new WeightUpdateDialogEventHandler(this);
		mCancel.setOnClickListener(eventHandler);
		mOk.setOnClickListener(eventHandler);
		mWeight.setOnClickListener(eventHandler);
		
		return v;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		// Set font
		mNote.setTypeface(FxpApp.helveticaNeue);
		mCancel.setTypeface(FxpApp.helveticaNeue);
		mOk.setTypeface(FxpApp.helveticaNeue);
		mWeight.setTypeface(FxpApp.helveticaNeue);
	}

	public TextView getNote() {
		return mNote;
	}

	public Button getCancel() {
		return mCancel;
	}

	public Button getOk() {
		return mOk;
	}

	public EditText getWeight() {
		return mWeight;
	}

}
