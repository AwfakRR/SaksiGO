package com.development.saksigo.ProfessionFeature;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.development.saksigo.IntroductionActivity;
import com.development.saksigo.PhotoProfileActivity;
import com.development.saksigo.R;
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


import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class CompleteRegistration0Fragment extends Fragment {

    private CircleImageView profileImageView;
    private DatabaseReference databaseReference;

    TextView textViewFullname;
    FirebaseAuth mAuth;

    CircleImageView circleImageViewProfilePic;
    EditText editTextEmail;
    EditText editTextPhone;
    EditText editTextPassword;
    EditText editTextDate;
    String stringPhoneUser;
    String stringEmailUser;
    String stringPasswordUser;
    Button buttonSave;





    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_complete_registration_0_fragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance("https://saksigo-30792-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("KeyPartner");
        circleImageViewProfilePic = root.findViewById(R.id.circleImageView_profilePhoto);
        textViewFullname = root.findViewById(R.id.textView_fullnameProfession);

        editTextEmail = root.findViewById(R.id.editText_emailProfession);
        editTextPhone = root.findViewById(R.id.editText_phoneProfession);
        editTextDate = root.findViewById(R.id.editText_DOB);
        editTextDate.setKeyListener(null);
        editTextPassword = root.findViewById(R.id.editText_passwordProfession);
        buttonSave = root.findViewById((R.id.button_save0));




        Intent intent = new Intent(container.getContext(), PhotoProfileActivity.class);

        getUserInfo();

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

                                editTextDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

                            }
                        },  year, month, day);

                datePickerDialog.show();
            }
        });


        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringEmailUser = editTextEmail.getText().toString();
                if(stringEmailUser.isEmpty()) {
                    editTextEmail.setError("Email field is still empty.");
                    editTextEmail.requestFocus();
                }else if(checkEmail()){
                    editTextEmail.setError("Email must contain ‘@’ and placed not at the start or at the end of the input.");
                    editTextEmail.requestFocus();
                    return;
                }else if(!stringEmailUser.contains(".") || stringEmailUser.contains("@.") || stringEmailUser.contains(".@")){
                    editTextEmail.setError("email must contain ‘.’ and not next to ‘@’ symbol.");
                    editTextEmail.requestFocus();
                    return;
                }
                stringPhoneUser = editTextPhone.getText().toString();
                if(stringPhoneUser.isEmpty()){
                    editTextPhone.setError("Phone number field is still empty.");
                    editTextPhone.requestFocus();
                }else if(stringPhoneUser.length() < 10 || stringPhoneUser.length() > 12){
                    editTextPhone.setError("Phone number must be between 10 and 12 digits.");
                    editTextPhone.requestFocus();
                    return;
                }else if (checkPhone()){
                    editTextPhone.setError("Phone number must only contain numbers.");
                    editTextPhone.requestFocus();
                    return;
                }
                stringPasswordUser = editTextPassword.getText().toString();
                if(stringPasswordUser.isEmpty()){
                    editTextPassword.setError("Password field is still empty.");
                    editTextPassword.requestFocus();
                }else if(stringPasswordUser.length() < 6){
                    editTextPassword.setError("Password length must be over 6 characters.");
                    editTextPassword.requestFocus();
                    return;
                }else if(!Pattern.compile("[0-9]").matcher(stringPasswordUser).find() ||
                        !Pattern.compile("[A-Z]").matcher(stringPasswordUser).find() ||
                        !Pattern.compile("[a-z]").matcher(stringPasswordUser).find()){
                    editTextPassword.setError("Password must contain at least 1 digit, 1 uppercase letter and 1 lowercase letter.");
                    editTextPassword.requestFocus();
                    return;
                }

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

    private boolean checkEmail() {

        if(!stringEmailUser.contains("@") || stringEmailUser.startsWith("@") || stringEmailUser.endsWith("@")){
            return true;
        }else{
            return false;
        }

    }

    private boolean checkPhone(){
        char a;
        boolean checkPhone = false;
        String[] wordBox = new String[15];

        for(int i=0; i<stringPhoneUser.length(); i++){

            a = stringPhoneUser.charAt(i);
            wordBox[i] = String.valueOf(Character.isDigit(a));
            if(wordBox[i].equals("false")) checkPhone = true;
        }

        if(checkPhone == true){
            return true;
        }else{
            return false;
        }
    }


    private void getUserInfo() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                    String name = dataSnapshot.child("fullname").getValue().toString();


                    textViewFullname.setText(name);

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
}
