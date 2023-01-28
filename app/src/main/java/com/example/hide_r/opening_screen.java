package com.example.hide_r;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.tomer.fadingtextview.FadingTextView;

import java.util.Timer;
import java.util.TimerTask;


public class opening_screen extends AppCompatActivity {


    FadingTextView fadingTextView;
    FadingTextView fadingtext2;
    FadingTextView move_on;
    String[] intro = {" ", "Welcome", "to", "hide_r"};   // first text to fade in
     // last text to fade in
    String[] move = {" ", "press here to continue"};  // the text to be tapped in order to move to next screen
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opening_screeen_layout);
        opening();   // runs the main method



        View myView = findViewById(R.id.move);
        myView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switchActivities();   //when the move tag, which is the final fading text, is touched the app switches activites to MainActivity
                // ... Respond to touch events
                return true;
            }
        });


    }

    private void switchActivities() {
        Intent switchActivityIntent = new Intent(this, MainActivity.class);  //switches from opening_screen activity to MainActivity
        startActivity(switchActivityIntent);
    }

    public void opening(){
        //main method for this activity

        fadingTextView = findViewById(R.id.fadingTextView);     //sets tag for text1
        fadingTextView.setTexts(intro);


        move_on = findViewById(R.id.move);    //sets a tag for text3
        move_on.setTexts(move);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {  //sets timer function to stop the fading texts
                fadingTextView.stop();

                // this code will be executed after 6 seconds
            }
        }, 3500);



        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                move_on.stop();

                // this code will be executed after 7 seconds
            }
        }, 7000);
    }


}
