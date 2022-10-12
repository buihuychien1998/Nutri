package com.mteam.nutri.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mteam.nutri.ui.screen.Items;
import com.mteam.nutri.R;

import java.util.List;

public class NutritionListAdapter extends BaseAdapter {

	private Context mContext;

	private List<Items> mItems;

	public NutritionListAdapter(Context context, List<Items> items) {
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

		Items nutritionRow = (Items) getItem(position);
		if(nutritionRow != null){
			String name = nutritionRow.getTitle();

			TextView txtName = (TextView) v.findViewById(R.id.txtName);

			txtName.setText(name);
		}

		return v;
	}

}
