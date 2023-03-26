package com.example.hide_r;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.Executor;


public class bioAuth extends AppCompatActivity {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_auth);


        ArrayList<View> viewsToFadeIn = new ArrayList<View>();
        viewsToFadeIn.add(findViewById(R.id.authButton));
        viewsToFadeIn.add(findViewById(R.id.authText));
        for (View v : viewsToFadeIn)
        {
            v.setAlpha(0); // make invisible to start
        }
        for (View v : viewsToFadeIn)
        {
            // 2 second fade in time
            v.animate().alpha(1.0f).setDuration(2400).start();
        }


        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(bioAuth.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                                "Authentication error: " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded", Toast.LENGTH_SHORT).show();

                MainActivity.dr.decryptDirectory(MainActivity.inputDirectory, MainActivity.inputDirectory);
                switchActivities();//proceeds to the gallery activity
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Confidential Gallery Access")
                .setSubtitle("Use biometric credentials")
                .setNegativeButtonText("Use account password")
                .build();

        // Prompt appears when user clicks "Authenticate".

        Button biometricLoginButton = findViewById(R.id.authButton);
        biometricLoginButton.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });

        Button returnButton = (Button) findViewById(R.id.returnMAIN);
        assert returnButton != null;
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();   //when the return Button is pressed we switch back to the camera class
            }
        });

    }



    private void switchActivities() {  //proceeds to gallery class
        Intent switchActivityIntent = new Intent(this, Gallery.class);
        startActivity(switchActivityIntent);
    }



    private void goBack() {  //returns to the main class
        Intent switchActivityIntent = new Intent(this, MainActivity.class);
        startActivity(switchActivityIntent);
    }



}