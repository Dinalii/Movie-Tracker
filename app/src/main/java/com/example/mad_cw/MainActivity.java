package com.example.mad_cw;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private Button identifycarmakebutton;
    private Button hintsbutton;
    private Button identifycarimagebutton;
    private Button advancedlevelbutton;
    private Switch countdownswitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // remove title bar of app
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countdownswitch = findViewById(R.id.switch_countdown);

        // Opening of "Identify car make " activity using button click
        identifycarmakebutton = (Button) findViewById(R.id.button_identify_the_car_make);
        identifycarmakebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IdentifyCarMakeActivity.class);

                // letting the activity know whether the countdown was toggled on or off
                intent.putExtra("Countdown", countdownswitch.isChecked());

                startActivity(intent);
            }
        });

        // Opening of "Hints " activity using button click
        hintsbutton = (Button) findViewById(R.id.button_hints);
        hintsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HintsActivity.class);

                // letting the activity know whether the countdown was toggled on or off
                intent.putExtra("Countdown", countdownswitch.isChecked());

                startActivity(intent);
            }
        });

        // Opening of "Identify car image " activity using button click
        identifycarimagebutton = (Button) findViewById(R.id.button_identify_car_image);
        identifycarimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IdentifyCarImageActivity.class);

                // letting the activity know whether the countdown was toggled on or off
                intent.putExtra("Countdown", countdownswitch.isChecked());

                startActivity(intent);
            }
        });

        // Opening of "Advanced Level " activity using button click
        advancedlevelbutton = (Button) findViewById(R.id.button_advanced_level);
        advancedlevelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdvancedLevelActivity.class);

                // letting the activity know whether the countdown was toggled on or off
                intent.putExtra("Countdown", countdownswitch.isChecked());

                startActivity(intent);
            }
        });
    }

}

/*
References

Customized Button
https://www.youtube.com/watch?v=B5I08OK-Bu0

*/