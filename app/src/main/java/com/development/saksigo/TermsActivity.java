package com.development.saksigo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TermsActivity extends AppCompatActivity {

    TextView textViewTermsTitle;
    WebView webViewTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        //Actionbar style
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setElevation(0);

        textViewTermsTitle = findViewById(R.id.textView_termsTitle);
        webViewTerms = (WebView) findViewById(R.id.webView_terms);
        webViewTerms.setBackgroundColor(Color.TRANSPARENT);

        webViewTerms.getSettings().setJavaScriptEnabled(true);
        webViewTerms.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                view.loadUrl(
                        "javascript:document.body.style.setProperty(\"color\", \"white\");"
                );
            }
        });

        if(RegisterActivity.terms > RegisterActivity.privacy){

            textViewTermsTitle.setText(R.string.terms_and_conditions);
            webViewTerms.loadUrl("file:///android_asset/terms_and_conditions.html");
        }else{

            textViewTermsTitle.setText(R.string.privacy_policy);
            webViewTerms.loadUrl("file:///android_asset/privacy_policy.html");
        }


    }
}