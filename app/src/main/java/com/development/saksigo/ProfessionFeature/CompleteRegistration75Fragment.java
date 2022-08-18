package com.development.saksigo.ProfessionFeature;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.development.saksigo.R;

public class CompleteRegistration75Fragment extends Fragment {

    Button buttonSave75;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_complete_registration_75_fragment, container, false);

        buttonSave75 = root.findViewById(R.id.button_photoId);

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        CompleteRegistration100Fragment completeRegistration100Fragment = new CompleteRegistration100Fragment();

        buttonSave75.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentTransaction.replace(R.id.containerCompleteRegistration, completeRegistration100Fragment);
                fragmentTransaction.addToBackStack("professionRegistration");
                fragmentTransaction.commit();

            }
        });


        return root;
    }
}
