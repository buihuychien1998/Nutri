package com.scopic.fxp.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.scopic.fxp.FxpApp;
import com.scopic.fxp.R;
import com.scopic.fxp.bean.ResetRestoreRow;
import com.scopic.fxp.bean.UserData;
import com.scopic.fxp.ui.billing.PurchaseManager;

public class RestoreListAdapter extends BaseAdapter {

	private Context mContext;

	private List<ResetRestoreRow> mItems;

	public RestoreListAdapter(Context context, List<ResetRestoreRow> items) {
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
			v = inflater.inflate(R.layout.reset_restore_row, null);
		}

		ResetRestoreRow resetRestoreRow = (ResetRestoreRow) getItem(position);
		final String name = resetRestoreRow.getName();
		String buttonName = resetRestoreRow.getButtonName();

		TextView txtName = (TextView) v.findViewById(R.id.txtName);
		Button btnRestore = (Button) v.findViewById(R.id.btnResetRestore);
		
		btnRestore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
				//List<UserData> userDatas = databaseHelper.select(UserData.class, null, null, null, null);
				
				ArrayList<String> ownedSignatureWorkouts = PurchaseManager.getOwnedProductIds(mContext);
				
				if(ownedSignatureWorkouts == null) {
					Toast.makeText(mContext, mContext.getString(R.string.purchases_restore_failed), Toast.LENGTH_SHORT).show();
					return;
				}
				
				for (String premiumName : FxpApp.premiumWorkoutsMap.keySet()) {
					String purchaseId = FxpApp.premiumWorkoutsMap.get(premiumName).get(0).getPurchaseId();
					
					if(ownedSignatureWorkouts.contains(purchaseId)) {
						UserData userData = null;
//					    if(userDatas.size() != 0) {
//					    	userData = userDatas.get(0);
//						}
							
						if(userData == null) {
							userData = new UserData();
						}
						  
						userData.getBoughtPremiumWorkouts().add(purchaseId);
				    	  
//						if(databaseHelper.update(userData) == 0) {
//							databaseHelper.insert(userData);
//						}
					}
				}
				
				Toast.makeText(mContext, mContext.getString(R.string.purchases_restore_succeeded), Toast.LENGTH_SHORT).show();
			}
		});
		
		txtName.setText(name);
		txtName.setTypeface(FxpApp.helveticaNeue);
		btnRestore.setText(buttonName);
		btnRestore.setTypeface(FxpApp.helveticaNeue);

		return v;
	}

}
