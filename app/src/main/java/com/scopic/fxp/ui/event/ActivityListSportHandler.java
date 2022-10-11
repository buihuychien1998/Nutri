package com.scopic.fxp.ui.event;

import android.view.View;
import android.widget.AdapterView;

import androidx.fragment.app.FragmentManager;

import com.scopic.fxp.bean.WorkoutRow;
import com.scopic.fxp.ui.screen.ActivityListSport;
import com.scopic.fxp.ui.screen.ActivityPremiumWorkoutList;
import com.scopic.fxp.ui.screen.Item;

import java.util.List;

public class ActivityListSportHandler implements View.OnClickListener, AdapterView.OnItemClickListener {

    private FragmentManager fragmentManager;

    ActivityListSport activityWorkoutList;

    private List<Item> workoutRows;



    public ActivityListSportHandler(
            ActivityListSport activityWorkoutList) {
        super();

        this.activityWorkoutList = activityWorkoutList;

        fragmentManager = activityWorkoutList.getActivity().getSupportFragmentManager();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public List<Item> getWorkoutRows() {
        return workoutRows;
    }

    public void setWorkoutRows(List<Item> workoutRows) {
        this.workoutRows = workoutRows;
    }
}
