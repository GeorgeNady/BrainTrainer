package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    TextView tvScore;
    TextView tvTimer;
    TextView tvQuestion;
    TextView tvResultMessage;
    androidx.gridlayout.widget.GridLayout gridLayout;
    Random rand;
    int a;
    int b;
    ArrayList<Integer> answers = new ArrayList<>();
    int locationOfCorrectAnswer;
    TextView choose0;
    TextView choose1;
    TextView choose2;
    TextView choose3;
    int score = 0;
    int numberOfQuestion = 0;
    int secondsRemaining = 30;

    public void go(View view) {

        tvResultMessage.setText("");
        tvScore.setText("0/0");
        score = 0;
        numberOfQuestion = 0;
        btnStart.setVisibility(View.GONE);
        tvScore.setVisibility(View.VISIBLE);
        tvTimer.setVisibility(View.VISIBLE);
        tvQuestion.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);

        CountDownTimer countDownTimer = new CountDownTimer(30100,1000) {
            @Override
            public void onTick(long l) {
                secondsRemaining--;
                tvTimer.setText(MessageFormat.format("{0}s", secondsRemaining));
            }

            @Override
            public void onFinish() {
                tvScore.setVisibility(View.GONE);
                tvTimer.setVisibility(View.GONE);
                tvQuestion.setVisibility(View.GONE);
                gridLayout.setVisibility(View.GONE);
                if (score >= (numberOfQuestion/2)){
                    tvResultMessage.setText(MessageFormat.format("your score is : {0}\nyou Winner!", tvScore.getText()));
                    MediaPlayer.create(MainActivity.this,R.raw.claping).start();
                } else {
                    tvResultMessage.setText(MessageFormat.format("your score is : {0}\nLoser!", tvScore.getText()));
                    MediaPlayer.create(MainActivity.this,R.raw.glass_breaking).start();
                }
                secondsRemaining = 30;
                tvResultMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btnStart.setVisibility(View.VISIBLE);
                        tvResultMessage.setVisibility(View.GONE);
                    }
                });
            }
        };

        countDownTimer.start();

    }

    public void chooseAnswer(View view) {

        tvResultMessage.setVisibility(View.VISIBLE);

        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())){
            tvResultMessage.setText(R.string.correct);
            score++;
        } else {
            tvResultMessage.setText(R.string.wrong);
        }
        numberOfQuestion++;
        tvScore.setText(MessageFormat.format("{0}/{1}", score, numberOfQuestion));

        generateQuestion();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btnStart);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);
        gridLayout = (androidx.gridlayout.widget.GridLayout) findViewById(R.id.gridLayout);
        tvResultMessage = (TextView) findViewById(R.id.tvResultMessage);
        choose0 = (TextView) findViewById(R.id.choose0);
        choose1 = (TextView) findViewById(R.id.choose1);
        choose2 = (TextView) findViewById(R.id.choose2);
        choose3 = (TextView) findViewById(R.id.choose3);

        generateQuestion();

    }

    @SuppressLint("SetTextI18n")
    private void generateQuestion() {

        rand = new Random();

        a = rand.nextInt(21);
        b = rand.nextInt(21);

        tvQuestion.setText(a + " + " + b);

        locationOfCorrectAnswer = rand.nextInt(4);

        answers.clear();

        for (int i = 0; i<4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(a+b);
            } else {
                int wrongAnswer = rand.nextInt(41);
                while (wrongAnswer == (a+b)) {
                    wrongAnswer = rand.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }

        choose0.setText(String.valueOf(answers.get(0)));
        choose1.setText(String.valueOf(answers.get(1)));
        choose2.setText(String.valueOf(answers.get(2)));
        choose3.setText(String.valueOf(answers.get(3)));

    }

}