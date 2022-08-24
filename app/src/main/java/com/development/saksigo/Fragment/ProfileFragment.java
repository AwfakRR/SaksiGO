package com.development.saksigo.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.development.saksigo.ProfileFeature.ProfileCompleteRegistrationFragment;
import com.development.saksigo.R;

public class ProfileFragment extends Fragment {



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profile_fragment, container, false);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        ProfileCompleteRegistrationFragment profileCompleteRegistrationFragment = new ProfileCompleteRegistrationFragment();
        fragmentTransaction.replace(R.id.container_profile, profileCompleteRegistrationFragment);
        fragmentTransaction.addToBackStack("profileRegistration");
        fragmentTransaction.commit();



        return root;
    }
}
