package com.development.saksigo.ProfessionFeature;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.development.saksigo.R;

public class CompleteRegistration100Fragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_complete_registration_100_fragment, container, false);


        ProfessionCompleteRegistrationFragment p = new ProfessionCompleteRegistrationFragment();

        p.textViewProfile.setTypeface(null, Typeface.NORMAL);
        p.textViewProfileAccording.setTypeface(null, Typeface.NORMAL);
        p.textViewNationalId.setTypeface(null, Typeface.NORMAL);
        p.textViewBankAccount.setTypeface(p.textViewBankAccount.getTypeface(), Typeface.BOLD_ITALIC);


        return root;
    }
}
