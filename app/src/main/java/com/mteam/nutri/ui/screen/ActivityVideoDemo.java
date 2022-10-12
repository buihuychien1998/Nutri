package com.mteam.nutri.ui.screen;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.mteam.nutri.youtube.DeveloperKey;
import com.mteam.nutri.R;

public class ActivityVideoDemo extends Fragment implements
		YouTubePlayer.OnInitializedListener {

	private YouTubePlayerSupportFragment youTubeView;

	private static final int RECOVERY_DIALOG_REQUEST = 1;
	
	private boolean isFullScreen = false;
	
	private YouTubePlayer videoPlayer;
	
	private String videoUrl;
	
	private boolean fromWorkoutDetail = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_video_demo, container,
				false);

		youTubeView = (YouTubePlayerSupportFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);

		youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);

		RelativeLayout menubarLayout = (RelativeLayout) getActivity()
				.findViewById(R.id.menubarLayout);
		menubarLayout.setVisibility(View.GONE);
		getActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		
		return view;
	}
	
	@Override
	public void onInitializationSuccess(Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
		if (!wasRestored) {
			videoPlayer = player;
			videoPlayer.setFullscreen(true);
	        videoPlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {

	            @Override
	            public void onFullscreen(boolean _isFullScreen) {
	            	isFullScreen = _isFullScreen;
	            }
	        });

	        if(videoUrl == null || videoUrl.equals("")) {
	        	Toast.makeText(getActivity(), getString(R.string.error_not_available), Toast.LENGTH_LONG).show();
	        	return;
	        }
			player.loadVideo(videoUrl);
		}
	}

	protected Provider getYouTubePlayerProvider() {
		return (YouTubePlayerSupportFragment) getFragmentManager().findFragmentById(R.id.youtube_fragment);
	}

	@Override
	public void onInitializationFailure(Provider provider,
                                        final YouTubeInitializationResult errorReason) {
		if (errorReason.isUserRecoverableError()) {
			errorReason.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST)
					.show();
		} else if(errorReason.equals(YouTubeInitializationResult.NETWORK_ERROR)) {
			String errorMessage = String.format(
					getString(R.string.error_network), errorReason.toString());
			Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG)
					.show();
		} else {
			String errorMessage = String.format(
					getString(R.string.error_player), errorReason.toString());
			Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RECOVERY_DIALOG_REQUEST) {
			// Retry initialization if user performed a recovery action
			getYouTubePlayerProvider().initialize(DeveloperKey.DEVELOPER_KEY,
					this);
		}
	}

	public boolean isFullScreen() {
		return isFullScreen;
	}

	public YouTubePlayer getVideoPlayer() {
		return videoPlayer;
	}

	public YouTubePlayerSupportFragment getYouTubeView() {
		return youTubeView;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	/**
	 * @return the fromWorkoutDetail
	 */
	public boolean isFromWorkoutDetail() {
		return fromWorkoutDetail;
	}

	/**
	 * @param fromWorkoutDetail the fromWorkoutDetail to set
	 */
	public void setFromWorkoutDetail(boolean fromWorkoutDetail) {
		this.fromWorkoutDetail = fromWorkoutDetail;
	}
}
