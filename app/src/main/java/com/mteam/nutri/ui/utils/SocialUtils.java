package com.mteam.nutri.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.facebook.SessionEvents;
import com.mteam.nutri.twitter.PrepareRequestTokenActivity;
import com.mteam.nutri.twitter.TwitterUtils;

public class SocialUtils {

	private static final Handler mFacebookHandler = new Handler();

	private static final Handler mTwitterHandler = new Handler();

	private static final String TAG = "Facebook";

	public static String postMessage;

	public static void shareOnFacebook() {
		if (!isNetworkConnected(FxpApp.activityMain)) {
			Toast.makeText(FxpApp.activityMain, "Can not post message on Facebook!\nPlease check your internet connection.", Toast.LENGTH_LONG).show();
		} else {
			postMessage();
		}
	}

	@SuppressWarnings("unchecked")
	public static void shareOnTwitter() {
		if (!isNetworkConnected(FxpApp.activityMain)) {
			Toast.makeText(FxpApp.activityMain, "Can not post message on Twitter!\nPlease check your internet connection.", Toast.LENGTH_LONG).show();
		} else {
			new SendTweetTask().execute("http://example.com/image.png");
		}
	}

	@SuppressWarnings("deprecation")
	public static void postMessage() {
			SessionEvents.AuthListener listener = new SessionEvents.AuthListener() {

				@Override
				public void onAuthSucceed() {
					postMessageInThread();
				}

				@Override
				public void onAuthFail(String error) {
					mFacebookHandler.post(mUpdateFailedFacebookNotification);
				}
			};
			SessionEvents.addAuthListener(listener);

	}

	private static void postMessageInThread() {
		Thread t = new Thread() {
			public void run() {

				try {
					mFacebookHandler.post(mUpdateFacebookNotification);
				} catch (Exception ex) {
					Log.e(TAG, "Error sending msg", ex);
					mFacebookHandler.post(mUpdateFailedFacebookNotification);
				}
			}
		};
		t.start();
	}

	public static void sendTweet() {
		Thread t = new Thread() {
			public void run() {

				try {
					TwitterUtils.sendTweet(FxpApp.prefs, getPostMsg());
					mTwitterHandler.post(mUpdateTwitterNotification);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		};
		t.start();
	}

	@SuppressWarnings("rawtypes")
	private static class SendTweetTask extends AsyncTask {
		protected Object doInBackground(Object... urls) {
			if (TwitterUtils.isAuthenticated(FxpApp.prefs)) {
				sendTweet();
			} else {
				Intent i = new Intent(FxpApp.activityMain.getApplicationContext(), PrepareRequestTokenActivity.class);
				i.putExtra("tweet_msg", getPostMsg());
				FxpApp.activityMain.startActivity(i);
			}
			return null;
		}

		protected void onPostExecute(Object result) {
		}
	}

	private static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = cm.getActiveNetworkInfo();
		if (network != null) {
			return network.isAvailable();
		}
		return false;
	}

	final static Runnable mUpdateFacebookNotification = new Runnable() {
		public void run() {
			Toast.makeText(FxpApp.activityMain, "Facebook updated!", Toast.LENGTH_LONG).show();
		}
	};

	final static Runnable mUpdateFailedFacebookNotification = new Runnable() {
		public void run() {
			Toast.makeText(FxpApp.activityMain, "Can not post message on Facebook!", Toast.LENGTH_LONG).show();
		}
	};

	final static Runnable mUpdateTwitterNotification = new Runnable() {
		public void run() {
			Toast.makeText(FxpApp.activityMain, "Tweet sent!", Toast.LENGTH_LONG).show();
		}
	};

	private static String getPostMsg() {
		return postMessage;
	}
}
