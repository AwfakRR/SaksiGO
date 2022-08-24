package com.development.saksigo.ProfileFeature;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.development.saksigo.LoadingDialog;
import com.development.saksigo.PhotoProfileActivity;
import com.development.saksigo.R;
import com.development.saksigo.ResetPasswordActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompleteRegistration0Fragment extends Fragment {


    private DatabaseReference databaseReference;

    TextView textViewFullname;
    FirebaseAuth mAuth;
    Spinner spinnerProvince;
    EditText editTextDate, editTextEmail, editTextPhone;
    String stringSpinner, stringDate, stringEmail, stringPhone, stringParseIntProvince, stringImage;

    Button buttonSave, buttonResetPassword;
    CircleImageView circleImageViewProfilePic;
    int selectedYear, minimalYear, intProvince=0;
    LoadingDialog loadingDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profile_complete_registration_0_fragment, container, false);

        ProfileCompleteRegistrationFragment p = new ProfileCompleteRegistrationFragment();

        p.textViewProfile.setTypeface(p.textViewProfile.getTypeface(), Typeface.BOLD_ITALIC);
        p.textViewProfileAccording.setTypeface(null, Typeface.NORMAL);
        p.textViewNationalId.setTypeface(null, Typeface.NORMAL);
        p.textViewBankAccount.setTypeface(null, Typeface.NORMAL);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://saksigo-30792-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("KeyPartner");

        loadingDialog = new LoadingDialog(getActivity());

        Date d = new Date();
        minimalYear = d.getYear()-18+1900;

        circleImageViewProfilePic = root.findViewById(R.id.circleImageView_profilePhoto);
        textViewFullname = root.findViewById(R.id.textView_fullnameProfession);
        spinnerProvince = root.findViewById(R.id.spinner_Province);
        editTextEmail = root.findViewById(R.id.editText_emailProfession);
        editTextPhone = root.findViewById(R.id.editText_phoneProfession);
        buttonResetPassword = root.findViewById(R.id.button_resetPasswordProfession);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.id_province));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProvince.setAdapter(arrayAdapter);

        editTextDate = root.findViewById(R.id.editText_DOB);
        buttonSave = root.findViewById(R.id.button_save0);
        editTextDate.setKeyListener(null);

        getUserInfo();

        stringEmail = editTextEmail.getText().toString();
        stringPhone = editTextPhone.getText().toString();
        stringDate = editTextDate.getText().toString();
        stringSpinner = spinnerProvince.getSelectedItem().toString();
        intProvince = spinnerProvince.getSelectedItemPosition();







        Intent intent = new Intent(container.getContext(), PhotoProfileActivity.class);
        Intent intentResetPassword = new Intent(container.getContext(), ResetPasswordActivity.class);


        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentResetPassword);
            }
        });

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                editTextDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                selectedYear = year;

                            }
                        },  year, month, day);

                datePickerDialog.show();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringEmail = editTextEmail.getText().toString();
                stringPhone = editTextPhone.getText().toString();
                stringDate = editTextDate.getText().toString();
                stringSpinner = spinnerProvince.getSelectedItem().toString();
                intProvince = spinnerProvince.getSelectedItemPosition();

                if(stringEmail.isEmpty()){
                    editTextEmail.setError("Email field is still empty!");
                    editTextEmail.requestFocus();
                    return;
                }else if(checkEmail()){
                    editTextEmail.setError("Email must contain ‘@’ and be placed properly.");
                    editTextEmail.requestFocus();
                    return;
                }else if(!stringEmail.contains(".") || stringEmail.contains("@.") || stringEmail.contains(".@")){
                    editTextEmail.setError("Email must contain ‘.’ and not next to the ‘@’ symbol.");
                    editTextEmail.requestFocus();
                    return;
                }else if(stringPhone.isEmpty()){
                    editTextPhone.setError("Phone number field is still empty!");
                    editTextPhone.requestFocus();
                    return;
                }else if(stringPhone.length() < 10 || stringPhone.length() > 12){
                    editTextPhone.setError("Phone number must be between 10 and 12 digits.");
                    editTextPhone.requestFocus();
                    return;
                }else if(checkPhone()){
                    editTextPhone.setError("Phone number must only contain numbers.");
                    editTextPhone.requestFocus();
                    return;
                }else if (stringDate.isEmpty()){
                    editTextDate.setError("Date of birth is not selected yet!");
                    editTextDate.requestFocus();
                    return;
                }else if (checkTime()){
                    editTextDate.setError("You must be at least 18 years old to use SaksiGO's services.");
                    editTextDate.requestFocus();
                    return;
                }else if(stringSpinner.equals("Select your province..")){
                    editTextDate.clearFocus();
                    editTextDate.setError(null);
                    Toast.makeText(getActivity(), "Province is not selected yet!", Toast.LENGTH_LONG).show();
                    return;
                }


                editTextDate.clearFocus();
                editTextDate.setError(null);
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                CompleteRegistration25Fragment completeRegistration25Fragment = new CompleteRegistration25Fragment();

                loadingDialog.startLoadingDialog();

                updateProfile0();

                fragmentTransaction.replace(R.id.containerCompleteRegistration, completeRegistration25Fragment);
                fragmentTransaction.addToBackStack("profileRegistration");
                fragmentTransaction.commit();

                loadingDialog.dismissDialog();
            }
        });

        circleImageViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        return root;
    }

    private void updateProfile0() {

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("email", stringEmail);
        userMap.put("phoneNumber", stringPhone);
        userMap.put("dateOfBirth", stringDate);
        userMap.put("province", intProvince);

        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);

    }

    private void getUserInfo() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                    String name = dataSnapshot.child("fullname").getValue().toString();
                    stringEmail = dataSnapshot.child("email").getValue().toString();
                    stringPhone = dataSnapshot.child("phoneNumber").getValue().toString();
                    if(dataSnapshot.hasChild("dateOfBirth")) stringDate = dataSnapshot.child("dateOfBirth").getValue().toString();
                    if(dataSnapshot.hasChild("province"))  stringParseIntProvince = dataSnapshot.child("province").getValue().toString();

                    textViewFullname.setText(name);
                    editTextEmail.setText(stringEmail);
                    editTextPhone.setText(stringPhone);
                    editTextDate.setText(stringDate);
                    if(dataSnapshot.hasChild("province"))  intProvince = Integer.valueOf(stringParseIntProvince);
                    spinnerProvince.setSelection(intProvince);

                    if(dataSnapshot.hasChild("image")){
                        String image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(circleImageViewProfilePic);
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private boolean checkEmail() {

        if(!stringEmail.contains("@") || stringEmail.startsWith("@") || stringEmail.endsWith("@")){
            return true;
        }else{
            return false;
        }

    }
    private boolean checkPhone(){
        char a;
        boolean checkPhone = false;
        String[] wordBox = new String[15];

        for(int i=0; i<stringPhone.length(); i++){

            a = stringPhone.charAt(i);
            wordBox[i] = String.valueOf(Character.isDigit(a));
            if(wordBox[i].equals("false")) checkPhone = true;
        }

        if(checkPhone == true){
            return true;
        }else{
            return false;
        }
    }
    private boolean checkTime() {
        Boolean checkTime = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date strDate = null;
        try {
            strDate = sdf.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (minimalYear-selectedYear<0) {
            checkTime = true;
        }
        return checkTime;
    }
}
