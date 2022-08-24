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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.development.saksigo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfessionCompleteRegistrationFragment extends Fragment {

    public static ProgressBar progressBarProfessionRegistration;
    public static TextView textViewProfile, textViewProfileAccording, textViewNationalId, textViewBankAccount, textViewPercent;
    public static ImageView imageViewCheckProfile, imageViewCheckProfileAccording, imageViewCheckNationalId, imageViewCheckBankAccount;

    int checkProfile=0;
    String stringCheckProfile;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference0_25, databaseReference50, databaseReference75, databaseReference;

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
        CompleteRegistration25Fragment completeRegistration25Fragment = new CompleteRegistration25Fragment();
        CompleteRegistration50Fragment completeRegistration50Fragment = new CompleteRegistration50Fragment();
        CompleteRegistration75Fragment completeRegistration75Fragment = new CompleteRegistration75Fragment();
        fragmentTransaction.replace(R.id.containerCompleteRegistration, completeRegistration0Fragment);
        fragmentTransaction.addToBackStack("profileRegistration");
        fragmentTransaction.commit();

        mAuth = FirebaseAuth.getInstance();
        databaseReference0_25 = FirebaseDatabase.getInstance("https://saksigo-30792-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("KeyPartner");
        databaseReference50 = FirebaseDatabase.getInstance("https://saksigo-30792-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("NationalId");
        databaseReference75 = FirebaseDatabase.getInstance("https://saksigo-30792-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("BankAccount");
        databaseReference = FirebaseDatabase.getInstance("https://saksigo-30792-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        stringCheckProfile = String.valueOf(checkProfile);
        //Progress bar settings
        progressBarProfessionRegistration.getProgressDrawable().setColorFilter(Color.parseColor("#FFA806"), PorterDuff.Mode.SRC_IN);
        progressBarProfessionRegistration.setProgress(checkProfile);
        textViewPercent.setText(stringCheckProfile+"%");

        updateProgress();

        textViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerCompleteRegistration, completeRegistration0Fragment);
                fragmentTransaction.addToBackStack("profileRegistration");
                fragmentTransaction.commit();
            }
        });

        textViewProfileAccording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerCompleteRegistration, completeRegistration25Fragment);
                fragmentTransaction.addToBackStack("profileRegistration");
                fragmentTransaction.commit();
            }
        });

        textViewNationalId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerCompleteRegistration, completeRegistration50Fragment);
                fragmentTransaction.addToBackStack("profileRegistration");
                fragmentTransaction.commit();
            }
        });

        textViewBankAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.containerCompleteRegistration, completeRegistration75Fragment);
                fragmentTransaction.addToBackStack("profileRegistration");
                fragmentTransaction.commit();
            }
        });

        return root;
    }

    private void updateProgress() {



        databaseReference0_25.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                //Registration 0
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){

                    //if berhasil
                    if(dataSnapshot.hasChild("userId") && dataSnapshot.hasChild("email") && dataSnapshot.hasChild("fullname")
                            && dataSnapshot.hasChild("image") && dataSnapshot.hasChild("dateOfBirth") && dataSnapshot.hasChild("phoneNumber")
                            && dataSnapshot.hasChild("province")) {

                        //Set Progress
                        //Profile
                        textViewProfile.setTextColor(Color.parseColor("#FFA806"));
                        imageViewCheckProfile.setColorFilter(getContext().getResources().getColor(R.color.yellow));

                        checkProfile = checkProfile + 15;

                        stringCheckProfile = String.valueOf(checkProfile);

                        //Progress bar settings
                        progressBarProfessionRegistration.getProgressDrawable().setColorFilter(Color.parseColor("#FFA806"), PorterDuff.Mode.SRC_IN);
                        progressBarProfessionRegistration.setProgress(checkProfile);
                        textViewPercent.setText(stringCheckProfile+"%");


                    }

                    if(dataSnapshot.hasChild("aboutYou") && dataSnapshot.hasChild("legalServicesType")){

                        //Set Progress

                        //Profile According
                        textViewProfileAccording.setTextColor(Color.parseColor("#FFA806"));
                        imageViewCheckProfileAccording.setColorFilter(getContext().getResources().getColor(R.color.yellow));

                        checkProfile = checkProfile + 10;

                        stringCheckProfile = String.valueOf(checkProfile);

                        //Progress bar settings
                        progressBarProfessionRegistration.getProgressDrawable().setColorFilter(Color.parseColor("#FFA806"), PorterDuff.Mode.SRC_IN);
                        progressBarProfessionRegistration.setProgress(checkProfile);
                        textViewPercent.setText(stringCheckProfile+"%");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference50.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                //Registration 0
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){

                    if(dataSnapshot.hasChild("id") && dataSnapshot.hasChild("firstname") && dataSnapshot.hasChild("lastname")
                            && dataSnapshot.hasChild("gender") && dataSnapshot.hasChild("address") && dataSnapshot.hasChild("postal")
                            && dataSnapshot.hasChild("photoId") && dataSnapshot.hasChild("selfieWithId")){

                        //Set Progress

                        //NationalId
                        textViewNationalId.setTextColor(Color.parseColor("#FFA806"));
                        imageViewCheckNationalId.setColorFilter(getContext().getResources().getColor(R.color.yellow));

                        checkProfile = checkProfile+25;

                        stringCheckProfile = String.valueOf(checkProfile);

                        //Progress bar settings
                        progressBarProfessionRegistration.getProgressDrawable().setColorFilter(Color.parseColor("#FFA806"), PorterDuff.Mode.SRC_IN);
                        progressBarProfessionRegistration.setProgress(checkProfile);
                        textViewPercent.setText(stringCheckProfile+"%");

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference75.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Registration 75
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {

                    if(dataSnapshot.hasChild("accountNumber") && dataSnapshot.hasChild("accountBookPhoto")
                            && dataSnapshot.hasChild("bankName")){


                        //Set Progress

                        //Bank Account
                        textViewBankAccount.setTextColor(Color.parseColor("#FFA806"));
                        imageViewCheckBankAccount.setColorFilter(getContext().getResources().getColor(R.color.yellow));


                        checkProfile = checkProfile + 50;

                        stringCheckProfile = String.valueOf(checkProfile);

                        //Progress bar settings
                        progressBarProfessionRegistration.getProgressDrawable().setColorFilter(Color.parseColor("#FFA806"), PorterDuff.Mode.SRC_IN);
                        progressBarProfessionRegistration.setProgress(checkProfile);
                        textViewPercent.setText(stringCheckProfile+"%");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });

    }

}
