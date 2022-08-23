package com.development.saksigo.ProfessionFeature;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.development.saksigo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class CompleteRegistration75Fragment extends Fragment {

    Spinner spinnerBank;
    EditText editBankNo;
    String stringBank, stringBankNumber;
    Button buttonSave75;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.profession_complete_registration_75_fragment, container, false);

        spinnerBank = root.findViewById(R.id.spinner_bank);
        editBankNo = root.findViewById(R.id.editText_bankNo);
        buttonSave75 = root.findViewById(R.id.button_save75);

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        CompleteRegistration100Fragment completeRegistration100Fragment = new CompleteRegistration100Fragment();

        buttonSave75.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringBank = spinnerBank.getSelectedItem().toString();
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

            }

        }

        );



        return root;
    }
    public void updateData(){

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("bank", stringBank);
        userMap.put("bankNo", stringBankNumber);

        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);
    }
}
