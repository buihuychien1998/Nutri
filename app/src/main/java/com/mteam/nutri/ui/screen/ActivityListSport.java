package com.mteam.nutri.ui.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.bean.WorkoutBean;
import com.mteam.nutri.bean.WorkoutRow;
import com.mteam.nutri.ui.event.ActivityListSportHandler;
import com.mteam.nutri.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityListSport extends AbstractFragment {

    private TextView mTitle;

    private View mBack;

    private ListView mWorkoutList;

    final List<Item> items = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_sport,
                container, false);

        mTitle = (TextView) view.findViewById(R.id.title);
        mWorkoutList = (ListView) view.findViewById(R.id.workouts);
        mBack = view.findViewById(R.id.imgBack);
        initData();
        ActivityListSportHandler eventHandler = new ActivityListSportHandler(this,getContext());
        mBack.setOnClickListener(eventHandler);


        FoodAdapter workoutListAdapter = new FoodAdapter(this.requireActivity(), items);
        mWorkoutList.setAdapter(workoutListAdapter);
        mWorkoutList.setOnItemClickListener(eventHandler);
        eventHandler.setWorkoutRows(items);

        RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
        menubarLayout.setVisibility(View.VISIBLE);

        return view;
    }

    private void initData() {
        items.add(new Item("Treadmill",
                "https://www.johnsonfitness.com.my/store/image/cache/catalog/product/horizon/treadmill/7.4AT/main%20image%201600x-550x550.jpg",
                "A device formerly used for driving machinery, consisting of a large wheel with steps fitted into its inner surface. It was turned by the weight of people or animals treading the steps."));

        items.add(new Item("Abdominal exercise machine",
                "https://www.verywellfit.com/thmb/6tmWR6jgMRTJ_EoJsV3oTqQ0Pzo=/1500x1000/filters:no_upscale():max_bytes(150000):strip_icc()/Web_1500-VFW_xmark_fitness_adjustable_decline_ab_workout_bench_nick_kova_00027-6fa082f9cdc144a4acfc813669fd8e60.jpg",
                "The structure of the abdominal exercise machine includes: a seat, a frame, a roller, a spring, a zipper, a bicycle pedal (available in some abs exercise machines with functions such as ellipticals."));

        items.add(new Item("Chest exercise machine",
                "https://i.pinimg.com/originals/a8/c2/8b/a8c28b2b9c30de2e9f46ff1d9152bb1f.jpg",
                "The chest press machine is the most sought-after machine in every gym because gymers have a variety of exercises. In the process of training with the press, whether with dumbbells or electric pull exercises, they work on different muscle groups, not just the chest."));

        items.add(new Item("Leg exercise machine",
                "https://cdn.fitnessexpostores.com/wp-content/uploads/2020/06/best-machines-for-leg-workouts-Fitness-Expo-e1620383082990.jpg",
                "The leg machine is one of the most popular exercise machines today. It has many types and, as the name suggests, mainly affects the leg muscles, helping the practitioner have a healthy, toned, aesthetic foot. Those who are used to bodybuilding, frequenting the gym are probably not unfamiliar with this equipment."));

        items.add(new Item("Weight training gym",
                "https://goodfit.vn/wp-content/uploads/2020/11/4-phuong-phap-luyen-tap-tang-co-1024x683.jpg",
                "Weight training is a strength training exercise that increases bone density and reduces the risk of fractures and fractures in the elderly. In the era of technology development, many people have the habit of sitting and working for hours in front of the computer"));

    }

    private List<WorkoutRow> prepareData() {
        Map<String, List<WorkoutBean>> workoutsMap = FxpApp.premiumWorkoutsMap;

        List<WorkoutRow> workoutRows = new ArrayList<WorkoutRow>();
        for (String workoutName : workoutsMap.keySet()) {
            WorkoutRow workoutRow = new WorkoutRow(workoutName, R.drawable.premium_workout_default_icon);

            String purchaseId = workoutsMap.get(workoutName).get(0).getPurchaseId();

            workoutRow.setPurchaseId(purchaseId);
            workoutRows.add(workoutRow);
        }

        return workoutRows;
    }

    @Override
    public void onResume() {
        super.onResume();

        mTitle.setTypeface(FxpApp.helveticaNeue);
    }
}
