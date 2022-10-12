package com.mteam.nutri.ui.screen;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mteam.nutri.R;


public class PatientPhotoDetailFragment extends AbstractActivity {


    ImageView patientImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        patientImage = (ImageView) findViewById(R.id.patientImage);
        String itemTitle = getIntent().getStringExtra("PLACE_TITLE");
        Glide.with(this).load(itemTitle).into(patientImage);
    }
}
