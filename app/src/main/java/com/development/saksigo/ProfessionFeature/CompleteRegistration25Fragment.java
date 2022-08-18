package com.development.saksigo.ProfessionFeature;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.development.saksigo.LoadingDialog;
import com.development.saksigo.LoginActivity;
import com.development.saksigo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class CompleteRegistration25Fragment extends Fragment {

    public String stringAboutYou="", stringVideoLink, stringLegalServices, stringParseIntLegalServicesType="";
    EditText editTextAboutYou, editTextVideoLink;
    Button buttonSaveContinue;
    Spinner spinnerLegalServices;
    LoadingDialog loadingDialog;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    int intLegalServicesType=0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_complete_registration_25_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://saksigo-30792-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("KeyPartner");

        loadingDialog = new LoadingDialog(getActivity());

        editTextAboutYou = (EditText) root.findViewById(R.id.editText_aboutYouProfession);
        editTextVideoLink = (EditText) root.findViewById(R.id.editText_videoLinkProfession);
        spinnerLegalServices = (Spinner) root.findViewById(R.id.spinner_LegalServices);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.id_advocateType));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLegalServices.setAdapter(arrayAdapter);


        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        CompleteRegistration50Fragment completeRegistration50Fragment = new CompleteRegistration50Fragment();

        getUserInfo();

        buttonSaveContinue = (Button) root.findViewById(R.id.button_save25);
        buttonSaveContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intLegalServicesType = spinnerLegalServices.getSelectedItemPosition();
                stringAboutYou = editTextAboutYou.getText().toString();
                stringVideoLink = editTextVideoLink.getText().toString();
                stringLegalServices = spinnerLegalServices.getSelectedItem().toString();

                if (stringLegalServices.equals("Select your work specialty..")){
                    Toast.makeText(getActivity(), "Legal Services type is not selected yet!", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (stringAboutYou.isEmpty()){
                    editTextAboutYou.setError("About You field is still empty!");
                    editTextAboutYou.requestFocus();
                    return;
                }else if (stringAboutYou.length() < 50) {
                    editTextAboutYou.setError("You must write a minimum of 50 characters.");
                    editTextAboutYou.requestFocus();
                    return;
                }
                loadingDialog.startLoadingDialog();

                uploadData();

                fragmentTransaction.replace(R.id.containerCompleteRegistration, completeRegistration50Fragment);
                fragmentTransaction.addToBackStack("professionRegistration");
                fragmentTransaction.commit();
                loadingDialog.dismissDialog();
            }
        });

        return root;
    }

    private void getUserInfo() {

        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){

                    if(dataSnapshot.hasChild("legalServicesType")){
                        stringParseIntLegalServicesType = dataSnapshot.child("legalServicesType").getValue().toString();
                        intLegalServicesType = Integer.parseInt(stringParseIntLegalServicesType);
                    }

                    if(dataSnapshot.hasChild("aboutYou")) {
                        stringAboutYou = dataSnapshot.child("aboutYou").getValue().toString();
                    }

                    if(dataSnapshot.hasChild("videoLink")) stringVideoLink = dataSnapshot.child("videoLink").getValue().toString();

                    spinnerLegalServices.setSelection(intLegalServicesType);
                    editTextAboutYou.setText(stringAboutYou);
                    editTextVideoLink.setText(stringVideoLink);

                    if(!stringAboutYou.isEmpty() && !stringParseIntLegalServicesType.isEmpty()){
                        //Textview settings
                        ProfessionCompleteRegistrationFragment.textViewProfile.setTypeface(ProfessionCompleteRegistrationFragment.textViewProfile.getTypeface(), Typeface.NORMAL);
                        ProfessionCompleteRegistrationFragment.textViewProfile.setTextColor(Color.parseColor("#FFA806"));
                        ProfessionCompleteRegistrationFragment.imageViewCheckProfile.setColorFilter(getContext().getResources().getColor(R.color.yellow));

                        ProfessionCompleteRegistrationFragment.textViewProfileAccording.setTextColor(Color.parseColor("#FFA806"));
                        ProfessionCompleteRegistrationFragment.imageViewCheckProfileAccording.setColorFilter(getContext().getResources().getColor(R.color.yellow));
                        ProfessionCompleteRegistrationFragment.textViewProfileAccording.setTypeface(ProfessionCompleteRegistrationFragment.textViewProfileAccording.getTypeface(), Typeface.BOLD_ITALIC);

                        //Progress bar settings
                        ProfessionCompleteRegistrationFragment.progressBarProfessionRegistration.getProgressDrawable().setColorFilter(Color.parseColor("#FFA806"), PorterDuff.Mode.SRC_IN);
                        ProfessionCompleteRegistrationFragment.progressBarProfessionRegistration.setProgress(50);
                        ProfessionCompleteRegistrationFragment.textViewPercent.setText("50%");
                    }else{

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void uploadData() {

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("legalServicesType", intLegalServicesType);
        userMap.put("aboutYou", stringAboutYou);
        userMap.put("videoLink", stringVideoLink);

        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);

    }
}
