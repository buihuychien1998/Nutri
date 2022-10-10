package com.scopic.fxp.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.ExerciseRow;
import com.scopic.fxp.ui.commons.Params;
import com.scopic.fxp.ui.screen.AbstractActivity;
import com.scopic.fxp.ui.screen.ActivityVideoDemo;

public class WorkoutDetailListAdapter extends BaseAdapter {

	private Context mContext;

	private List<ExerciseRow> mItems;

	public WorkoutDetailListAdapter(Context context, List<ExerciseRow> items) {
		mContext = context;
		mItems = items;
	}

	@Override
	public int getCount() {
		return mItems == null ? 0 : mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.workout_detail_row, null);
		}

		ExerciseRow exerciseRow = (ExerciseRow) getItem(position);
		final String name = exerciseRow.getName();
		int logo = exerciseRow.getLogo();
		String subName = exerciseRow.getSubName();

		TextView txtName = (TextView) v.findViewById(R.id.txtName);
		ImageView imgLogo = (ImageView) v.findViewById(R.id.imgLogo);
		TextView txtSubName = (TextView) v.findViewById(R.id.txtSubName);
		Button btnPlay = (Button) v.findViewById(R.id.btnPlay);
		
		txtName.setText("\n" + name);
		txtName.setTypeface(FxpApp.helveticaNeueBold);
		txtSubName.setText(subName);
		txtSubName.setTypeface(FxpApp.helveticaNeueThin);
		imgLogo.setImageResource(logo);
		
		btnPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FxpApp.currentExerciseName = name;
				if(FxpApp.exercisesMap.get(FxpApp.currentExerciseName) != null) {
					FragmentTransaction transaction = ((AbstractActivity)mContext).getSupportFragmentManager().beginTransaction();
					ActivityVideoDemo activityVideoDemo = new ActivityVideoDemo();
					
					activityVideoDemo.setFromWorkoutDetail(true);
					activityVideoDemo.setVideoUrl(FxpApp.exercisesMap.get(FxpApp.currentExerciseName).getYoutubeURL());
					transaction.replace(R.id.fragment, activityVideoDemo, Params.VIDEO_DEMO_SCREEN);
					transaction.commit();
				}
			}
		});

		return v;
	}

}
