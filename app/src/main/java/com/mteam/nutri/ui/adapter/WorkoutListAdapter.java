package com.mteam.nutri.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.bean.WorkoutRow;
import com.mteam.nutri.R;

import java.util.List;

public class WorkoutListAdapter extends BaseAdapter {

	private Context mContext;

	private List<WorkoutRow> mItems;

	public WorkoutListAdapter(Context context, List<WorkoutRow> items) {
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
			v = inflater.inflate(R.layout.workout_list_row, null);
		}

		WorkoutRow workoutRow = (WorkoutRow) getItem(position);
		String name = workoutRow.getName();
		int logo = workoutRow.getLogo();

		TextView txtName = (TextView) v.findViewById(R.id.txtName);
		ImageView imgLogo = (ImageView) v.findViewById(R.id.imgLogo);
		
		txtName.setText(name);
		txtName.setTypeface(FxpApp.helveticaNeue);
		imgLogo.setImageResource(logo);

		return v;
	}

}
