package com.development.saksigo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Timer timer;
    Intent intent;
    String emailSP, passwordSP, loginAgain="false";
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        SharedPreferences sharedPreferences = this.getSharedPreferences("LoginActivity", MODE_PRIVATE);



        emailSP = sharedPreferences.getString("email", null);
        passwordSP = sharedPreferences.getString("password", null);
        loginAgain = sharedPreferences.getString("loginAgain", "false");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
//        mAuth.getCurrentUser().reload();

        intent = new Intent(this, IntroductionActivity.class);
        timer = new Timer();

        Intent intentLogin = new Intent(this, HomeActivity.class);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if(loginAgain.equals("true")){
                    mAuth.signInWithEmailAndPassword(emailSP, passwordSP).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            startActivity(intentLogin);
                            finish();
                        }
                    });
                    finish();
                }else{
                    startActivity(intent);
                    finish();
                }

            }
        }, 2000);



    }



}