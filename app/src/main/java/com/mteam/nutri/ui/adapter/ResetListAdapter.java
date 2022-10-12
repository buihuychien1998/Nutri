package com.mteam.nutri.ui.adapter;

//public class ResetListAdapter extends BaseAdapter {

//	private Context mContext;
//
//	private List<ResetRestoreRow> mItems;
//
//	public ResetListAdapter(Context context, List<ResetRestoreRow> items) {
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
//			v = inflater.inflate(R.layout.reset_restore_row, null);
//		}
//
//		ResetRestoreRow resetRestoreRow = (ResetRestoreRow) getItem(position);
//		final String name = resetRestoreRow.getName();
//		String buttonName = resetRestoreRow.getButtonName();
//
//		TextView txtName = (TextView) v.findViewById(R.id.txtName);
//		Button btnReset = (Button) v.findViewById(R.id.btnResetRestore);
//
//		btnReset.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				WorkoutData workoutData = new WorkoutData();
//				workoutData.setWorkoutName(name);
//
////				DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
////				databaseHelper.delete(workoutData);
//				FxpApp.currentDays.put(FxpApp.currentWorkoutName, 0);
//
//				Toast.makeText(mContext, "Workout is reset!", Toast.LENGTH_SHORT).show();
//
//				//List<WorkoutData> workoutDatas = databaseHelper.select(WorkoutData.class, null, null, null, null);
//				List<WorkoutRow> workoutRows = new ArrayList<WorkoutRow>();
//				for (WorkoutData workout : workoutDatas) {
//					WorkoutRow workoutRow = new WorkoutRow(workout.getWorkoutName(), R.drawable.photo2_icon_nutrition_guide_scr);
//					workoutRows.add(workoutRow);
//				}
//
//				List<ResetRestoreRow> resetRestoreRows = new ArrayList<ResetRestoreRow>();
//				for (int i = 0; i < workoutRows.size(); i++) {
//					ResetRestoreRow resetRestoreRow = new ResetRestoreRow(workoutRows.get(i).getName(), "Reset");
//					resetRestoreRows.add(resetRestoreRow);
//				}
//
//				ResetListAdapter resetListAdapter = new ResetListAdapter(mContext, resetRestoreRows);
//				FragmentManager fragmentManager = ((FragmentActivity)mContext).getSupportFragmentManager();
//				ActivityResetRestoreList activityResetRestoreList = (ActivityResetRestoreList) fragmentManager.findFragmentByTag(Params.RESET_RESTORE_SCREEN);
//				activityResetRestoreList.getExercisesList().setAdapter(resetListAdapter);
//				activityResetRestoreList.getExercisesList().setOnItemClickListener(activityResetRestoreList.getEventHandler());
//				activityResetRestoreList.getEventHandler().setResetRestoreRows(resetRestoreRows);
//
//				List<UserData> userDatas = databaseHelper.select(UserData.class, null, null, null, null);
//
//				UserData userData = null;
//			    if(userDatas.size() != 0) {
//			    	userData = userDatas.get(0);
//				}
//
//			    if(userData == null) {
//					userData = new UserData();
//				}
//
//			    userData.setFuelPoints(new HashMap<String, Long>());
//
//			    if(databaseHelper.update(userData) == 0) {
//					databaseHelper.insert(userData);
//				}
//			}
//		});
//
//		txtName.setText(name);
//		txtName.setTypeface(FxpApp.helveticaNeue);
//		btnReset.setText(buttonName);
//		btnReset.setTypeface(FxpApp.helveticaNeue);
//
//		return v;
//	}

//}
