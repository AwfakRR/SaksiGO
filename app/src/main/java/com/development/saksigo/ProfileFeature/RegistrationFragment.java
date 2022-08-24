package com.development.saksigo.ProfileFeature;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.development.saksigo.R;

public class RegistrationFragment extends Fragment {

    ImageView imageViewLegalServices, imageViewExpertWitness;

    public RegistrationFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_registration_fragment, container, false);

        imageViewLegalServices = (ImageView) root.findViewById(R.id.imageView_legalServices);
        imageViewExpertWitness = (ImageView) root.findViewById(R.id.imageView_expertWitness);

        imageViewLegalServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ProfessionCompleteRegistrationFragment professionCompleteRegistrationFragment= new ProfessionCompleteRegistrationFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.containerProfessionRegistration, professionCompleteRegistrationFragment);
//                fragmentTransaction.addToBackStack("professionRegistration");
//                fragmentTransaction.commit();
            }
        });

        imageViewExpertWitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return root;
    }
}
