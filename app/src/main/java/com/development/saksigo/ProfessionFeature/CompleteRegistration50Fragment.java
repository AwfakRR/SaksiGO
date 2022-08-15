package com.development.saksigo.ProfessionFeature;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.development.saksigo.R;

public class CompleteRegistration50Fragment extends Fragment {

    Button buttonSaveAndContinue50;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_complete_registration_50_fragment, container, false);


//        buttonSaveAndContinue50 = root.findViewById(R.id.button_)

        return root;
    }
}
