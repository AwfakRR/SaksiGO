package com.development.saksigo.ProfileFeature;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.development.saksigo.R;

public class CompleteRegistration100Fragment extends Fragment {

    Button buttonConfirm;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profile_complete_registration_100_fragment, container, false);


        buttonConfirm = root.findViewById(R.id.button_save100);

        ProfileCompleteRegistrationFragment p = new ProfileCompleteRegistrationFragment();

        p.textViewProfile.setTypeface(null, Typeface.NORMAL);
        p.textViewProfileAccording.setTypeface(null, Typeface.NORMAL);
        p.textViewNationalId.setTypeface(null, Typeface.NORMAL);
        p.textViewBankAccount.setTypeface(p.textViewBankAccount.getTypeface(), Typeface.BOLD_ITALIC);

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        CompleteRegistration0Fragment completeRegistration0Fragment = new CompleteRegistration0Fragment();


        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentTransaction.replace(R.id.containerCompleteRegistration, completeRegistration0Fragment);
                fragmentTransaction.addToBackStack("profileRegistration");
                fragmentTransaction.commit();
            }
        });


        return root;
    }
}
