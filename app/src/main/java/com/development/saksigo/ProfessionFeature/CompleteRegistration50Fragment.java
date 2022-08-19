package com.development.saksigo.ProfessionFeature;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.development.saksigo.DatabaseModel.NationalId;
import com.development.saksigo.DatabaseModel.Users;
import com.development.saksigo.PhotoProfileActivity;
import com.development.saksigo.R;
import com.development.saksigo.RegisterActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class CompleteRegistration50Fragment extends Fragment {


    Button buttonSaveAndContinue50, buttonSelfieWithId, buttonPhotoId;
    Spinner spinnerGender;
    EditText editTextID, editTextFirstN, editTextLastN, editTextAddress, editTextPostal, editTextCAddress, editTextCPostal;
    String stringGender, stringID, stringFirstN, stringLastN, stringAddress, stringPostal,
            stringSelfieWithId, stringPhotoId, stringMatchesId, stringCAddress, stringCPostal, stringSpinnerGender;
    int intSpinnerGender;
    boolean booleanMatchesId, checkPhotoType, checkNationalIdPhoto;
    ImageView imageViewSelfieWithId, imageViewIdPhoto;
    CheckBox checkBoxMatchesId;
    Uri uriSelfieWithId = Uri.parse(""), uriPhotoId = Uri.parse("");

    private StorageTask uploadTask;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    StorageReference storageProfilePicsRef;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_complete_registration_50_fragment, container, false);

        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("gs://saksigo-30792.appspot.com/KeyPartner/NationalId");
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://saksigo-30792-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("NationalId");

        spinnerGender = root.findViewById(R.id.spinner_Gender);
        editTextID = root.findViewById(R.id.editText_nationalId);
        editTextFirstN = root.findViewById(R.id.editText_firstNameId);
        editTextLastN = root.findViewById(R.id.editText_lastNameId);
        editTextAddress = root.findViewById(R.id.editText_addressId);
        editTextPostal = root.findViewById(R.id.editText_postalId);

        buttonSelfieWithId = root.findViewById(R.id.button_selfieWithId);
        buttonPhotoId = root.findViewById(R.id.button_photoId);

        imageViewSelfieWithId = root.findViewById(R.id.imageView_selfieWithId);
        imageViewIdPhoto = root.findViewById(R.id.imageView_idPhoto);

        checkBoxMatchesId = root.findViewById(R.id.checkBox_matchedId);
        editTextCAddress = root.findViewById(R.id.editText_addressCurrent);
        editTextCPostal = root.findViewById(R.id.editText_postalCurrent);
        buttonSaveAndContinue50 = root.findViewById(R.id.button_save50);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.id_gender));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(arrayAdapter);

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        CompleteRegistration75Fragment completeRegistration75Fragment = new CompleteRegistration75Fragment();

//        getUserInfo();

        checkBoxMatchesId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    editTextCAddress.setEnabled(false);
                    editTextCPostal.setEnabled(false);

                    booleanMatchesId = true;

                    stringCAddress = editTextAddress.getText().toString();
                    stringCPostal = editTextPostal.getText().toString();


                    editTextCAddress.setBackgroundResource(R.drawable.disabled_rounded_edit_text);
                    editTextCPostal.setBackgroundResource(R.drawable.disabled_rounded_edit_text);


                }else{
                    editTextCAddress.setEnabled(true);
                    editTextCPostal.setEnabled(true);

                    booleanMatchesId = false;

                    stringCAddress = editTextCAddress.getText().toString();
                    stringCPostal = editTextCPostal.getText().toString();

                    editTextCAddress.setBackgroundResource(R.drawable.rounded_edit_text);
                    editTextCPostal.setBackgroundResource(R.drawable.rounded_edit_text);
                }
            }
        });


        buttonSelfieWithId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPhotoType = true;
                CropImage.activity().setAspectRatio(1,1).start(getContext(), CompleteRegistration50Fragment.this);
            }
        });

        buttonPhotoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPhotoType = false;
                CropImage.activity().setAspectRatio(1,1).start(getContext(), CompleteRegistration50Fragment.this);

            }
        });

        buttonSaveAndContinue50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringID = editTextID.getText().toString();
                stringFirstN = editTextFirstN.getText().toString();
                stringLastN = editTextLastN.getText().toString();
                stringAddress = editTextAddress.getText().toString();
                stringPostal = editTextPostal.getText().toString();
                stringCAddress = editTextCAddress.getText().toString();
                stringCPostal = editTextCPostal.getText().toString();
                stringGender = spinnerGender.getSelectedItem().toString();

                if (stringID.isEmpty()){
                    editTextID.setError("National ID field is still empty!");
                    editTextID.requestFocus();
                    return;
                }else if (stringID.length() < 16 || stringID.length() > 16){
                    editTextID.setError("National ID must be 16 characters.");
                    editTextID.requestFocus();
                    return;
                }else if (stringFirstN.isEmpty()){
                    editTextFirstN.setError("First Name field is still empty!");
                    editTextFirstN.requestFocus();
                    return;
                }else if (stringLastN.isEmpty()) {
                    editTextLastN.setError("Last Name field is still empty!");
                    editTextLastN.requestFocus();
                    return;
                }else if(stringGender.equals("Select your gender..")){
                    Toast.makeText(getActivity(), "Gender is not selected yet!", Toast.LENGTH_LONG).show();
                    return;
                }else if (stringAddress.isEmpty()){
                    editTextAddress.setError("Address field is still empty!");
                    editTextAddress.requestFocus();
                    return;
                }else if(stringPostal.isEmpty()){
                    editTextPostal.setError("Postal code field is still empty!");
                    editTextPostal.requestFocus();
                    return;
                }else if (stringPostal.length() < 5 || stringPostal.length() > 5){
                    editTextPostal.setError("Postal code must be 5 digits");
                    editTextPostal.requestFocus();
                    return;
                }else if (checkPostal()){
                    editTextPostal.setError("Postal code must only contain numbers.");
                    editTextPostal.requestFocus();
                    return;
                }else if(!checkNationalIdPhoto){
                    Toast.makeText(getActivity(), "National ID Photo must be uploaded!", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!checkBoxMatchesId.isChecked()){
                    if (stringCAddress.isEmpty()){
                        editTextCAddress.setError("Current Address field is still empty!");
                        editTextCAddress.requestFocus();
                        return;
                    }else if (stringCPostal.isEmpty()){
                        editTextCPostal.setError("Current Postal code field is still empty!");
                        editTextCPostal.requestFocus();
                        return;
                    }else if (stringCPostal.length() < 5 || stringCPostal.length() > 5){
                        editTextCPostal.setError("Current Postal code must be 5 digits.");
                        editTextCPostal.requestFocus();
                        return;
                    }else if (checkCPostal()){
                        editTextCPostal.setError("Current Postal code must only contain numbers.");
                        editTextCPostal.requestFocus();
                        return;
                    }
                }

                //database preparation
                stringMatchesId = String.valueOf(booleanMatchesId);

                intSpinnerGender = spinnerGender.getSelectedItemPosition();
                stringSpinnerGender = String.valueOf(intSpinnerGender);

                uploadProfileImageSelfie();
                uploadProfileImagePhoto();

                updateData();


                fragmentTransaction.replace(R.id.containerCompleteRegistration, completeRegistration75Fragment);
                fragmentTransaction.addToBackStack("professionRegistration");
                fragmentTransaction.commit();


            }
        });

        return root;
    }



//    private void getUserInfo() {
//
//        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null && checkPhotoType==true){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            uriSelfieWithId = result.getUri();

            imageViewSelfieWithId.setImageURI(uriSelfieWithId);
        }else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null && checkPhotoType==false) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            uriPhotoId = result.getUri();

            imageViewIdPhoto.setImageURI(uriPhotoId);
        }else {
            Toast.makeText(getActivity(), "Error, try again.", Toast.LENGTH_SHORT).show();

        }

        if(!uriSelfieWithId.toString().isEmpty() && !uriPhotoId.toString().isEmpty() ){
            checkNationalIdPhoto = true;
        }else{
            checkNationalIdPhoto = false;
        }
    }

    private void uploadProfileImagePhoto() {

        if (uriPhotoId != null){
            final StorageReference fileRef = storageProfilePicsRef.child(mAuth.getCurrentUser().getUid()+ ".jpg");

            uploadTask = fileRef.putFile(uriPhotoId);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }) .addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUrl = task.getResult();
                        stringPhotoId = downloadUrl.toString();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("selfieWithId", stringPhotoId);

                        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);


                    }
                }
            });
        }
        else {

            Toast.makeText(getActivity(), "Image not selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadProfileImageSelfie(){

        if (uriSelfieWithId != null){
            final StorageReference fileRef = storageProfilePicsRef.child(mAuth.getCurrentUser().getUid()+ ".jpg");

            uploadTask = fileRef.putFile(uriSelfieWithId);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }) .addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUrl = task.getResult();
                        stringSelfieWithId = downloadUrl.toString();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("selfieWithId", stringSelfieWithId);

                        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);


                    }
                }
            });
        }
        else {

            Toast.makeText(getActivity(), "Image not selected", Toast.LENGTH_SHORT).show();
        }


    }


    private void updateData() {



        NationalId nationalId = new NationalId(stringID, stringFirstN, stringLastN, stringSpinnerGender,
                stringAddress, stringPostal, stringSelfieWithId, stringPhotoId, stringMatchesId, stringCAddress, stringCPostal);

        FirebaseDatabase.getInstance("https://saksigo-30792-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("NationalId")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(nationalId);

    }

    private boolean checkPostal(){
        char a;
        boolean checkPostal = false;
        String[] wordBox = new String[15];

        for(int i=0; i<stringPostal.length(); i++){

            a = stringPostal.charAt(i);
            wordBox[i] = String.valueOf(Character.isDigit(a));
            if(wordBox[i].equals("false")) checkPostal = true;
        }

        if(checkPostal == true){
            return true;
        }else{
            return false;
        }
    }

    private boolean checkCPostal(){
        char a;
        boolean checkCPostal = false;
        String[] wordBox = new String[15];

        for(int i=0; i<stringCPostal.length(); i++){

            a = stringCPostal.charAt(i);
            wordBox[i] = String.valueOf(Character.isDigit(a));
            if(wordBox[i].equals("false")) checkCPostal = true;
        }

        if(checkCPostal == true){
            return true;
        }else{
            return false;
        }
    }
}
