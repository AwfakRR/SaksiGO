package com.development.saksigo.Adapter;

import static android.provider.Settings.System.getString;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.development.saksigo.R;

import java.util.Locale;

public class SliderAdapterHowTo extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;




    public SliderAdapterHowTo(Context context){

        this.context=context;
    }

    public int[] slide_illus = {
            R.drawable.ic_undraw_personal_file,
            R.drawable.ic_undraw_certification,
            R.drawable.ic_undraw_exciting_news
    };

    public String[] slide_heading = {
            "Registration",
            "Validation",
            "Cases Page"
    };

    public String[] slide_description = {
            "Key partners at SaksiGO need to register their firm and their law case specialty, to allow the customer to see their intricate trades.",
            "After successful registration, the validation process will begin on our side immediately. Verified accounts may access all of SaksiGO’s features.",
            "Before key partners can start helping their clients on the platform, they are needed to set their cases first. Our robust communication and management system will allow key partners to help their clients efficiently."
    };

    @Override
    public int getCount() {
        return slide_description.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.imageView_illus);
        TextView slideHeading = (TextView) view.findViewById(R.id.textView_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.textView_description);

        slideImageView.setImageResource(slide_illus[position]);
        slideHeading.setText(slide_heading[position]);
        slideDescription.setText(slide_description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
