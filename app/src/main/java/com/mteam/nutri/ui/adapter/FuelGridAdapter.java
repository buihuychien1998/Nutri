package com.mteam.nutri.ui.adapter;//package com.mteam.nutri.ui.adapter;
//
//import java.util.List;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.mteam.nutri.FxpApp;
//import com.mteam.nutri.R;
//import com.mteam.nutri.bean.FuelRow;
//
//public class FuelGridAdapter extends BaseAdapter {
//
//	private Context mContext;
//
//	private List<FuelRow> mItems;
//
//	public FuelGridAdapter(Context context, List<FuelRow> items) {
//		mContext = context;
//		mItems = items;
//	}
//
//	@Override
//	public int getCount() {
//		return mItems == null ? 0 : mItems.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return mItems.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		View v = convertView;
//		if (v == null) {
//			LayoutInflater inflater = (LayoutInflater) mContext
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			v = inflater.inflate(R.layout.fuel_grid_row, null);
//		}
//
//		FuelRow fuelRow = (FuelRow) getItem(position);
//		String day = fuelRow.getDay();
//		String point = fuelRow.getPoint();
//
//		TextView txtDay = (TextView) v.findViewById(R.id.txtDay);
//		TextView txtPoint = (TextView) v.findViewById(R.id.txtPoint);
//		TextView txtDays = (TextView) v.findViewById(R.id.txtDays);
//
//		txtDay.setText(day);
//		txtDay.setTypeface(FxpApp.helveticaNeue);
//		txtPoint.setText(point);
//		txtPoint.setTypeface(FxpApp.helveticaNeue);
//		txtDays.setTypeface(FxpApp.helveticaNeue);
//
//		return v;
//	}
//
//}
