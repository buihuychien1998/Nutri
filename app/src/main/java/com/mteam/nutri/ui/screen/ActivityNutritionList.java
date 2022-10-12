package com.mteam.nutri.ui.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mteam.nutri.FxpApp;
import com.mteam.nutri.bean.NutritionCategoryBean;
import com.mteam.nutri.bean.NutritionRow;
import com.mteam.nutri.ui.adapter.NutritionListAdapter;
import com.mteam.nutri.ui.event.NutritionListScreenEventHandler;
import com.mteam.nutri.ui.utils.CommonUtils;
import com.mteam.nutri.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ActivityNutritionList extends AbstractFragment {

    private TextView mTitle;

    private View mBack;

    private ListView mNutritionList;

    private List<Items> listNutrition = new ArrayList<>();

    public static final String ICON_PREFIX = "icon";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nutritions_list,
                container, false);

        mTitle = (TextView) view.findViewById(R.id.title);
        mNutritionList = (ListView) view.findViewById(R.id.nutrition);
        mBack = view.findViewById(R.id.imgBack);

        initData();

        NutritionListScreenEventHandler eventHandler = new NutritionListScreenEventHandler(this);
        mBack.setOnClickListener(eventHandler);

        List<NutritionRow> nutritionRows = prepareData();

        NutritionListAdapter workoutListAdapter = new NutritionListAdapter(this.getActivity(), listNutrition);
        mNutritionList.setAdapter(workoutListAdapter);
        mNutritionList.setOnItemClickListener(eventHandler);
        eventHandler.setNutritionRows(listNutrition);

        RelativeLayout menubarLayout = (RelativeLayout) getActivity().findViewById(R.id.menubarLayout);
        menubarLayout.setVisibility(View.VISIBLE);

        return view;
    }

    private void initData() {
        listNutrition.add(new Items("Make most of your meal vegetables and fruits – ½ of your plate. Aim for color and variety, and remember that potatoes don’t count as vegetables on the Healthy Eating Plate because of their negative impact on blood sugar."));
        listNutrition.add(new Items("Go for whole grains – ¼ of your plate. Whole and intact grains—whole wheat, barley, wheat berries, quinoa, oats, brown rice, and foods made with them, such as whole wheat pasta—have a milder effect on blood sugar and insulin than white bread, white rice, and other refined grains."));
        listNutrition.add(new Items("Protein power – ¼ of your plate. Fish, poultry, beans, and nuts are all healthy, versatile protein sources—they can be mixed into salads, and pair well with vegetables on a plate. Limit red meat, and avoid processed meats such as bacon and sausage."));
        listNutrition.add(new Items("Healthy plant oils – in moderation. Choose healthy vegetable oils like olive, canola, soy, corn, sunflower, peanut, and others, and avoid partially hydrogenated oils, which contain unhealthy trans fats. Remember that low-fat does not mean “healthy.”"));
        listNutrition.add(new Items("Drink water, coffee, or tea. Skip sugary drinks, limit milk and dairy products to one to two servings per day, and limit juice to a small glass per day."));
        listNutrition.add(new Items("Stay active. The red figure running across the Healthy Eating Plate’s placemat is a reminder that staying active is also important in weight control."));
    }

    private List<NutritionRow> prepareData() {
        Map<String, NutritionCategoryBean> nutritionsMap = FxpApp.nutritionsMap;
        if (nutritionsMap == null) {
            return new ArrayList<>();
        }

        List<NutritionRow> nutritionRows = new ArrayList<>();
        for (String nutritionName : nutritionsMap.keySet()) {
            NutritionCategoryBean nutritionCategoryBean = nutritionsMap.get(nutritionName);

            assert nutritionCategoryBean != null;
            String des = nutritionCategoryBean.getDescription();

            int imageId = R.drawable.breakfast_icon;
            try {
                imageId = CommonUtils.getId(nutritionCategoryBean.getIconId() + "_" + ICON_PREFIX, R.drawable.class);
            } catch (SecurityException e) {
            } catch (IllegalArgumentException e) {
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
            }

            NutritionRow nutritionRow = new NutritionRow(nutritionName, imageId, des);
            nutritionRows.add(nutritionRow);
        }

        NutritionRow[] nutritionRowsArr = new NutritionRow[4];
        for (NutritionRow nutritionRow : nutritionRows) {
            if (nutritionRow.getName().equals("Breakfast")) {
                nutritionRowsArr[0] = nutritionRow;
            } else if (nutritionRow.getName().equals("Snack")) {
                nutritionRowsArr[1] = nutritionRow;
            } else if (nutritionRow.getName().equals("Lunch")) {
                nutritionRowsArr[2] = nutritionRow;
            } else {
                nutritionRowsArr[3] = nutritionRow;
            }
        }

        return Arrays.asList(nutritionRowsArr);
    }

    @Override
    public void onResume() {
        super.onResume();

        mTitle.setTypeface(FxpApp.helveticaNeue);
    }
}
