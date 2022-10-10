package com.scopic.fxp.ui.adapter;

import java.util.List;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class DayPagerAdapter extends FragmentStatePagerAdapter {
	private List<Fragment> fragments;

	/**
	 * @param fm
	 * @param fragments
	 */
	public DayPagerAdapter(FragmentManager fm, List<Fragment> fragments, Context context) {
		super(fm);
		this.fragments = fragments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 */
	@NonNull
	@Override
	public Fragment getItem(int position) {
		return this.fragments.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return this.fragments.size();
	}
	
}
