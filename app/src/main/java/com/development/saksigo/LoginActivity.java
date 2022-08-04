package com.development.saksigo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    String email, password, userId;
    Button buttonLogin;

    String i;

    boolean passwordVisible;
    TextView textViewForgotPassword;

    LoadingDialog loadingDialog;

    //Firebase variable
    private FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;

    LandingActivity landingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginActivity", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Firebase Auth

//        mAuth.getCurrentUser().reload();

        //Actionbar style
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        loadingDialog = new LoadingDialog(LoginActivity.this);

        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editTextPassword_password);
        textViewForgotPassword = findViewById(R.id.textView_forgotPassword);
        buttonLogin = findViewById(R.id.button_loginLogin);

        editTextPassword.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction() == motionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=editTextPassword.getRight()-editTextPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = editTextPassword.getSelectionEnd();
                        if(passwordVisible){
                            editTextPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_lock,0,R.drawable.ic_baseline_visibility_off,0);



                            editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible = false;
                        }else{
                            editTextPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_baseline_lock,0,R.drawable.ic_baseline_visibility_on,0);

                            editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible = true;
                        }
                        editTextPassword.setSelection(selection);
                        return true;
                    }
                }


                return false;
            }
        });

        Intent intent = new Intent(this, ResetPasswordActivity.class);
        ClickableSpan clickableSpanTerms = new ClickableSpan() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(@NonNull View widget) {
                widget.cancelPendingInputEvents();
                startActivity(intent);

            }

            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // Show links with underlines (optional)
                ds.setUnderlineText(true);
                ds.setARGB(255,255,168,6);
                ds.setFakeBoldText(true);
            }
        };

        SpannableString linkText = new SpannableString("Reset Here");
        linkText.setSpan(clickableSpanTerms, 0, linkText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence cs = TextUtils.expandTemplate("Forgot Password? ^1", linkText);

        textViewForgotPassword.setText(cs);
        // Finally, make links clickable
        textViewForgotPassword.setMovementMethod(LinkMovementMethod.getInstance());

        Intent intentHome = new Intent(this, HomeActivity.class);



        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if(email.isEmpty()){
                    editTextEmail.setError("Email cannot be empty");
                    editTextEmail.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmail.setError("Enter a valid email!");
                    editTextEmail.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    editTextPassword.setError("Password cannot be empty");
                    editTextPassword.requestFocus();
                    return;
                }






//                if(!email.isEmpty()) {
//                    Log.i("tag", email);
//                    Log.i("tag", password);
//                    return;
//                }

                mAuth = FirebaseAuth.getInstance();
                loadingDialog.startLoadingDialog();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        user = mAuth.getCurrentUser();
                        mAuth.getCurrentUser().reload();
                        if(task.isSuccessful() && user.isEmailVerified()){




                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.putString("loginAgain", "true");
                            editor.commit();


                            loadingDialog.dismissDialog();
                            startActivity(intentHome);





                            finish();



                        }else if(!user.isEmailVerified()){
                            Toast.makeText(LoginActivity.this, "Please verify your email address!", Toast.LENGTH_LONG).show();
                            return;
                        }else if(!task.isSuccessful()){
                            loadingDialog.dismissDialog();
                            Toast.makeText(LoginActivity.this, "Please provide a correct email and password!", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });

    }


}