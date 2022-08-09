package com.development.saksigo.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.development.saksigo.Adapter.SliderAdapterHowTo;
import com.development.saksigo.ProfessionRegistration;
import com.development.saksigo.R;
import com.google.android.material.tabs.TabLayout;

public class ProfessionFragment extends Fragment {

    private ViewPager viewPager;

    private SliderAdapterHowTo sliderAdapter;

    TabLayout tabLayout;
    Button btnContinue;
    int pageNumber = 0;
    Context context;

    public ProfessionFragment() {


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_fragment, container, false);

        context = container.getContext();

        viewPager = (ViewPager) root.findViewById(R.id.viewPagerHowTo);

        sliderAdapter = new SliderAdapterHowTo(context);

        viewPager.setAdapter(sliderAdapter);

        tabLayout = (TabLayout) root.findViewById(R.id.tabLayoutHowTo);
        tabLayout.setupWithViewPager(viewPager, true);

        btnContinue = root.findViewById(R.id.button_continueHowTo);

        Intent intent = new Intent(context, ProfessionRegistration.class);


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
                    context.startActivity(intent);

                }


            }
        });

        return root;
    }
}
