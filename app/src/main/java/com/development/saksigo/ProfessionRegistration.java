package com.development.saksigo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.development.saksigo.ProfileFeature.RegistrationFragment;

public class ProfessionRegistration extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession_registration);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        RegistrationFragment registrationFragment = new RegistrationFragment();
        fragmentTransaction.replace(R.id.containerProfessionRegistration, registrationFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();



    }


}