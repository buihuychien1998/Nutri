package com.scopic.fxp.ui.event;

import android.view.View;
import android.view.View.OnClickListener;

import com.scopic.fxp.R;
import com.scopic.fxp.ui.screen.ShareDialog;
import com.scopic.fxp.ui.utils.SocialUtils;

public class ShareDialogEventHandler implements OnClickListener {

	private ShareDialog shareDialog;

	private String postMessage;

	public ShareDialogEventHandler(ShareDialog shareDialog) {
		super();

		this.shareDialog = shareDialog;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layoutFacebook:
			shareOnFacebook();
			break;

		case R.id.layoutTwitter:
			shareOnTwitter();
			break;
		}
	}

	private void shareOnFacebook() {
		SocialUtils.postMessage = postMessage;
		
		SocialUtils.shareOnFacebook();

		shareDialog.dismiss();
	}

	private void shareOnTwitter() {
		SocialUtils.postMessage = postMessage;
		
		SocialUtils.shareOnTwitter();

		shareDialog.dismiss();
	}

	public String getPostMessage() {
		return postMessage;
	}

	public void setPostMessage(String postMessage) {
		this.postMessage = postMessage;
	}
}
