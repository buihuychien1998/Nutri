package com.mteam.nutri.ui.screen;

import android.os.Bundle;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.bean.ExerciseBean;
import com.mteam.nutri.ui.event.ExerciseDetailScreenEventHandler;
import com.mteam.nutri.ui.utils.CommonUtils;
import com.mteam.nutri.R;

import java.util.List;

public class ActivityExerciseDetail extends AbstractFragment {

	private TextView mTitle;
	
	private TextView mContents;
	
	private ImageView mThumbnail;
	
	private ImageView mPrev;
	
	private ImageView mNext;
	
	private View mBack;
	
	private View mVideo;
	
	public static final String THUMB_PREFIX = "thumb";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_exercises_detail,
		        container, false);
		
		mTitle = (TextView) view.findViewById(R.id.title);
		mBack = view.findViewById(R.id.imgBack);
		mVideo = view.findViewById(R.id.btnVideo);
		mContents = (TextView) view.findViewById(R.id.txtContents);
		mThumbnail = (ImageView) view.findViewById(R.id.imgSample);
		mPrev = (ImageView) view.findViewById(R.id.imgPrev);
		mNext = (ImageView) view.findViewById(R.id.imgNext);
		
		ExerciseDetailScreenEventHandler eventHandler = new ExerciseDetailScreenEventHandler(this);
		mBack.setOnClickListener(eventHandler);
		mVideo.setOnClickListener(eventHandler);
		mPrev.setOnClickListener(eventHandler);
		mNext.setOnClickListener(eventHandler);
		
		RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.VISIBLE);
		
		prepareData();
		
		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mTitle.setTypeface(FxpApp.helveticaNeue);
		mContents.setTypeface(FxpApp.helveticaNeue);
		((Button)mVideo).setTypeface(FxpApp.helveticaNeue);
	}

	public void prepareData() {
		String exerciseName = FxpApp.currentExerciseName;
		mTitle.setText(exerciseName);
		
		ExerciseBean exerciseBean = FxpApp.exercisesMap.get(exerciseName);
		
		String muscleTargets = "";
		for (String muscle : exerciseBean.getMuscleGroupItTargets()) {
			muscleTargets += muscle + ", ";
		}
		if (!muscleTargets.equals("")) {
			muscleTargets = muscleTargets.substring(0, muscleTargets.length() - 2);
		}
		
		String contents = exerciseName + "\n\n" 
				+ getString(R.string.ex_description) + "\n" + exerciseBean.getDescription() + "\n\n"
				+ getString(R.string.ex_muscle) + "\n" + muscleTargets + "\n\n"
				+ getString(R.string.ex_notes) + "\n" + exerciseBean.getNote() + "\n\n"
				+ getString(R.string.ex_focus) + "\n" + exerciseBean.getFocus();
		
		SpannableString ss1=  new SpannableString(contents);
		ss1.setSpan(new RelativeSizeSpan(1.5f), 0, exerciseName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // set size
		ss1.setSpan(new AlignmentSpan.Standard(Alignment.ALIGN_CENTER), 0, exerciseName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); // set align
		mContents.setText(ss1);
		
		int imageId = R.drawable.thumb1;
		try {
			imageId = CommonUtils.getId(THUMB_PREFIX + exerciseBean.getId(), R.drawable.class);
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		} catch (NoSuchFieldException e) {
		} catch (IllegalAccessException e) {
		}
		
		mThumbnail.setImageResource(imageId);
		
		List<ExerciseBean> exercisesList = FxpApp.exercisesList;
		int currentIndex = 0;
		for (int i = 0; i < exercisesList.size(); i++) {
			if(exercisesList.get(i).getName().equals(exerciseName)) {
				currentIndex = i;
				break;
			}
		}
		if(currentIndex == 0) {
			mPrev.setVisibility(View.INVISIBLE);
		}
		if(currentIndex == exercisesList.size() - 1) {
			mNext.setVisibility(View.INVISIBLE);
		}
	}

	public ImageView getPrev() {
		return mPrev;
	}

	public ImageView getNext() {
		return mNext;
	}
}
