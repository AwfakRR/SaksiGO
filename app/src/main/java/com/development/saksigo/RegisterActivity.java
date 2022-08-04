package com.development.saksigo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.development.saksigo.DatabaseModel.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    CheckBox checkBoxTerms, checkBoxPrivacy, checkBoxNews;
    public static int terms, privacy;
    private FirebaseAuth mAuth;
    EditText editTextPassword, editTextRePassword, editTextEmail, editTextName, editTextPhone;
    boolean passwordVisible;
    Button register;
    String email, fullname, password, rePassword, phoneNumber;
    RelativeLayout relativeLayoutProgress;
    ConstraintLayout constraintLayoutRegister;
    LoadingDialog loadingDialog;
    String stringCheckboxTerms, stringCheckboxPrivacy, stringCheckboxNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Actionbar style
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.editText_emailRegister);
        editTextName = findViewById(R.id.editText_nameRegister);
        editTextPassword = findViewById(R.id.editText_PasswordRegister);
        editTextRePassword = findViewById(R.id.editText_rePasswordRegister);
        editTextPhone = findViewById(R.id.editText_phoneRegister);
        checkBoxTerms = findViewById(R.id.checkBox_terms);
        checkBoxPrivacy = findViewById(R.id.checkBox_privacy);
        checkBoxNews = findViewById(R.id.checkBox_news);
        register = findViewById(R.id.button_registerRegister);
        relativeLayoutProgress = findViewById(R.id.relative_progressRegister);


        loadingDialog = new LoadingDialog(RegisterActivity.this);

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



        Intent intent = new Intent(this, TermsActivity.class);
        ClickableSpan clickableSpanTerms = new ClickableSpan() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(@NonNull View widget) {
                widget.cancelPendingInputEvents();
                terms++;
                privacy = 0;
                startActivity(intent);
            }

            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // Show links with underlines (optional)
                ds.setUnderlineText(true);
                ds.setARGB(255,255,168,6);
            }
        };

        ClickableSpan clickableSpanPrivacy = new ClickableSpan() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(@NonNull View widget) {
                widget.cancelPendingInputEvents();
                privacy++;
                terms = 0;
                startActivity(intent);

            }

            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // Show links with underlines (optional)
                ds.setUnderlineText(true);
                ds.setARGB(255,255,168,6);
            }
        };

        SpannableString linkText = new SpannableString(getString(R.string.see_here));
        linkText.setSpan(clickableSpanTerms, 0, linkText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence cs = TextUtils.expandTemplate("I Agree with Terms of Use. ^1 ", linkText);

        SpannableString linkTextPrivacy = new SpannableString(getString(R.string.see_here));
        linkTextPrivacy.setSpan(clickableSpanPrivacy, 0, linkTextPrivacy.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        CharSequence csPrivacy = TextUtils.expandTemplate("I Agree with the Privacy Policy. ^1 ", linkTextPrivacy);

        checkBoxTerms.setText(cs);
        // Finally, make links clickable
        checkBoxTerms.setMovementMethod(LinkMovementMethod.getInstance());

        checkBoxPrivacy.setText(csPrivacy);
        // Finally, make links clickable
        checkBoxPrivacy.setMovementMethod(LinkMovementMethod.getInstance());

        Intent intentVerification = new Intent(this, VerificationActivity.class);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editTextEmail.getText().toString();
                fullname = editTextName.getText().toString();
                password = editTextPassword.getText().toString();
                rePassword = editTextRePassword.getText().toString();
                phoneNumber = editTextPhone.getText().toString();
                stringCheckboxTerms = String.valueOf(checkBoxTerms.isChecked());
                stringCheckboxPrivacy = String.valueOf(checkBoxPrivacy.isChecked());
                stringCheckboxNews = String.valueOf(checkBoxNews.isChecked());

                if(checkEmail()){
                    editTextEmail.setError("email must contain ‘@’ and placed not at the start or at the end of the input");
                    editTextEmail.requestFocus();
                    return;
                }

                if(!email.contains(".") || email.contains("@.") || email.contains(".@")){
                    editTextEmail.setError("email must contain ‘.’ and not next to ‘@’ symbol.");
                    editTextEmail.requestFocus();
                    return;
                }

                if(fullname.length() < 5 || fullname.length() > 30){
                    editTextName.setError("username must between 5 and 30 character");
                    editTextName.requestFocus();
                    return;
                }

                if(password.length() < 6){
                    editTextPassword.setError("password length must be more than 6 characters.");
                    editTextPassword.requestFocus();
                    return;
                }

                if(!Pattern.compile("[0-9]").matcher(password).find() || !Pattern.compile("[A-Z]").matcher(password).find() || !Pattern.compile("[a-z]").matcher(password).find()){
                    editTextPassword.setError("password must contains at least 1 digit, 1 uppercased letter and 1 lowercased letter.");
                    editTextPassword.requestFocus();
                    return;
                }

                if(!password.equals(rePassword)){
                    editTextRePassword.setError("Re-enter the same password for confirmation");
                    editTextRePassword.requestFocus();
                    return;
                }

                if(phoneNumber.length() < 10 || phoneNumber.length() > 12){
                    editTextPhone.setError("phone number must be between 10 and 12 digits");
                    editTextPhone.requestFocus();
                    return;
                }

                if(checkPhone()){
                    editTextPhone.setError("phone number must contains only numbers");
                    editTextPhone.requestFocus();
                    return;
                }

                if(stringCheckboxTerms.equals("false") || stringCheckboxPrivacy.equals("false")){
                    Toast.makeText(RegisterActivity.this, "User must agree to our policies before registration", Toast.LENGTH_LONG).show();
                    return;
                }

                loadingDialog.startLoadingDialog();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Users user = new Users(email, fullname, phoneNumber, stringCheckboxTerms, stringCheckboxPrivacy, stringCheckboxNews);

                            FirebaseDatabase.getInstance("https://saksigo-30792-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("KeyPartner")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                                                loadingDialog.dismissDialog();
                                                //redirect to login layout

                                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                                firebaseUser.sendEmailVerification();


                                                startActivity(intentVerification);
                                                finish();

                                            }else {
                                                Toast.makeText(RegisterActivity.this, "Registration Failed!", Toast.LENGTH_LONG).show();
                                                loadingDialog.dismissDialog();

                                            }
                                        }
                                    });
                        }else {
                            Toast.makeText(RegisterActivity.this, "Registration Failed!", Toast.LENGTH_LONG).show();
                            loadingDialog.dismissDialog();

                        }
                    }
                });

            }
        });




    }

    private boolean checkEmail() {

        if(!email.contains("@") || email.startsWith("@") || email.endsWith("@")){
            return true;
        }else{
            return false;
        }

    }

    private boolean checkPhone(){
        char a;
        boolean checkPhone = false;
        String[] wordBox = new String[15];

        for(int i=0; i<phoneNumber.length(); i++){

            a = phoneNumber.charAt(i);
            wordBox[i] = String.valueOf(Character.isDigit(a));
            if(wordBox[i].equals("false")) checkPhone = true;
        }

        if(checkPhone == true){
            return true;
        }else{
            return false;
        }
    }

}