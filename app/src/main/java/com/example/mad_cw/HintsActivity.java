package com.example.mad_cw;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mad_cw.Adapter.GridViewAnswerAdapter;
import com.example.mad_cw.Adapter.GridViewSuggestAdapter;
import com.example.mad_cw.Common.Common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HintsActivity extends AppCompatActivity {

    public List<String> suggestSource = new ArrayList<>();
    public GridViewAnswerAdapter answerAdapter;
    public GridViewSuggestAdapter suggestAdapter;
    public Button submitBtn;
    public GridView gridViewAnswer;
    public GridView gridViewSuggest;
    public ImageView imageViewQuestion;
    private TextView mShowResultMessage;
    private TextView mShowCorrectAnswer;
    private boolean mCountdownToggle;
    private CountDownTimer mCountDownTimer;
    private TextView mCountDownText;
    private ProgressBar mCountProgress;
    private long countdownTime;          // used to pass the remaining countdown time into the saved state when the device is rotated
    private int timeLeft;
    public char[] answer;
    private int numClicks = 0;
    String correct_answer;
    int[] image_list = {R.drawable.audi, R.drawable.bmw, R.drawable.benz, R.drawable.honda, R.drawable.kia, R.drawable.lexus, R.drawable.mazda, R.drawable.skoda, R.drawable.subaru, R.drawable.toyota,
            R.drawable.volvo, R.drawable.jaguar, R.drawable.mustang, R.drawable.mitsubishi, R.drawable.bugatti, R.drawable.renault, R.drawable.volkswagn, R.drawable.mg, R.drawable.ds, R.drawable.lambogini, R.drawable.ford};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hints);

        mCountdownToggle = getIntent().getExtras().getBoolean("Countdown");         // getting the status of the switch in the main screen

        // restore the state
        // if screen was rotated and activity was restarted
        if (savedInstanceState != null) {

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
                submitBtn.setText("Next");
                if (resultText.toString().equals("CORRECT!")) {
                    mShowResultMessage.setTextColor(Color.parseColor("#42bf2d"));
                } else if (resultText.toString().equals("WRONG!")) {
                    mShowResultMessage.setTextColor(Color.RED);
                    mShowCorrectAnswer.setTextColor(Color.BLUE);
                }
            } else {
                submitBtn.setText("Submit");

            }


            // getting the String value that comes with the key "displayed_carImage"
            // displayRelevantImage(randomCarImage, randomImageIndex);

            // display chosen random image
            ImageView imageCar = findViewById(R.id.random_car_image);

            //            System.out.println(allDisplayedImages);         // to check whether arrayList is saved


        } else {            // if activity was created for the first time (opened)

            //------------Game, if Countdown is toggled on
            // check if the countdown timer is on and run the countdown timer here, else follow the normal method
            final long SET_TIME = 10000;

            if (mCountdownToggle) {
                runTimer(SET_TIME);

            } else {
                // proceed with the normal game flow, without the countdown timer
            }
        }
        initView();
    }

    private void initView() {
        gridViewAnswer = /*(GridView)*/ findViewById(R.id.gridViewAnswer);          // Connecting GridView to variable
        gridViewSuggest = /*(GridView)*/ findViewById(R.id.gridViewSuggestion);     // Connecting GridView to variable
        imageViewQuestion = /*(ImageView)*/ findViewById(R.id.hint_image);          // Connecting TextView to variable
        mShowResultMessage = /*(TextView)*/ findViewById(R.id.result_text);         // Connecting TextView to variable
        mShowCorrectAnswer = /*(TextView)*/ findViewById(R.id.correct_carMake_answer);         // Connecting TextView to variable

        //Add SetupList
        setupList();

        submitBtn = (Button) findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";

                for (int i = 0; i < Common.user_submit_answer.length; i++)
                    result += String.valueOf(Common.user_submit_answer[i]);

                if (submitBtn.getText().equals("Submit")) {        // Submit has been clicked
                    if (result.equals(correct_answer)) {

                        //Reset
                        Common.count = 0;
                        Common.user_submit_answer = new char[correct_answer.length()];

                        //Set Adapter
                        GridViewAnswerAdapter answerAdapter = new GridViewAnswerAdapter(setupNullList(), getApplicationContext());
                        gridViewAnswer.setAdapter(answerAdapter);
                        answerAdapter.notifyDataSetChanged();

                        GridViewSuggestAdapter suggestAdapter = new GridViewSuggestAdapter(suggestSource, getApplicationContext(), HintsActivity.this);
                        gridViewSuggest.setAdapter(suggestAdapter);
                        suggestAdapter.notifyDataSetChanged();

                        //Toast.makeText(getApplicationContext(),"Finish!"+result,Toast.LENGTH_SHORT).show();
                        mShowResultMessage.setText("CORRECT!");
                        mShowResultMessage.setTextColor(Color.parseColor("#42bf2d"));

                        mShowCorrectAnswer.setText(correct_answer);
                        mShowCorrectAnswer.setTextColor(Color.BLUE);

                    } else {
                        //Toast.makeText(MainActivity.this,"Incorrect",Toast.LENGTH_SHORT).show();
                        mShowResultMessage.setText("WRONG!");
                        mShowResultMessage.setTextColor(Color.RED);

                        mShowCorrectAnswer.setText(correct_answer);
                        mShowCorrectAnswer.setTextColor(Color.BLUE);

                    }
                    submitBtn.setText("Next");

                } else {            // Next has been clicked

                    submitBtn.setText("Submit");
                    mShowResultMessage.setText("");
                    mShowCorrectAnswer.setText("");

                    if (mCountdownToggle) {
                        mCountDownTimer.start();            // start the count down timer
                        runTimer(10000);
                    }
                    setupList();       // display new random image
                }
            }
        });
    }

    private void setupList() {
        //Random car
        Random random = new Random();

        int imageSelected = image_list[random.nextInt(image_list.length)];
        imageViewQuestion.setImageResource(imageSelected);

        correct_answer = getResources().getResourceName(imageSelected);
        correct_answer = correct_answer.substring(correct_answer.lastIndexOf("/") + 1);
        answer = correct_answer.toCharArray();

        Common.user_submit_answer = new char[answer.length];

        //Add Answer character to list
        suggestSource.clear();
        for (char item : answer) {
            //Add car make to the list
            suggestSource.add(String.valueOf(item));
        }

        //Randomly add some characters to list
        for (int i = answer.length; i < answer.length * 2; i++)
            suggestSource.add(Common.alphabet_character[random.nextInt(Common.alphabet_character.length)]);

        //Sort random
        Collections.shuffle(suggestSource);

        //Set for GridView
        answerAdapter = new GridViewAnswerAdapter(setupNullList(), this);
        suggestAdapter = new GridViewSuggestAdapter(suggestSource, this, this);

        answerAdapter.notifyDataSetChanged();
        suggestAdapter.notifyDataSetChanged();

        gridViewSuggest.setAdapter(suggestAdapter);
        gridViewAnswer.setAdapter(answerAdapter);

    }

    private char[] setupNullList() {
        char result[] = new char[answer.length];
        for (int i = 0; i < answer.length; i++)
            result[i] = ' ';
        return result;

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
                }
                else if (timeLeft == 0) {
                    mShowResultMessage.setText("Time's up!");
                    mShowResultMessage.setTextColor(Color.BLUE);
                    mShowCorrectAnswer.setText(correct_answer);
                }else {
                    mProgressCircle.setColor(Color.parseColor("#880E4F"));
                }

                System.out.println("Waiting for " + timeLeft + " secs...");
                countdownTime = millisUntilFinished;
            }

            public void onFinish() {
                mCountProgress.setProgress(0);            // updating progress bar
                mCountDownText.setText(Integer.toString(0));
                //repeating should be done only when the "Next button is clicked"
            }
        }.start();
    }
}

/*

REFERENCES

Guessing image by fiiling blanks
https://youtu.be/6RIFhaldHzM

Set on text changed listener
https://youtu.be/daZOKNBMmsg


*/