package com.mteam.nutri.ui.screen;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class AbstractDialog extends DialogFragment {
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, android.R.style.Theme_Holo_Dialog);
	}
	
}
