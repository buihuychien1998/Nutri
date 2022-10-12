package com.mteam.nutri.ui.screen;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.ui.event.ShareDialogEventHandler;
import com.mteam.nutri.R;

public class ShareDialog extends AbstractDialog {

	private LinearLayout mShareFacebook;
	
	private LinearLayout mShareTwitter;
	
	private String postMessage;
	
	private TextView mTxtFacebook;
	
	private TextView mTxtTwitter;
	
	public static ShareDialog newInstance() {
		ShareDialog standardFramesDialog = new ShareDialog();

		return standardFramesDialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_share, container, false);

		mTxtFacebook = (TextView) v.findViewById(R.id.txtFacebook);
		mTxtTwitter = (TextView) v.findViewById(R.id.txtTwitter);
		mShareFacebook = (LinearLayout) v.findViewById(R.id.layoutFacebook);
		mShareTwitter = (LinearLayout) v.findViewById(R.id.layoutTwitter);
		
		ShareDialogEventHandler eventHandler = new ShareDialogEventHandler(this);
		mShareFacebook.setOnClickListener(eventHandler);
		mShareTwitter.setOnClickListener(eventHandler);
		eventHandler.setPostMessage(postMessage);
		
		WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();

		params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.RIGHT;
		int marginRight = Math.round(getResources().getDimension(R.dimen.dialog_standard_frame_margin_right));
		int marginTop = Math.round(getResources().getDimension(R.dimen.dialog_standard_frame_margin_top));

		DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        
		params.x = marginRight;
		params.y = - width / 2 + marginTop;
		
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		getDialog().getWindow().setAttributes(params);
		
		return v;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
		params.dimAmount = 0.0f;
		params.flags &= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
	    getDialog().getWindow().setAttributes(params);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		// Set font
		mTxtFacebook.setTypeface(FxpApp.helveticaNeue);
		mTxtTwitter.setTypeface(FxpApp.helveticaNeue);
	}

	public String getPostMessage() {
		return postMessage;
	}

	public void setPostMessage(String postMessage) {
		this.postMessage = postMessage;
	}
}
