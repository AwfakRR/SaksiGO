package com.development.saksigo.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.development.saksigo.ProfessionFeature.CompleteRegistration25Fragment;
import com.development.saksigo.ProfessionFeature.ProfessionCompleteRegistrationFragment;
import com.development.saksigo.R;

public class ProfileFragment extends Fragment {



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profile_fragment, container, false);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        ProfessionCompleteRegistrationFragment professionCompleteRegistrationFragment = new ProfessionCompleteRegistrationFragment();
        fragmentTransaction.replace(R.id.container_profile, professionCompleteRegistrationFragment);
        fragmentTransaction.addToBackStack("profileRegistration");
        fragmentTransaction.commit();



        return root;
    }
}
