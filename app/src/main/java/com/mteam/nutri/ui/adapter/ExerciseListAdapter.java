package com.mteam.nutri.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.bean.ExerciseRow;
import com.mteam.nutri.R;

import java.util.List;

public class ExerciseListAdapter extends BaseAdapter {

	private Context mContext;

	private List<ExerciseRow> mItems;

	public ExerciseListAdapter(Context context, List<ExerciseRow> items) {
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
			v = inflater.inflate(R.layout.exercise_row, null);
		}

		ExerciseRow exerciseRow = (ExerciseRow) getItem(position);
		String name = exerciseRow.getName();
		int logo = exerciseRow.getLogo();
		String subName = exerciseRow.getSubName();

		TextView txtName = (TextView) v.findViewById(R.id.txtName);
		ImageView imgLogo = (ImageView) v.findViewById(R.id.imgLogo);
		TextView txtSubName = (TextView) v.findViewById(R.id.txtSubName);
		
		txtName.setText(name);
		txtName.setTypeface(FxpApp.helveticaNeueBold);
		txtSubName.setText(subName);
		txtSubName.setTypeface(FxpApp.helveticaNeueThin);
		imgLogo.setImageResource(logo);

		return v;
	}

}
