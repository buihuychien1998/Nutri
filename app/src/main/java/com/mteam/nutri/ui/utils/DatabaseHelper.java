package com.mteam.nutri.ui.utils;//package com.mteam.nutri.ui.utils;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import com.mteam.nutri.bean.UserData;
//import com.mteam.nutri.bean.WorkoutData;
//
//public class DatabaseHelper extends SQLiteOpenHelper {
//	private static final int DB_VERSION = 1;
//	private static final String DB_NAME = "fxp.db";
//
//	public DatabaseHelper(Context context) {
//		super(context, DB_NAME, null, DB_VERSION);
//	}
//
//	@Override
//	public void onCreate(SQLiteDatabase db) {
//		createTable(db, WorkoutData.class, true);
//		createTable(db, UserData.class, true);
//	}
//
//	@Override
//	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		dropTable(db, WorkoutData.class, true);
//		dropTable(db, UserData.class, true);
//
//		onCreate(db);
//	}
//}
