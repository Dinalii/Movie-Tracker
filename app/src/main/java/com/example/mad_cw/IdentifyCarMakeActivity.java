package com.example.mad_cw;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IdentifyCarMakeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public String[] allcarMakes = {"Audi", "Benz", "BMW", "Honda", "KIA", "Lexus", "Mazda", "Skoda", "Subaru", "Toyota", "Volvo"};


    // Arrays that reference to the car images categorized as manufacturer.
    String[] imagesAudi = {"audi_1", "audi_2", "audi_3", "audi_4", "audi_5", "audi_6", "audi_7", "audi_8", "audi_9", "audi_10"};
    String[] imagesBenz = {"benz_1", "benz_2", "benz_3", "benz_4", "benz_5", "benz_6", "benz_7", "benz_8", "benz_9", "benz_10"};
    String[] imagesBMW = {"bmw_1", "bmw_2", "bmw_3", "bmw_4", "bmw_5", "bmw_6", "bmw_7", "bmw_8", "bmw_9", "bmw_10"};
    String[] imagesHonda = {"honda_1", "honda_2", "honda_3", "honda_4", "honda_5", "honda_6", "honda_7", "honda_8", "honda_9", "honda_10"};
    String[] imagesKIA = {"kia_1", "kia_2", "kia_3", "kia_4", "kia_5", "kia_6", "kia_7", "kia_8", "kia_9", "kia_10"};
    String[] imagesLexus = {"lex_1", "lex_2", "lex_3", "lex_4", "lex_5", "lex_6", "lex_7", "lex_8", "lex_9", "lex_10"};
    String[] imagesMazda = {"maz_1", "maz_2", "maz_3", "maz_4", "maz_5", "maz_6", "maz_7", "maz_8", "maz_9", "maz_10"};
    String[] imagesSkoda = {"sko_1", "sko_2", "sko_3", "sko_4", "sko_5", "sko_6", "sko_7", "sko_8", "sko_9", "sko_10"};
    String[] imagesSubaru = {"suba_1", "suba_2", "suba_3", "suba_4", "suba_5", "suba_6", "suba_7", "suba_8", "suba_9", "suba_10"};
    String[] imagesToyota = {"toyo_1", "toyo_2", "toyo_3", "toyo_4", "toyo_5", "toyo_6", "toyo_7", "toyo_8", "toyo_9", "toyo_10"};
    String[] imagesVolvo = {"vol_1", "vol_2", "vol_3", "vol_4", "vol_5", "vol_6", "vol_7", "vol_8", "vol_9", "vol_10"};


    List<String> allDisplayedImages = new ArrayList<>();        // all the displayed images are added here. To make sure that images aren't repeated


    public String randomCarImage;
    public int randomImageIndex;
    public String randomImageOfChosenCarMake;
    private long countdownTime;          // used to pass the remaining countdown time into the saved state when the device is rotated
    private int timeLeft;
    public String selectedSpinnerLabel;
    private TextView mShowResultMessage;
    private TextView mShowCorrectAnswer;
    private Button mButtonSubmitNext;
    private boolean mCountdownToggle;
    private CountDownTimer mCountDownTimer;
    private TextView mCountDownText;
    private ProgressBar mCountProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_make);

        mShowResultMessage = /*(TextView)*/ findViewById(R.id.result_text);         // Connecting TextView to variable
        mShowCorrectAnswer = /*(TextView)*/ findViewById(R.id.correct_carMake_answer);         // Connecting TextView to variable
        mButtonSubmitNext = /*(Button)*/ findViewById(R.id.submit_button_search);         // Connecting Button to variable
        mCountdownToggle = getIntent().getExtras().getBoolean("Countdown");         // getting the status of the switch in the main screen

        //---------Spinner
        // create the spinner
        Spinner spinner = findViewById(R.id.carMake_spinner);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
        }

        // Create ArrayAdapter using the string array and default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.carMake_array, android.R.layout.simple_spinner_item);


        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
        }

        // restore the state
        // if screen was rotated and activity was restarted
        if (savedInstanceState != null) {

            randomCarImage = savedInstanceState.getString("displayed_carImage");
            randomImageIndex = savedInstanceState.getInt("displayed_index");
            allDisplayedImages = savedInstanceState.getStringArrayList("displayed_images");
            selectedSpinnerLabel = savedInstanceState.getString("spinner_chosen");
            countdownTime = savedInstanceState.getLong("time_left");
            String buttonText = savedInstanceState.getString("button_text");
            //System.out.println(buttonText);

            CharSequence correctAns = savedInstanceState.getCharSequence("correct_answer_text");
            mShowCorrectAnswer.setText(correctAns);

            CharSequence resultText = savedInstanceState.getCharSequence("result_text");
            mShowResultMessage.setText(resultText);
            //System.out.println(mShowResultMessage.toString());

            if (mCountdownToggle) {
                mCountDownText = findViewById(R.id.timer_text);
                mCountDownText.setVisibility(View.VISIBLE);             // show countdown timer

                timeLeft = (int) (countdownTime / 1000);
                mCountDownText.setText(Integer.toString(timeLeft));

                // Re applying circular progress countdown colours
                mCountProgress = findViewById(R.id.circular_progress_timer);        // circular progress bar for countdown
                mCountProgress.setProgress(100);                                   // resetting progress bar

                final GradientDrawable mProgressCircle = (GradientDrawable) mCountProgress.getProgressDrawable();      // getting the drawable shape of the progress bar

                if (timeLeft <= 2) {
                    mProgressCircle.setColor(Color.RED);
                } else if (timeLeft <= 5) {
                    mProgressCircle.setColor(Color.parseColor("#ffa000"));
                } else {
                    mProgressCircle.setColor(Color.parseColor("#880E4F"));
                }


                if (resultText.toString().equals("CORRECT!")) {
                    mShowResultMessage.setTextColor(Color.parseColor("#42bf2d"));
                    mCountDownText.setText(Integer.toString(timeLeft));     // show time that was left
                } else if (resultText.toString().equals("WRONG!")) {
                    mShowResultMessage.setTextColor(Color.RED);
                    mCountDownText.setText(Integer.toString(timeLeft));     // show time that was left
                } else if (resultText.toString().equals("Time's up!")) {
                    mShowResultMessage.setTextColor(Color.BLUE);
                    mCountDownText.setText(Integer.toString(timeLeft));     // show time that was left
                } else {
                    if (mCountdownToggle) {
                        // run the timer only if a result isn't displayed already
                        runTimer(countdownTime);
                    }
                }
            }


            if (buttonText.equals("Next")) {
                mButtonSubmitNext.setText("Next");

                if (resultText.toString().equals("CORRECT!")) {
                    mShowResultMessage.setTextColor(Color.parseColor("#42bf2d"));
                } else if (resultText.toString().equals("WRONG!")) {
                    mShowResultMessage.setTextColor(Color.RED);
                    mShowCorrectAnswer.setTextColor(Color.BLUE);
                }
            } else {
                mButtonSubmitNext.setText("Submit");

            }


            // getting the String value that comes with the key "displayed_carImage"
            displayRelevantImage(randomCarImage, randomImageIndex);

            // display chosen random image
            ImageView imageCar = findViewById(R.id.random_car_image);
            int resource_id = getResources().getIdentifier(randomImageOfChosenCarMake, "drawable", "com.example.mad_cw");
            imageCar.setImageResource(resource_id);

            //            System.out.println(allDisplayedImages);         // to check whether arrayList is saved


        } else {            // if activity was created for the first time (opened)
            //----------Display initial random image
            displayRandomImage();

            //------------Game, if Countdown is toggled on
            // check if the countdown timer is on and run the countdown timer here, else follow the normal method
            final long SET_TIME = 10000;

            if (mCountdownToggle) {
                runTimer(SET_TIME);

            } else {
                // proceed with the normal game flow, without the countdown timer
            }

        }

    }

    @Override
    protected void onDestroy() {                // when going back to the main menu
        super.onDestroy();

        if (mCountdownToggle) {                 // only if the countdown toggle had been turned on
            if (mCountDownTimer != null) {
                mCountDownTimer.cancel();       // stopping the countdown running in the background
            }
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("displayed_carImage", randomCarImage);         // saving displayed breed
        outState.putInt("displayed_index", randomImageIndex);             // saving displayed index of chosen image
        outState.putStringArrayList("displayed_images", (ArrayList<String>) allDisplayedImages);            // saving arrayList of displayed images
        outState.putString("spinner_chosen", selectedSpinnerLabel);          // spinner that has been chosen
        outState.putCharSequence("result_text", mShowResultMessage.getText());
        outState.putCharSequence("correct_answer_text", mShowCorrectAnswer.getText());
        outState.putString("button_text", mButtonSubmitNext.getText().toString());   // to make sure that button text isn't reset to "Submit" all the time
        outState.putLong("time_left", countdownTime);          // time left in countdown timer
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedSpinnerLabel = adapterView.getItemAtPosition(i).toString();
        //        displayToast(selectedSpinnerLabel);             // used to check whether the correct spinner item was identified. <Can remove later>
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void displayToast(String message) {          // used to check whether the spinner works as expected
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }


    public void displayRandomImage() {           // method used to display a random image of a random car

        ImageView imageCarType = findViewById(R.id.random_car_image);

        do {            // making sure that the same images aren't repeated
            // this will run in an infinite loop once all the images are displayed.
            randomCarImage = allcarMakes[getRandomCarImage()];           // get a random car
            randomImageIndex = getRandomImage();                         // get a random image of a particular car
            displayRelevantImage(randomCarImage, randomImageIndex);     // display a chosen image

        } while (allDisplayedImages.contains(randomImageOfChosenCarMake));

        allDisplayedImages.add(randomImageOfChosenCarMake);

        // display chosen random image
        int resource_id = getResources().getIdentifier(randomImageOfChosenCarMake, "drawable", "com.example.mad_cw");
        imageCarType.setImageResource(resource_id);

    }

    public void displayRelevantImage(String randomCarMakeImage, int randomImageIndex) {         // display a chosen image
        switch (randomCarMakeImage) {
            case "Audi":
                randomImageOfChosenCarMake = imagesAudi[randomImageIndex];     // get a random image reference
                break;
            case "Benz":
                randomImageOfChosenCarMake = imagesBenz[randomImageIndex];     // get a random image reference
                break;
            case "BMW":
                randomImageOfChosenCarMake = imagesBMW[randomImageIndex];     // get a random image reference
                break;
            case "Honda":
                randomImageOfChosenCarMake = imagesHonda[randomImageIndex];     // get a random image reference
                break;
            case "KIA":
                randomImageOfChosenCarMake = imagesKIA[randomImageIndex];     // get a random image reference
                break;
            case "Lexus":
                randomImageOfChosenCarMake = imagesLexus[randomImageIndex];     // get a random image reference
                break;
            case "Mazda":
                randomImageOfChosenCarMake = imagesMazda[randomImageIndex];     // get a random image reference
                break;
            case "Skoda":
                randomImageOfChosenCarMake = imagesSkoda[randomImageIndex];     // get a random image reference
                break;
            case "Subaru":
                randomImageOfChosenCarMake = imagesSubaru[randomImageIndex];     // get a random image reference
                break;
            case "Toyota":
                randomImageOfChosenCarMake = imagesToyota[randomImageIndex];     // get a random image reference
                break;
            case "Volvo":
                randomImageOfChosenCarMake = imagesVolvo[randomImageIndex];     // get a random image reference
        }

    }


    public int getRandomCarImage() {
        //get random number between 0-10 (index range for 11 carMake in the array)
        Random r = new Random();
        return r.nextInt(11);
    }

    public int getRandomImage() {
        //get random number between 0-9 (index range for 10 image references in the array)
        Random r = new Random();
        return r.nextInt(10);
    }

    public void submitCheck(View view) {
        getResult();            // follow steps to display resulty
    }


    public void getResult() {         // steps that are followed when the result is required to be shown
        if (mButtonSubmitNext.getText().equals("Identify")) {        // Submit has been clicked
            if (selectedSpinnerLabel.equals(randomCarImage)) {
                mShowResultMessage.setText("CORRECT!");
                mShowResultMessage.setTextColor(Color.parseColor("#42bf2d"));
                mShowCorrectAnswer.setText(randomCarImage);
                mShowCorrectAnswer.setTextColor(Color.parseColor("#fdee00"));
            } else {
                mShowResultMessage.setText("WRONG!");
                mShowResultMessage.setTextColor(Color.RED);

                mShowCorrectAnswer.setText(randomCarImage);
                mShowCorrectAnswer.setTextColor(Color.parseColor("#fdee00"));
            }
            mButtonSubmitNext.setText("Next");

            if (mCountdownToggle) {
                mCountDownTimer.cancel();           // reset the countdown timer, for new image, if "Submit" was clicked before the countdown ended
            }

        } else {            // Next has been clicked
            mButtonSubmitNext.setText("Identify");
            mShowResultMessage.setText("");
            mShowCorrectAnswer.setText("");

            if (mCountdownToggle) {
                //mCountDownTimer.start();            // start the count down timer
                runTimer(10000);
            }
            displayRandomImage();       // display new random image
        }
    }


    public void runTimer(long setTime) {

        mCountDownText = findViewById(R.id.timer_text);
        mCountDownText.setVisibility(View.VISIBLE);                          // show countdown timer
        mCountProgress = findViewById(R.id.circular_progress_timer);        // circular progress bar for countdown
        mCountProgress.setProgress(100);                                   // resetting progress bar

        final GradientDrawable mProgressCircle = (GradientDrawable) mCountProgress.getProgressDrawable();            // getting the drawable shape of the progress bar


        mCountDownTimer = new CountDownTimer(setTime, 1000) {

            public void onTick(long millisUntilFinished) {
                timeLeft = (int) (1 + (millisUntilFinished / 1000));
                mCountDownText.setText(Integer.toString(timeLeft));
                mCountProgress.setProgress(timeLeft * 10);            // updating progress bar

                if (timeLeft <= 2) {
                    mProgressCircle.setColor(Color.RED);
                } else if (timeLeft <= 5) {
                    mProgressCircle.setColor(Color.parseColor("#ffa000"));
                } else if (timeLeft == 0) {
                    mProgressCircle.setColor(Color.RED);
                    mShowCorrectAnswer.setText(randomCarImage);
                    mShowCorrectAnswer.setTextColor(Color.parseColor("#fdee00"));
                } else {
                    mProgressCircle.setColor(Color.parseColor("#880E4F"));
                }
                System.out.println("Waiting for " + timeLeft + " secs...");
                countdownTime = millisUntilFinished;
            }

            public void onFinish() {
                mCountProgress.setProgress(0);            // updating progress bar

                if (mButtonSubmitNext.getText().equals("Identify")) {             // checking if Next button was clicked by the user
                    //"Submit" will be shown only if Next was clicked
                    mCountDownText.setText(Integer.toString(0));
                    getResult();            // follow steps to display result

                    //repeating should be done only when the "Next button is clicked"
                    //start();            // this will get the CountDownTimer to repeat
                }
            }
        }.start();
    }
}
/*

References:

https://stackoverflow.com/questions/4602902/how-to-set-the-text-color-of-textview-in-code
https://material.io/design/color/the-color-system.html#tools-for-picking-colors

How to Restore Variables When Rotating the Device
https://www.youtube.com/watch?v=TcTgbVudLyQ

Circular Progress bar - countdown timer
https://www.youtube.com/watch?v=hSfN_aYKkzo
https://codinginflow.com/tutorials/android/circular-determinate-progressbar
https://material.io/components/progress-indicators/#specs

 */