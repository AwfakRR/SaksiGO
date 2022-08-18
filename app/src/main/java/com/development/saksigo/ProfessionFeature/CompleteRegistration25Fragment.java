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

import com.development.saksigo.LoadingDialog;
import com.development.saksigo.LoginActivity;
import com.development.saksigo.R;

public class CompleteRegistration25Fragment extends Fragment {

    String stringAboutYou, stringVideoLink, stringLegalServices;
    EditText editTextAboutYou, editTextVideoLink;
    Button buttonSaveContinue;
    Spinner spinnerLegalServices;
    LoadingDialog loadingDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_complete_registration_25_fragment, container, false);
        loadingDialog = new LoadingDialog(getActivity());


        editTextAboutYou = (EditText) root.findViewById(R.id.editText_aboutYouProfession);
        editTextVideoLink = (EditText) root.findViewById(R.id.editText_videoLinkProfession);
        spinnerLegalServices = (Spinner) root.findViewById(R.id.spinner_LegalServices);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.id_advocateType));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLegalServices.setAdapter(arrayAdapter);


        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        CompleteRegistration50Fragment completeRegistration50Fragment = new CompleteRegistration50Fragment();

        buttonSaveContinue = (Button) root.findViewById(R.id.button_save25);
        buttonSaveContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                fragmentTransaction.replace(R.id.containerCompleteRegistration, completeRegistration50Fragment);
                fragmentTransaction.addToBackStack("professionRegistration");
                fragmentTransaction.commit();
                loadingDialog.dismissDialog();
            }
        });

        return root;
    }
}
