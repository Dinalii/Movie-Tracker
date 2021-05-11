package com.example.mad_cw;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IdentifyCarImageActivity extends AppCompatActivity {


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
    private List<String> displayingCarMakes = new ArrayList<>();        // the 3 displayed carMakes will be here. To make sure that multiple images of the same type won't be shown together
    private List<Integer> displayingImageIndexes = new ArrayList<>();        // the 3 displayed image indexes will be here. To reload the images when the device is rotated.

    public String randomCarImage;
    public String randomImageOfChosenCarImage;
    private String questionCarMake;           // Car Make that is displayed as the question
    private boolean isFlagFirstPick;         // used to check if one image was selected  -  to make sure that the user can't take multiple chances
    private long countdownTime;             // used to pass the remaining countdown time into the saved state when the device is rotated
    private int timeLeft;
    private TextView mCarMakeNameLabel;
    private ImageView mPickedImage;
    private TextView mShowResultMessage;
    private boolean mCountdownToggle;
    private CountDownTimer mCountDownTimer;
    private TextView mCountDownText;
    private ProgressBar mCountProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_car_image);

        mCarMakeNameLabel = /*(TextView)*/ findViewById(R.id.carType_name_label);         // Connecting TextView to variable
        mShowResultMessage = /*(TextView)*/ findViewById(R.id.result_text);         // Connecting TextView to variable
        mCountdownToggle = getIntent().getExtras().getBoolean("Countdown");         // getting the status of the switch in the main screen

        // restore the state
        if (savedInstanceState != null) {           // if screen was rotated and activity was restarted

            countdownTime = savedInstanceState.getLong("time_left");
            questionCarMake = savedInstanceState.getString("question_carMake");
            displayingCarMakes = savedInstanceState.getStringArrayList("displaying_three");
            allDisplayedImages = savedInstanceState.getStringArrayList("all_displayed_images");
            displayingImageIndexes = savedInstanceState.getIntegerArrayList("all_displayed_image_indexes");
            CharSequence resultText = savedInstanceState.getCharSequence("result_text");

            if (mCountdownToggle) {
                mCountDownText = findViewById(R.id.timer_text);
                mCountDownText.setVisibility(View.VISIBLE);             // show countdown timer
                timeLeft = (int) (countdownTime / 1000);
                mCountDownText.setText(Integer.toString(timeLeft));

                // Re applying circular progress countdown colours
                mCountProgress = findViewById(R.id.circular_progress_timer);        // circular progress bar for countdown
                mCountProgress.setProgress(100);            // resetting progress bar

                final GradientDrawable mProgressCircle = (GradientDrawable) mCountProgress.getProgressDrawable();            // getting the drawable shape of the progress bar

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
//                // run the timer only if a result isn't displayed already
                        runTimer(countdownTime);
                    }
                }
            }

            mShowResultMessage.setText(resultText);
            mCarMakeNameLabel.setText(questionCarMake);

            // Display images that were already shown ------------------
            ImageView imageCarFirst = findViewById(R.id.first_car_image);
            ImageView imageCarSecond = findViewById(R.id.second_car_image);
            ImageView imageCarThird = findViewById(R.id.third_car_image);

            String imageOne = displayRelevantImage(displayingCarMakes.get(0), displayingImageIndexes.get(0));
            String imageTwo = displayRelevantImage(displayingCarMakes.get(1), displayingImageIndexes.get(1));
            String imageThree = displayRelevantImage(displayingCarMakes.get(2), displayingImageIndexes.get(2));

            imageCarFirst.setImageResource(getResources().getIdentifier(imageOne, "drawable", "com.example.mad_cw"));
            imageCarFirst.setTag(displayingCarMakes.get(0));          // displayingCarMake ArrayList will have the manufacturer names in order of adding
            imageCarSecond.setImageResource(getResources().getIdentifier(imageTwo, "drawable", "com.example.mad_cw"));
            imageCarSecond.setTag(displayingCarMakes.get(1));
            imageCarThird.setImageResource(getResources().getIdentifier(imageThree, "drawable", "com.example.mad_cw"));
            imageCarThird.setTag(displayingCarMakes.get(2));


        } else {                    // if activity was created for the first time (opened)

            showImageSet();         // display initial set of images

            //------------Game, if Countdown is toggled on
            // check if the countdown timer is on and run the countdown timer here, else follow the normal method
            long SET_TIME = 10000;

            if (mCountdownToggle) {
                runTimer(SET_TIME);
            } else {
                // proceed with the normal game flow, without the countdown timer
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong("time_left", countdownTime);          // time left in countdown timer
        outState.putString("question_carMake", questionCarMake);
        outState.putStringArrayList("displaying_three", (ArrayList<String>) displayingCarMakes);
        outState.putStringArrayList("all_displayed_images", (ArrayList<String>) allDisplayedImages);
        outState.putIntegerArrayList("all_displayed_image_indexes", (ArrayList<Integer>) displayingImageIndexes);
        outState.putCharSequence("result_text", mShowResultMessage.getText());

    }

    @Override
    protected void onDestroy() {                // when going back to the main menu
        super.onDestroy();
        if (mCountdownToggle) {         // only if the countdown toggle had been turned on
            if (mCountDownTimer != null) {
                mCountDownTimer.cancel();           // stopping the countdown running in the background
            }
        }
    }


    public void showImageSet() {
        ImageView imageCarFirst = findViewById(R.id.first_car_image);
        ImageView imageCarSecond = findViewById(R.id.second_car_image);
        ImageView imageCarThird = findViewById(R.id.third_car_image);

        imageCarFirst.setImageResource(displayRandomImage());
        imageCarFirst.setTag(displayingCarMakes.get(0));          // displaying carMake ArrayList will have the manufacturer names in order of adding
        imageCarSecond.setImageResource(displayRandomImage());
        imageCarSecond.setTag(displayingCarMakes.get(1));
        imageCarThird.setImageResource(displayRandomImage());
        imageCarThird.setTag(displayingCarMakes.get(2));

        mShowResultMessage.setText("");
        choosecarMakeToBeIdentified();            // display carMake to be identified
    }


    public int displayRandomImage() {           // method used to display a random image of a random manufacturer
        int randomImageIndex;
        do
        {    // making sure that the same images aren't repeated & images of the same manufacturer aren't shown together
            // this will run in an infinite loop once all the images are displayed.

            randomCarImage = allcarMakes[getRandomCarImage()];   // get a random manufacturer
            randomImageIndex = getRandomImage();                // get a random image of a particular manufacturer
            randomImageOfChosenCarImage = displayRelevantImage(randomCarImage, randomImageIndex);

        } while (allDisplayedImages.contains(randomImageOfChosenCarImage) || displayingCarMakes.contains(randomCarImage));

        allDisplayedImages.add(randomImageOfChosenCarImage);        // to make sure that the image isn't repeated
        displayingCarMakes.add(randomCarImage);                     // to make sure that images of the same manufacturer aren't shown at once
        displayingImageIndexes.add(randomImageIndex);               // to recall indexes when the device is rotated

        // return chosen random image
        return getResources().getIdentifier(randomImageOfChosenCarImage, "drawable", "com.example.mad_cw");
    }


    public String displayRelevantImage(String randomCarMakeImage, int randomImageIndex) {         // display a chosen image
        switch (randomCarMakeImage) {
            case "Audi":
                randomImageOfChosenCarImage = imagesAudi[randomImageIndex];     // get a random image reference
                break;
            case "Benz":
                randomImageOfChosenCarImage = imagesBenz[randomImageIndex];     // get a random image reference
                break;
            case "BMW":
                randomImageOfChosenCarImage = imagesBMW[randomImageIndex];     // get a random image reference
                break;
            case "Honda":
                randomImageOfChosenCarImage = imagesHonda[randomImageIndex];     // get a random image reference
                break;
            case "KIA":
                randomImageOfChosenCarImage = imagesKIA[randomImageIndex];     // get a random image reference
                break;
            case "Lexus":
                randomImageOfChosenCarImage = imagesLexus[randomImageIndex];     // get a random image reference
                break;
            case "Mazda":
                randomImageOfChosenCarImage = imagesMazda[randomImageIndex];     // get a random image reference
                break;
            case "Skoda":
                randomImageOfChosenCarImage = imagesSkoda[randomImageIndex];     // get a random image reference
                break;
            case "Subaru":
                randomImageOfChosenCarImage = imagesSubaru[randomImageIndex];     // get a random image reference
                break;
            case "Toyota":
                randomImageOfChosenCarImage = imagesToyota[randomImageIndex];     // get a random image reference
                break;
            case "Volvo":
                randomImageOfChosenCarImage = imagesVolvo[randomImageIndex];     // get a random image reference
        }
        // return chosen random image
        return randomImageOfChosenCarImage;

    }

    public int getRandomCarImage() {
        //get random number between 0-10 (index range for 11 breeds in the array)
        Random r = new Random();
        return r.nextInt(11);
    }

    public int getRandomImage() {
        //get random number between 0-9 (index range for 10 image references in the array)
        Random r = new Random();
        return r.nextInt(10);
    }

    private void choosecarMakeToBeIdentified() {           // used to pick the breed to be identified
        Random r = new Random();
        int questionCarMakeIndex = r.nextInt(3);

        questionCarMake = displayingCarMakes.get(questionCarMakeIndex);
        mCarMakeNameLabel.setText(questionCarMake);

    }

    public void showNextImageSet(View view) {        // when "Next" button is clicked, shows new set of images
        displayingCarMakes.clear();                 // for a new set of image car Makes
        displayingImageIndexes.clear();            // for a new set of images
        isFlagFirstPick = false;                  // resetting the flag for picking an image

        if (mCountdownToggle) {
            if (mCountDownTimer != null) {          // if next is touched repeatedly, this will prevent the countdown timer messing up
                mCountDownTimer.cancel();
            }
            runTimer(10000);
        }
        showImageSet();
    }


    public void checkAnswerFirst(View view) {                                 // when the first image is clicked, checks if the answer is correct
        mPickedImage = findViewById(R.id.first_car_image);
        if (!mShowResultMessage.getText().equals("Time's up!")) {            // making sure that the user can't choose an image after the timer is over
            displayResult();
        }
    }

    public void checkAnswerSecond(View view) {                              // when the second image is clicked, checks if the answer is correct
        mPickedImage = findViewById(R.id.second_car_image);
        if (!mShowResultMessage.getText().equals("Time's up!")) {            // making sure that the user can't choose an image after the timer is over
            displayResult();
        }
    }

    public void checkAnswerThird(View view) {                                 // when the third image is clicked, checks if the answer is correct
        mPickedImage = findViewById(R.id.third_car_image);
        if (!mShowResultMessage.getText().equals("Time's up!")) {            // making sure that the user can't choose an image after the timer is over
            displayResult();
        }
    }


    public void displayResult() {
        try {
            if (!isFlagFirstPick) {                                         // allowing the user to take only one attempt
                isFlagFirstPick = true;
                if (mPickedImage.getTag().equals(questionCarMake)) {        // If the displayed car make was picked properly
                    mShowResultMessage.setText("CORRECT!");
                    mShowResultMessage.setTextColor(Color.parseColor("#42bf2d"));
                } else {
                    mShowResultMessage.setText("WRONG!");
                    mShowResultMessage.setTextColor(Color.RED);
                }
            }
            mPickedImage = null;        // need for countdown game -> otherwise previous image selected position will be taken

            // can't do with set tag as it's done when the images are loaded
        } catch (Exception e) {          // will come here, if no image was chosen -> needed for the countdown game
            mShowResultMessage.setText("Time's up!");
            mShowResultMessage.setTextColor(Color.BLUE);
        }

        if (mCountdownToggle) {
            mCountDownTimer.cancel();           // reset the countdown timer, for new image, if "Next" was clicked before the countdown ended
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
                } else {
                    mProgressCircle.setColor(Color.parseColor("#880E4F"));
                }

                System.out.println("Waiting for " + timeLeft + " secs...");
                countdownTime = millisUntilFinished;
            }

            public void onFinish() {
                mCountProgress.setProgress(0);            // updating progress bar
                mCountDownText.setText(Integer.toString(0));
                displayResult();                          // follow steps to display result
                //repeating should be done only when the "Next button is clicked"
            }

        }.start();
    }
}


/*

References:

https://stackoverflow.com/questions/6449654/how-to-get-image-resource-name/14028623

https://stackoverflow.com/questions/15213974/add-multiple-items-to-already-initialized-arraylist-in-java

https://stackoverflow.com/questions/6751564/how-to-pass-a-boolean-between-intents

Dynamically change colour of drawable
https://www.codota.com/code/java/methods/android.graphics.drawable.GradientDrawable/setColor

 */