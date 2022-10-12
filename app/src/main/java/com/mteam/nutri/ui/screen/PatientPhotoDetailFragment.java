package com.mteam.nutri.ui.screen;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mteam.nutri.R;


public class PatientPhotoDetailFragment extends AbstractActivity {


    ImageView patientImage;
    ImageView mBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        patientImage = (ImageView) findViewById(R.id.patientImage);
        mBtnBack = (ImageView) findViewById(R.id.btnBack);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String itemTitle = getIntent().getStringExtra("PLACE_TITLE");
        Glide.with(this).load(itemTitle).into(patientImage);
    }
}
