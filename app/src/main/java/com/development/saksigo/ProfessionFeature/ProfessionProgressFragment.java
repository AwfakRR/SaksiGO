package com.development.saksigo.ProfessionFeature;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.development.saksigo.R;

public class ProfessionProgressFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_registration_progress_fragment, container, false);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        ProfessionRegistration20Fragment professionRegistration20Fragment = new ProfessionRegistration20Fragment();
        fragmentTransaction.replace(R.id.containerServiceRegistration, professionRegistration20Fragment);
        fragmentTransaction.addToBackStack("professionRegistration");
        fragmentTransaction.commit();

        return root;
    }

}
