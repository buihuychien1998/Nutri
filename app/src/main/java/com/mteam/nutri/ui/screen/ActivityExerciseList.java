package com.mteam.nutri.ui.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.bean.ExerciseBean;
import com.mteam.nutri.bean.ExerciseRow;
import com.mteam.nutri.ui.adapter.ExerciseListAdapter;
import com.mteam.nutri.ui.event.ExerciseListScreenEventHandler;
import com.mteam.nutri.ui.utils.CommonUtils;
import com.mteam.nutri.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ActivityExerciseList extends AbstractFragment {

	private TextView mTitle;

	private View mBack;

	private ListView mExercisesList;
	
	public static final String ICON_PREFIX = "icon";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_exercises_list, container, false);

		mTitle = (TextView) view.findViewById(R.id.title);
		mExercisesList = (ListView) view.findViewById(R.id.exercises);
		mBack = view.findViewById(R.id.imgBack);

		ExerciseListScreenEventHandler eventHandler = new ExerciseListScreenEventHandler(this);
		mBack.setOnClickListener(eventHandler);

		List<ExerciseRow> exerciseRows = prepareData();

		ExerciseListAdapter workoutListAdapter = new ExerciseListAdapter(this.getActivity(), exerciseRows);
		mExercisesList.setAdapter(workoutListAdapter);
		mExercisesList.setOnItemClickListener(eventHandler);
		eventHandler.setExerciseRows(exerciseRows);

		RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.VISIBLE);

		return view;
	}

	private List<ExerciseRow> prepareData() {
		Map<String, ExerciseBean> exercisesMap = FxpApp.exercisesMap;
		
		List<ExerciseRow> exerciseRows = new ArrayList<ExerciseRow>();
		for (String exerciseName : exercisesMap.keySet()) {
			ExerciseBean exerciseBean = exercisesMap.get(exerciseName);
			
			String des = exerciseBean.getDescription();
			
			int imageId = R.drawable.icon1;
			try {
				imageId = CommonUtils.getId(ICON_PREFIX + exerciseBean.getId(), R.drawable.class);
			} catch (SecurityException e) {
			} catch (IllegalArgumentException e) {
			} catch (NoSuchFieldException e) {
			} catch (IllegalAccessException e) {
			}
			
			ExerciseRow exerciseRow = new ExerciseRow(exerciseName, imageId, des);
			exerciseRows.add(exerciseRow);
		}

		if (exerciseRows.size() > 0) {
			Collections.sort(exerciseRows, new Comparator<ExerciseRow>() {
				@Override
				public int compare(final ExerciseRow object1, final ExerciseRow object2) {
					return object1.getName().compareTo(object2.getName());
				}
			});
		}

		return exerciseRows;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		mTitle.setTypeface(FxpApp.helveticaNeue);
	}
}
