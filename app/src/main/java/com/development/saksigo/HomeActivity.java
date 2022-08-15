package com.development.saksigo;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.development.saksigo.Adapter.DrawerAdapter;
import com.development.saksigo.Adapter.DrawerItem;
import com.development.saksigo.Adapter.SimpleItem;
import com.development.saksigo.Adapter.SpaceItem;
import com.development.saksigo.Fragment.BalanceFragment;
import com.development.saksigo.Fragment.ChatFragment;
import com.development.saksigo.Fragment.HomeFragment;
import com.development.saksigo.Fragment.LawProcessFragment;
import com.development.saksigo.Fragment.ProfessionFragment;
import com.development.saksigo.Fragment.ProfileFragment;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.SlidingRootNavLayout;

import java.util.Arrays;

public class HomeActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener{

    private static final int POS_CLOSE = 0;
    private static final int POS_HOME = 1;
    private static final int POS_PROFESSION = 2;
    private static final int POS_LAW_PROCESS = 3;
    private static final int POS_PROFILE = 4;
    private static final int POS_CHAT = 5;
    private static final int POS_BALANCE = 6;
    private static final int POS_LOGOUT = 8;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;

    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        intent = new Intent(this, MainActivity.class);
        sharedPreferences = this.getSharedPreferences("LoginActivity", MODE_PRIVATE);
        editor = sharedPreferences.edit();



        //Actionbar style
//        getSupportActionBar().hide();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_CLOSE),
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_PROFESSION),
                createItemFor(POS_LAW_PROCESS),
                createItemFor(POS_PROFILE),
                createItemFor(POS_CHAT),
                createItemFor(POS_BALANCE),
                new SpaceItem(150),
                createItemFor(POS_LOGOUT)

        ));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_HOME);
    }

    private DrawerItem createItemFor(int position){
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.white))
                .withTextTint(color(R.color.white))
                .withSelectedIconTint(color(R.color.yellow))
                .withSelectedTextTint(color(R.color.yellow));
    }

    @ColorInt
    private int color(int res){
        return ContextCompat.getColor(this, res);
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons(){
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcon);
        Drawable[] icons = new Drawable[ta.length()];
        for(int i = 0; i<ta.length(); i++){
            int id = ta.getResourceId(i, 0);
            if(id != 0){
                icons[i]=ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(position == POS_HOME){

            HomeFragment homeFragment = new HomeFragment();
            transaction.replace(R.id.container, homeFragment);
            toolbar.setTitle("Home");
        }

        else if(position == POS_PROFESSION){
            ProfessionFragment professionFragment = new ProfessionFragment();
            transaction.replace(R.id.container, professionFragment);
            toolbar.setTitle("Profession");
        }

        else if(position == POS_LAW_PROCESS){
            LawProcessFragment lawProcessFragment = new LawProcessFragment();
            transaction.replace(R.id.container, lawProcessFragment);
            toolbar.setTitle("Law Process");
        }

        else if(position == POS_PROFILE){
            ProfileFragment profileFragment = new ProfileFragment();
            transaction.replace(R.id.container, profileFragment);
            toolbar.setTitle("Profile");
        }

        else if(position == POS_CHAT){
            ChatFragment chatFragment = new ChatFragment();
            transaction.replace(R.id.container, chatFragment);
            toolbar.setTitle("Chat");
        }

        else if(position == POS_BALANCE){
            BalanceFragment balanceFragment = new BalanceFragment();
            transaction.replace(R.id.container, balanceFragment);
            toolbar.setTitle("Balance");

        }
        else if(position == POS_LOGOUT){

            editor.putString("loginAgain", "false");
            editor.commit();
            startActivity(intent);

//            finish();
        }

        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }
}