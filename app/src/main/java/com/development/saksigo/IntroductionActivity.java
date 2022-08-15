package com.development.saksigo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;


public class IntroductionActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private SliderAdapter sliderAdapter;

    TabLayout tabLayout;
    Button btnContinue;
    int pageNumber = 0;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        getSupportActionBar().hide();

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        sliderAdapter = new SliderAdapter(this);

        viewPager.setAdapter(sliderAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, true);

        btnContinue = findViewById(R.id.button_continue);

        intent = new Intent(this, LandingActivity.class);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pageNumber = viewPager.getCurrentItem();
                if(pageNumber<2){
                    pageNumber++;
                    viewPager.setCurrentItem(pageNumber);
                }else{
                    pageNumber = 0;
                }


                if(pageNumber == 2){
                    btnContinue.setText("CONFIRM");
                }else if(pageNumber < 2 && viewPager.getCurrentItem() < 2){
                    btnContinue.setText("CONTINUE");
                }else if(btnContinue.getText()=="CONFIRM"){

                   startActivity(intent);
                   finish();
                }


            }
        });
    }

}