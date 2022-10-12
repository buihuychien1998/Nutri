package com.mteam.nutri.ui.screen;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.bean.NutritionCategoryBean;
import com.mteam.nutri.ui.components.CustomTypefaceSpan;
import com.mteam.nutri.ui.event.NutritionDetailScreenEventHandler;
import com.mteam.nutri.ui.utils.CommonUtils;
import com.mteam.nutri.R;

public class ActivityNutritionDetail extends AbstractFragment {

	private TextView mTitle;
	
	private View mBack;
	
	private TextView mContents1;
	
	private TextView mContents2;
	
	private ImageView mImageContents2;
	
	private String nutritionName;
	
	public static final String THUMB_PREFIX = "thumb";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_nutrition_detail,
		        container, false);
		
		mTitle = (TextView) view.findViewById(R.id.title);
		mBack = view.findViewById(R.id.imgBack);
		mContents1 = (TextView) view.findViewById(R.id.txtContents1);
		mContents2 = (TextView) view.findViewById(R.id.txtContents2);
		mImageContents2 = (ImageView) view.findViewById(R.id.imgContents2);
		
		NutritionDetailScreenEventHandler eventHandler = new NutritionDetailScreenEventHandler(this);
		mBack.setOnClickListener(eventHandler);
		
		RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.VISIBLE);
		
		prepareData();
		
		return view;
	}
	
	private void prepareData() {
		mTitle.setText(nutritionName);
		
		NutritionCategoryBean nutritionCategoryBean = FxpApp.nutritionsMap.get(nutritionName);
		
		String healthyChoices = "";
		for (String healthyChoice : nutritionCategoryBean.getHealthyChoices()) {
			healthyChoices += healthyChoice + ", ";
		}
		if(healthyChoices.length() > 1) {
			healthyChoices.substring(0, healthyChoices.length() - 2);
		}
		
		String contents1 = nutritionName.toUpperCase() + "\n\n" + nutritionCategoryBean.getDescription();
		
		String contents2 = getString(R.string.nu_healthy) + "\n\n" + healthyChoices;
		
		SpannableString ss1=  new SpannableString(contents1);
		ss1.setSpan(new RelativeSizeSpan(1.5f), 0, nutritionName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // set size
		ss1.setSpan (new CustomTypefaceSpan("", FxpApp.helveticaNeueBold), 0, nutritionName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss1.setSpan (new CustomTypefaceSpan("", FxpApp.helveticaNeue), nutritionName.length(), ss1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss1.setSpan(new StyleSpan(Typeface.ITALIC), nutritionName.length(), ss1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		mContents1.setText(ss1); 
		
		SpannableString ss2=  new SpannableString(contents2);
		ss2.setSpan(new RelativeSizeSpan(1.5f), 0, getString(R.string.nu_healthy).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // set size
		ss2.setSpan (new CustomTypefaceSpan("", FxpApp.helveticaNeueBold), 0, getString(R.string.nu_healthy).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss2.setSpan (new CustomTypefaceSpan("", FxpApp.helveticaNeue), getString(R.string.nu_healthy).length(), ss2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss2.setSpan(new StyleSpan(Typeface.ITALIC), getString(R.string.nu_healthy).length(), ss2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		mContents2.setText(ss2); 
		
		int imageId = R.drawable.breakfast_thumb;
		try {
			imageId = CommonUtils.getId(nutritionCategoryBean.getIconId() + "_" + THUMB_PREFIX, R.drawable.class);
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (NoSuchFieldException e) {
		} catch (IllegalAccessException e) {
		}
		mImageContents2.setImageResource(imageId);
	}

	public String getNutritionName() {
		return nutritionName;
	}

	public void setNutritionName(String nutritionName) {
		this.nutritionName = nutritionName;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mTitle.setTypeface(FxpApp.helveticaNeue);
//		mContents1.setTypeface(FxpApp.helveticaNeue);
//		mContents2.setTypeface(FxpApp.helveticaNeue);
	}
}
