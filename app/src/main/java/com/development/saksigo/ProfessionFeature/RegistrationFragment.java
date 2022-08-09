package com.development.saksigo.ProfessionFeature;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.development.saksigo.R;

public class RegistrationFragment extends Fragment {

    public RegistrationFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_registration_fragment, container, false);

        return root;
    }
}
