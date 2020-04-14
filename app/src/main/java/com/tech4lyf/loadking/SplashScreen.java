package com.tech4lyf.loadking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.tech4lyf.loadking.Helpers.AppSignatureHelper;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(this);
        appSignatureHelper.getAppSignatures();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent actsignup=new Intent(SplashScreen.this,SignupScreen.class);
                startActivity(actsignup);
            }
        },2000);
    }
}
