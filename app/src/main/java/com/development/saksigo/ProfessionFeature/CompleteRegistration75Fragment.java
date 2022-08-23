package com.development.saksigo.ProfessionFeature;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.development.saksigo.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.net.URI;
import java.util.HashMap;

public class CompleteRegistration75Fragment extends Fragment {

    Spinner spinnerBank;
    EditText editBankNo;
    String stringBank, stringBankNumber, stringAccountBook;
    Button buttonSave75, buttonUploadAccountBook;
    DatabaseReference databaseReference;
    Uri uriAccountBook;

    ImageView imageViewAccountBook;
    FirebaseAuth mAuth;

    StorageReference storageReferenceAccountBook;
    StorageTask storageTaskAccountBook;

    int integerBank;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_complete_registration_75_fragment, container, false);

        spinnerBank = root.findViewById(R.id.spinner_bank);
        editBankNo = root.findViewById(R.id.editText_bankNo);
        imageViewAccountBook = root.findViewById(R.id.imageView_accountBook);
        buttonUploadAccountBook = root.findViewById(R.id.button_uploadAccountBook);
        buttonSave75 = root.findViewById(R.id.button_save75);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://saksigo-30792-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("BankAccount");
        storageReferenceAccountBook = FirebaseStorage.getInstance().getReference().child("gs://saksigo-30792.appspot.com/KeyPartner/AccountBook");

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        CompleteRegistration100Fragment completeRegistration100Fragment = new CompleteRegistration100Fragment();

        getUserInfo();

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.id_bank));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBank.setAdapter(arrayAdapter);

        buttonUploadAccountBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setAspectRatio(1,1).start(getContext(), CompleteRegistration75Fragment.this);
            }
        });


        buttonSave75.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringBank = spinnerBank.getSelectedItem().toString();
                integerBank = spinnerBank.getSelectedItemPosition();
                stringBankNumber = editBankNo.getText().toString();

                if (stringBank.equals("Select your bank..")){
                    Toast.makeText(getActivity(), "Bank is not selected yet!", Toast.LENGTH_LONG).show();
                    return;
                }else if (stringBankNumber.isEmpty()){
                    editBankNo.setError("Bank Account number is still empty!");
                    editBankNo.requestFocus();
                    return;
                }

                fragmentTransaction.replace(R.id.containerCompleteRegistration, completeRegistration100Fragment);
                fragmentTransaction.addToBackStack("professionRegistration");
                fragmentTransaction.commit();

                updateData();

                uploadAccountBook();

            }

        }

        );



        return root;
    }

    private void uploadAccountBook() {

        if (uriAccountBook != null){
            final StorageReference fileRefSelfie = storageReferenceAccountBook.child(mAuth.getCurrentUser().getUid()+ "account_book.jpg");

            storageTaskAccountBook = fileRefSelfie.putFile(uriAccountBook);
            storageTaskAccountBook.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task taskSelfie) throws Exception {

                    if (!taskSelfie.isSuccessful()){
                        throw taskSelfie.getException();
                    }
                    return fileRefSelfie.getDownloadUrl();
                }
            }) .addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> taskSelfie) {
                    if (taskSelfie.isSuccessful()){
                        Uri downloadUrlSelfie = taskSelfie.getResult();
                        stringAccountBook = downloadUrlSelfie.toString();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("accountBookPhoto", stringAccountBook);


                        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);


                    }
                }
            });
        }
        else {

//            Toast.makeText(getActivity(), "Image not selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            uriAccountBook = result.getUri();

            imageViewAccountBook.setImageURI(uriAccountBook);
        }else {
            Toast.makeText(getActivity(), "Error, try again.", Toast.LENGTH_SHORT).show();

        }
    }

    private void getUserInfo() {

        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {

                    if(dataSnapshot.hasChild("bankName")){
                        stringBank = dataSnapshot.child("bankName").getValue().toString();
                        integerBank = Integer.parseInt(stringBank);
                        spinnerBank.setSelection(integerBank);
                    }

                    if(dataSnapshot.hasChild("accountNumber")) stringBankNumber = dataSnapshot.child("accountNumber").getValue().toString();

                    if(dataSnapshot.hasChild("accountBookPhoto")) {
                        stringAccountBook = dataSnapshot.child("accountBookPhoto").getValue().toString();
                        Picasso.get().load(stringAccountBook).into(imageViewAccountBook);
                    }

                    editBankNo.setText(stringBankNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void updateData(){



        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("bankName", integerBank);
        userMap.put("accountNumber", stringBankNumber);

        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
    }
}
