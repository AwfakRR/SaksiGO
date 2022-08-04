package com.development.saksigo;

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

import java.util.Locale;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){

        this.context=context;
    }

    public int[] slide_illus = {
            R.drawable.ic_undraw_articles,
            R.drawable.ic_undraw_projections,
            R.drawable.ic_undraw_reminder
    };

    public String[] slide_heading = {
            "Finding Client",
            "File Management",
            "Workflow Optimized"
    };

    public String[] slide_description = {
            "Here at SaksiGO, finding and meeting client made simpler with no physical place limitation, communication made easy, fast, and efficient.",
            "File management in SaksiGO made your workflow even faster and easier, supported by robust management system.",
            "With SaksiGO, your workflow would improved by using the tools provided such as communication platform, file management, and more."
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
