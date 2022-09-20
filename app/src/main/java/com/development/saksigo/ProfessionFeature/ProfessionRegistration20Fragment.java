package com.development.saksigo.ProfessionFeature;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.development.saksigo.R;

public class ProfessionRegistration20Fragment extends Fragment {

    public static ArrayAdapter arrayAdapter;
    Spinner spinnerProfessionCategory;

    Button buttonSaveContinue0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_registration_20_fragment, container, false);

        spinnerProfessionCategory = root.findViewById(R.id.spinner_Category);
        buttonSaveContinue0 = root.findViewById(R.id.button_saveProfession0);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfessionCategory.setAdapter(arrayAdapter);


        buttonSaveContinue0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                ProfessionRegistration20Fragment professionRegistration20Fragment = new ProfessionRegistration20Fragment();
                fragmentTransaction.replace(R.id.containerServiceRegistration, professionRegistration20Fragment);
                fragmentTransaction.addToBackStack("professionRegistration");
                fragmentTransaction.commit();


            }
        });


        return root;
    }
}
