package com.development.saksigo.ProfessionFeature;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.development.saksigo.R;

public class ProfessionCompleteRegistrationFragment extends Fragment {

    public static ProgressBar progressBarProfessionRegistration;
    public static TextView textViewProfile, textViewProfileAccording, textViewNationalId, textViewBankAccount, textViewPercent;
    public static ImageView imageViewCheckProfile, imageViewCheckProfileAccording, imageViewCheckNationalId, imageViewCheckBankAccount;







    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_complete_registration_fragment, container, false);

        //Find view by Id
        textViewProfile = (TextView) root.findViewById(R.id.textView_profileProfession);
        textViewProfileAccording = (TextView) root.findViewById(R.id.textView_profileAccordingProfession);
        textViewNationalId = (TextView) root.findViewById(R.id.textView_nationalIdProfession);
        textViewBankAccount = (TextView) root.findViewById(R.id.textView_bankProfession);

        textViewPercent = (TextView) root.findViewById(R.id.textView_percentProfession);

        imageViewCheckProfile = (ImageView) root.findViewById(R.id.imageView_checkProfile);
        imageViewCheckProfileAccording = (ImageView) root.findViewById(R.id.imageView_checkProfileAccording);
        imageViewCheckNationalId= (ImageView) root.findViewById(R.id.imageView_checkNationalId);
        imageViewCheckBankAccount = (ImageView) root.findViewById(R.id.imageView_checkBank);

        progressBarProfessionRegistration = (ProgressBar) root.findViewById(R.id.progressBar_professionRegistration);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        CompleteRegistration0Fragment completeRegistration0Fragment = new CompleteRegistration0Fragment();
        fragmentTransaction.replace(R.id.containerCompleteRegistration, completeRegistration0Fragment);
        fragmentTransaction.addToBackStack("profileRegistration");
        fragmentTransaction.commit();



        return root;
    }

}
