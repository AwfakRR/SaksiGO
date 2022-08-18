package com.development.saksigo.ProfessionFeature;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.development.saksigo.R;

public class CompleteRegistration50Fragment extends Fragment {

    Button buttonSaveAndContinue50;
    Spinner spinnerGender;
    EditText editTextID, editTextFirstN, editTextLastN, editTextAddress, editTextPostal, editTextCAddress, editTextCPostal;
    String stringGender, stringID, stringFirstN, stringLastN, stringAddress, stringPostal, stringCAddress, stringCPostal;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_complete_registration_50_fragment, container, false);

        spinnerGender = root.findViewById(R.id.spinner_Gender);
        editTextID = root.findViewById(R.id.editText_nationalId);
        editTextFirstN = root.findViewById(R.id.editText_firstNameId);
        editTextLastN = root.findViewById(R.id.editText_lastNameId);
        editTextAddress = root.findViewById(R.id.editText_addressId);
        editTextPostal = root.findViewById(R.id.editText_postalId);
        editTextCAddress = root.findViewById(R.id.editText_addressCurrent);
        editTextCPostal = root.findViewById(R.id.editText_postalCurrent);
        buttonSaveAndContinue50 = root.findViewById(R.id.button_save50);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.id_gender));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(arrayAdapter);

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        CompleteRegistration75Fragment completeRegistration75Fragment = new CompleteRegistration75Fragment();


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
                } else if (checkPostal()){
                    editTextPostal.setError("Postal code must only contain numbers.");
                    editTextPostal.requestFocus();
                    return;
                }else if (stringCAddress.isEmpty()){
                    editTextCAddress.setError("Current Address field is still empty!");
                    editTextCAddress.requestFocus();
                    return;
                }else if (stringCPostal.isEmpty()){
                    editTextCPostal.setError("Current Postal code field is still empty!");
                    editTextCPostal.requestFocus();
                    return;
                }else if (checkCPostal()){
                    editTextCPostal.setError("Current Postal code must only contain numbers.");
                    editTextCPostal.requestFocus();
                    return;
                }


                fragmentTransaction.replace(R.id.containerCompleteRegistration, completeRegistration75Fragment);
                fragmentTransaction.addToBackStack("professionRegistration");
                fragmentTransaction.commit();
            }
        });

        return root;
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
