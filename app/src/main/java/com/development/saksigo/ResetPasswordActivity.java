package com.development.saksigo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText editTextEmail;
    String email;
    Button buttonResetPassword;
    private FirebaseAuth mAuth;
    LoadingDialog loadingDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        loadingDialog = new LoadingDialog(ResetPasswordActivity.this);

        //Actionbar style
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        editTextEmail = findViewById(R.id.editText_emailAddress);
        buttonResetPassword = findViewById(R.id.button_resetPassword);

        mAuth = FirebaseAuth.getInstance();


        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editTextEmail.getText().toString().trim();

                if(email.isEmpty()){
                    editTextEmail.setError("Please provide registered email");
                    editTextEmail.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmail.setError("Enter a valid email!");
                    editTextEmail.requestFocus();
                    return;
                }

                loadingDialog.startLoadingDialog();
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(ResetPasswordActivity.this, "Check your email to reset your password!", Toast.LENGTH_LONG).show();
                            loadingDialog.dismissDialog();


                        }else{
                            Toast.makeText(ResetPasswordActivity.this, "Try again! Something wrong happenend!", Toast.LENGTH_LONG).show();
                            loadingDialog.dismissDialog();
                        }
                    }
                });
            }
        });



    }
}