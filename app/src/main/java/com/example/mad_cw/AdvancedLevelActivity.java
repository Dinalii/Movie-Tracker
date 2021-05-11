package com.example.mad_cw;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class AdvancedLevelActivity extends AppCompatActivity {

    ImageView randomImg1, randomImg2, randomImg3;
    EditText userInput1, userInput2, userInput3;
    Button submit_btn;
    TextView correctAns1, correctAns2, correctAns3;
    int submitCount = 0;
    Random random = new Random();
    int firstRanNumber = random.nextInt(24);
    int secondRanNumber = firstRanNumber + 3;
    int thirdRanNumber = secondRanNumber + 5;
    public int attempCount = 0;
    Integer[] imges1 = {
            R.drawable.audi_6, R.drawable.audi, R.drawable.audi_4, R.drawable.audi_3, R.drawable.audi_8,
            R.drawable.sko_1, R.drawable.sko_2, R.drawable.sko_3, R.drawable.sko_5, R.drawable.sko_6,
            R.drawable.bmw_3, R.drawable.bmw_1, R.drawable.bmw_2, R.drawable.bmw_4, R.drawable.benz_6,
            R.drawable.honda, R.drawable.honda_1, R.drawable.honda_3, R.drawable.honda_4, R.drawable.honda_5,
            R.drawable.kia, R.drawable.kia_2, R.drawable.kia_3, R.drawable.kia_6, R.drawable.sko_10,
            R.drawable.vol_2, R.drawable.vol_3, R.drawable.vol_4, R.drawable.vol_5, R.drawable.vol_1
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_level);


        //bta1 = findViewById(R.id.buttonAd1);
        submit_btn = findViewById(R.id.submit_btn);
        final String[] brands = {"audi", "skoda", "bmw", "honda", "kia", "volvo"};
        randomImg1 = findViewById(R.id.random_image_1);
        randomImg2 = findViewById(R.id.random_image_2);
        randomImg3 = findViewById(R.id.random_image_3);


        userInput1 = findViewById(R.id.image1_userAns);
        userInput2 = findViewById(R.id.image2_userAns);
        userInput3 = findViewById(R.id.image3_userAns);
        correctAns1 = findViewById(R.id.image_1_answer);
        correctAns2 = findViewById(R.id.image_2_answer);
        correctAns3 = findViewById(R.id.image_3_answer);

        randomImg1.setImageResource(imges1[firstRanNumber]);
        randomImg2.setImageResource(imges1[secondRanNumber]);
        randomImg3.setImageResource(imges1[thirdRanNumber]);

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput1.setText(" ");
                userInput2.setText("");
                userInput3.setText("");

                userInput1.setEnabled(true);
                userInput2.setEnabled(true);
                userInput3.setEnabled(true);

                advanced();
            }


            public void advanced() {
                submitCount = submitCount + 1;
                int TotalCorrectionCount = 0;
                int totalAttempsCount = 0;

                String getUserInput1 = userInput1.getText().toString().toLowerCase();
                String getUserInput2 = userInput2.getText().toString().toLowerCase();
                String getUserInput3 = userInput3.getText().toString().toLowerCase();

                if (firstRanNumber <= 4 && getUserInput1.equals("audi")) {//matching the userInputs with database
                    String colorright = "<font color=#49FB00>AUDI</font>";
                    userInput1.setText(Html.fromHtml(colorright));
                    userInput1.setEnabled(false);
                    TotalCorrectionCount++;


                } else if (firstRanNumber <= 4 && !getUserInput1.contentEquals("audi")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput1.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;
                } else if (firstRanNumber > 4 && firstRanNumber <= 9 && getUserInput1.equals("skoda")) {
                    String colorright = "<font color=#49FB00>SKODA</font>";
                    userInput1.setText(Html.fromHtml(colorright));
                    userInput1.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (firstRanNumber > 4 && firstRanNumber <= 9 && !getUserInput1.contentEquals("skoda")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput1.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;

                } else if (firstRanNumber > 9 && firstRanNumber <= 14 && getUserInput1.equals("bmw")) {
                    String colorright = "<font color=#49FB00>BMW</font>";
                    userInput1.setText(Html.fromHtml(colorright));
                    userInput1.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (firstRanNumber > 9 && firstRanNumber <= 14 && !getUserInput1.contentEquals("bmw")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput1.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;


                } else if (firstRanNumber > 14 && firstRanNumber <= 19 && getUserInput1.equals("honda")) {
                    String colorright = "<font color=#49FB00>HONDA</font>";
                    userInput1.setText(Html.fromHtml(colorright));
                    userInput1.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (firstRanNumber > 14 && firstRanNumber <= 19 && !getUserInput1.contentEquals("honda")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput1.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;

                } else if (firstRanNumber > 19 && firstRanNumber <= 24 && getUserInput1.equals("kia")) {
                    String colorright = "<font color=#49FB00>KIA</font>";
                    userInput1.setText(Html.fromHtml(colorright));
                    userInput1.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (firstRanNumber > 19 && firstRanNumber <= 24 && !getUserInput1.contentEquals("kia")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput1.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;

                } else if (firstRanNumber > 24 && firstRanNumber <= 29 && getUserInput1.equals("volvo")) {
                    String colorright = "<font color=#49FB00>VOLVO</font>";
                    userInput1.setText(Html.fromHtml(colorright));
                    userInput1.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (firstRanNumber > 24 && firstRanNumber <= 29 && !getUserInput1.contentEquals("volvo")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput1.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;


                }

                if (secondRanNumber <= 4 && getUserInput2.equals("audi")) {
                    String colorright = "<font color=#49FB00>AUDI</font>";
                    userInput2.setText(Html.fromHtml(colorright));
                    userInput2.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (secondRanNumber <= 4 && !getUserInput2.contentEquals("audi")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput2.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;
                } else if (secondRanNumber > 4 && secondRanNumber <= 9 && getUserInput2.equals("skoda")) {
                    String colorright = "<font color=#49FB00>SKODA</font>";
                    userInput2.setText(Html.fromHtml(colorright));
                    userInput2.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (secondRanNumber > 4 && secondRanNumber <= 9 && !getUserInput2.contentEquals("skoda")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput2.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;


                } else if (secondRanNumber > 9 && secondRanNumber <= 14 && getUserInput2.equals("bmw")) {
                    String colorright = "<font color=#49FB00>BMW</font>";
                    userInput2.setText(Html.fromHtml(colorright));
                    userInput2.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (secondRanNumber > 9 && secondRanNumber <= 14 && !getUserInput2.contentEquals("bmw")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput2.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;


                } else if (secondRanNumber > 14 && secondRanNumber <= 19 && getUserInput2.equals("honda")) {
                    String colorright = "<font color=#49FB00>HONDA</font>";
                    userInput2.setText(Html.fromHtml(colorright));
                    userInput2.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (secondRanNumber > 14 && secondRanNumber <= 19 && !getUserInput2.contentEquals("honda")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput2.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;

                } else if (secondRanNumber > 19 && secondRanNumber <= 24 && getUserInput2.equals("kia")) {
                    String colorright = "<font color=#49FB00>KIA</font>";
                    userInput2.setText(Html.fromHtml(colorright));
                    userInput2.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (secondRanNumber > 19 && secondRanNumber <= 24 && !getUserInput2.contentEquals("kia")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput2.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;

                } else if (secondRanNumber > 24 && secondRanNumber <= 29 && getUserInput2.equals("volvo")) {
                    String colorright = "<font color=#49FB00>VOLVO</font>";
                    userInput2.setText(Html.fromHtml(colorright));
                    userInput2.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (secondRanNumber > 24 && secondRanNumber <= 29 && !getUserInput2.contentEquals("volvo")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput2.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;


                }


                if (thirdRanNumber <= 4 && getUserInput3.equals("audi")) {
                    String colorright = "<font color=#49FB00>AUDI</font>";
                    userInput3.setText(Html.fromHtml(colorright));
                    userInput3.setEnabled(false);
                    TotalCorrectionCount++;

                } else if (thirdRanNumber <= 4 && !getUserInput3.contentEquals("audi")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput3.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;
                } else if (thirdRanNumber > 4 && thirdRanNumber <= 9 && getUserInput3.equals("skoda")) {
                    String colorright = "<font color=#49FB00>SKODA</font>";
                    userInput3.setText(Html.fromHtml(colorright));
                    userInput3.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (thirdRanNumber > 4 && thirdRanNumber <= 9 && !getUserInput3.contentEquals("skoda")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput3.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;

                } else if (thirdRanNumber > 9 && thirdRanNumber <= 14 && getUserInput3.equals("bmw")) {
                    String colorright = "<font color=#49FB00>BMW</font>";
                    userInput3.setText(Html.fromHtml(colorright));
                    userInput3.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (thirdRanNumber > 9 && thirdRanNumber <= 14 && !getUserInput3.contentEquals("bmw")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput3.setText(Html.fromHtml(colorWrong));

                    totalAttempsCount++;

                } else if (thirdRanNumber > 14 && thirdRanNumber <= 19 && getUserInput3.equals("honda")) {
                    String colorright = "<font color=#49FB00>HONDA</font>";
                    userInput3.setText(Html.fromHtml(colorright));
                    userInput3.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (thirdRanNumber > 14 && thirdRanNumber <= 19 && !getUserInput3.contentEquals("honda")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput3.setText(Html.fromHtml(colorWrong));

                    totalAttempsCount++;


                } else if (thirdRanNumber > 19 && thirdRanNumber <= 24 && getUserInput3.equals("kia")) {
                    String colorright = "<font color=#49FB00>KIA</font>";
                    userInput3.setText(Html.fromHtml(colorright));
                    userInput3.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (thirdRanNumber > 19 && thirdRanNumber <= 24 && !getUserInput3.contentEquals("kia")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput3.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;

                } else if (thirdRanNumber > 24 && thirdRanNumber <= 29 && getUserInput3.equals("volvo")) {
                    String colorright = "<font color=#49FB00>VOLVO</font>";
                    userInput3.setText(Html.fromHtml(colorright));
                    userInput3.setEnabled(false);
                    TotalCorrectionCount++;
                } else if (thirdRanNumber > 24 && thirdRanNumber <= 29 && !getUserInput3.contentEquals("volvo")) {
                    String colorWrong = "<font color=#FF1212>wrong</font>";
                    userInput3.setText(Html.fromHtml(colorWrong));
                    totalAttempsCount++;

                }


                if (submitCount == 3) {

                    ButtonText(submit_btn);


                    if (firstRanNumber <= 4 && !getUserInput1.contentEquals("audi")) {
                        String colorWrong = "<font color=#FFE302>WRONG</font>";
                        userInput1.setText(Html.fromHtml(colorWrong));
                        correctAns1.setText("audi");
                        correctAns1.setTextColor(Color.YELLOW);

                    }
                    if (firstRanNumber > 4 && firstRanNumber <= 9 && !getUserInput1.contentEquals("skoda")) {
                        String colorWrong = "<font color=#FFE302>WRONG</font>";
                        userInput1.setText(Html.fromHtml(colorWrong));
                        correctAns1.setText("skoda");
                        correctAns1.setTextColor(Color.YELLOW);
                    }

                    if (firstRanNumber > 9 && firstRanNumber <= 14 && !getUserInput1.contentEquals("bmw")) {
                        String colorWrong = "<font color=#FFE302>WRONG</font>";
                        userInput1.setText(Html.fromHtml(colorWrong));
                        correctAns1.setText("bmw");
                        correctAns1.setTextColor(Color.YELLOW);
                    }
                    if (firstRanNumber > 14 && firstRanNumber <= 19 && !getUserInput1.contentEquals("honda")) {
                        String colorWrong = "<font color=#FFE302>WRONG</font>";
                        userInput1.setText(Html.fromHtml(colorWrong));
                        correctAns1.setText("honda");
                        correctAns1.setTextColor(Color.YELLOW);

                    }
                    if (firstRanNumber > 19 && firstRanNumber <= 24 && !getUserInput1.contentEquals("kia")) {
                        String colorWrong = "<font color=#FFE302>WRONG</font>";
                        userInput1.setText(Html.fromHtml(colorWrong));
                        correctAns1.setText("kia");
                        correctAns1.setTextColor(Color.YELLOW);
                    }

                    if (firstRanNumber > 24 && firstRanNumber <= 29 && !getUserInput1.contentEquals("volvo")) {
                        String colorWrong = "<font color=#FFE302>WRONG</font>";
                        userInput1.setText(Html.fromHtml(colorWrong));
                        correctAns1.setText("volvo");
                        correctAns1.setTextColor(Color.YELLOW);
                    }


                    if (secondRanNumber <= 4 && !getUserInput2.contentEquals("audi")) {
                        String colorWrong = "<font color=#FFE302>WRONG</font>";
                        userInput2.setText(Html.fromHtml(colorWrong));
                        correctAns2.setText("audi");
                        correctAns2.setTextColor(Color.YELLOW);

                    }
                    if (secondRanNumber > 4 && secondRanNumber <= 9 && !getUserInput2.contentEquals("skoda")) {
                        String colorWrong = "<font color=#FFE302>WRONG</font>";
                        userInput2.setText(Html.fromHtml(colorWrong));
                        correctAns2.setText("skoda");
                        correctAns2.setTextColor(Color.YELLOW);
                    }

                    if (secondRanNumber > 9 && secondRanNumber <= 14 && !getUserInput2.contentEquals("toyota")) {
                        String colorWrong = "<font color=#FFE302>WRONG</font>";
                        userInput2.setText(Html.fromHtml(colorWrong));
                        correctAns2.setText("toyota");
                        correctAns2.setTextColor(Color.YELLOW);
                    }
                    if (secondRanNumber > 14 && secondRanNumber <= 19 && !getUserInput2.contentEquals("honda")) {
                        String colorWrong = "<font color=#FFE302>WRONG</font>";
                        userInput2.setText(Html.fromHtml(colorWrong));
                        correctAns2.setText("honda");
                        correctAns2.setTextColor(Color.YELLOW);

                    }
                    if (secondRanNumber > 19 && secondRanNumber <= 24 && !getUserInput2.contentEquals("kia")) {
                        String colorWrong = "<font color=#FFE302>WRONG</font>";
                        userInput2.setText(Html.fromHtml(colorWrong));
                        correctAns2.setText("kia");
                        correctAns2.setTextColor(Color.YELLOW);
                    }

                    if (secondRanNumber > 24 && secondRanNumber <= 29 && !getUserInput2.contentEquals("volvo")) {
                        String colorWrong = "<font color=#FFE302>WRONG</font>";
                        userInput2.setText(Html.fromHtml(colorWrong));
                        correctAns2.setText("volvo");
                        correctAns2.setTextColor(Color.YELLOW);
                    }


                    if (thirdRanNumber <= 4 && !getUserInput3.contentEquals("audi")) {
                        String colorWrong = "<font color=#FF1212>WRONG</font>";
                        userInput3.setText(Html.fromHtml(colorWrong));
                        correctAns3.setText("audi");
                        correctAns3.setTextColor(Color.YELLOW);

                    }
                    if (thirdRanNumber > 4 && thirdRanNumber <= 9 && !getUserInput3.contentEquals("skoda")) {
                        String colorWrong = "<font color=#FF1212>WRONG</font>";
                        userInput3.setText(Html.fromHtml(colorWrong));
                        correctAns3.setText("skoda");
                        correctAns3.setTextColor(Color.YELLOW);
                    }

                    if (thirdRanNumber > 9 && thirdRanNumber <= 14 && !getUserInput3.contentEquals("bmw")) {
                        String colorWrong = "<font color=#FF1212>WRONG</font>";
                        userInput3.setText(Html.fromHtml(colorWrong));
                        correctAns3.setText("bmw");
                        correctAns3.setTextColor(Color.YELLOW);
                    }
                    if (thirdRanNumber > 14 && thirdRanNumber <= 19 && !getUserInput3.contentEquals("honda")) {
                        String colorWrong = "<font color=#FF1212>WRONG</font>";
                        userInput3.setText(Html.fromHtml(colorWrong));
                        correctAns3.setText("honda");
                        correctAns3.setTextColor(Color.YELLOW);

                    }
                    if (thirdRanNumber > 19 && thirdRanNumber <= 24 && !getUserInput3.contentEquals("kia")) {
                        String colorWrong = "<font color=#FF1212>WRONG</font>";
                        userInput3.setText(Html.fromHtml(colorWrong));
                        correctAns3.setText("kia");
                        correctAns3.setTextColor(Color.YELLOW);
                    }

                    if (thirdRanNumber > 24 && thirdRanNumber <= 29 && !getUserInput3.contentEquals("volvo")) {
                        String colorWrong = "<font color=#FF1212>WRONG</font>";
                        userInput3.setText(Html.fromHtml(colorWrong));
                        correctAns3.setText("volvo");
                        correctAns3.setTextColor(Color.YELLOW);
                    }


                }

//
//                if (submit_btn.getText().equals("NEXT")) {
//                    advanced();
//                }


            }


        });


    }

    public void ButtonText(View v) {//enable the submit button
        v.setEnabled(true);
        Button button = (Button) v;
        submit_btn.setText("NEXT");


    }


}


