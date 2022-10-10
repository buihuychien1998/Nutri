package com.scopic.fxp.ui.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.NutritionRow;

public class NutritionListAdapter extends BaseAdapter {

	private Context mContext;

	private List<NutritionRow> mItems;

	public NutritionListAdapter(Context context, List<NutritionRow> items) {
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

		NutritionRow nutritionRow = (NutritionRow) getItem(position);
		if(nutritionRow != null){
			String name = nutritionRow.getName();
			int logo = nutritionRow.getLogo();
			String subName = nutritionRow.getSubName();

			TextView txtName = (TextView) v.findViewById(R.id.txtName);
			ImageView imgLogo = (ImageView) v.findViewById(R.id.imgLogo);
			TextView txtSubName = (TextView) v.findViewById(R.id.txtSubName);

			txtName.setText(name);
			txtName.setTypeface(FxpApp.helveticaNeueBold);
			txtSubName.setText(subName);
			txtSubName.setTypeface(FxpApp.helveticaNeueThin);
			imgLogo.setImageResource(logo);
		}

		return v;
	}

}
