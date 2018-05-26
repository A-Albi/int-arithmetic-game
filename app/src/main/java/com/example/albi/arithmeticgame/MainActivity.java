package com.example.albi.arithmeticgame;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public String[] arithmeticSymbols = null;
    public CountDownTimer CDT = null;
    public int guesses_Total = 0;
    public int score_Total = 0;
    public int minutes_left = 0;
    public int seconds_left = 0;
    public int time_left = 0;
    public int num_1 = 0;
    public int num_2 = 0;
    public int arithmeticOps = 0;
    public Random random = null;
    public TextView[] textViews = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout RL1 = findViewById(R.id.menuLayout);
        RelativeLayout RL2 = findViewById(R.id.gameLayout);
        RL1.setVisibility(View.VISIBLE);
        RL2.setVisibility(View.INVISIBLE);
        random = new Random();
        arithmeticOps = random.nextInt(4);
        textViews = new TextView[]{
                findViewById(R.id.choice1),
                findViewById(R.id.choice2),
                findViewById(R.id.choice3),
                findViewById(R.id.choice4)
        };

        CDT = new CountDownTimer(3 * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                update(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                TextView tv = findViewById(R.id.textView2);
                Button bv = findViewById(R.id.play_button);
                tv.setText(("Your score is: " + Integer.toString(score_Total) + " out of "
                        + Integer.toString(guesses_Total)));
                bv.setText(("Play again!"));
                RelativeLayout RL1 = findViewById(R.id.menuLayout);
                RelativeLayout RL2 = findViewById(R.id.gameLayout);
                RL1.setVisibility(View.VISIBLE);
                RL2.setVisibility(View.INVISIBLE);
            }
        };

        arithmeticSymbols = new String[]{" + ", " - ", " ÷ ", " ⋅ "};

        TextView score = findViewById(R.id.score);
        score.setText(("0 out of 0"));

    }

    public void update(long millisUntilFinished) {
        this.time_left = (int) millisUntilFinished / 1000;
        this.minutes_left = this.time_left / 60;
        this.seconds_left = this.time_left % 60;
        TextView editText1 = findViewById(R.id.time_left);
        editText1.setText(
                this.time_left != 0 ? (
                        Integer.toString(this.minutes_left) + ":" +
                                ((this.seconds_left < 10) ?
                                        ("0" + Integer.toString(this.seconds_left)) :
                                        Integer.toString(this.seconds_left))
                ) : "Time's up!"
        );
    }

    public void choice (View view) {
        int value = Integer.parseInt(((TextView) view).getText().toString());
        int value_test = 0;
        switch(arithmeticOps) {
            case(0):
                value_test = num_1 + num_2;
                break;
            case(1):
                value_test = num_1 - num_2;
                break;
            case(2):
                value_test = num_1 / num_2;
                break;
            case(3):
                value_test = num_1 * num_2;
                break;
        }
        if (value == value_test) score_Total++;
        guesses_Total++;
        update_score();
        resetData();
    }

    public void update_score() {
        TextView score = findViewById(R.id.score);
        score.setText((Integer.toString(this.score_Total)+"/"+Integer.toString(this.guesses_Total)));
    }

    public void resetData() {
        Random rand = new Random();
        num_1 = rand.nextInt(100);
        num_2 = rand.nextInt(100);
        int result = 0;
        while (num_2 == 0) num_2 = rand.nextInt();
        arithmeticOps = rand.nextInt(4);
        switch (arithmeticOps) {
            case(0):
                result = num_1 + num_2;
                break;
            case(1):
                result = num_1 - num_2;
                break;
            case(2):
                result = num_1 / num_2;
                break;
            case(3):
                result = num_1 * num_2;
                break;
        }

        int square = rand.nextInt(4);
        for (int i = 0; i < 4; i++)
            if (i == square) textViews[i].setText((Integer.toString(result)));
            else textViews[i].setText((Integer.toString(random.nextInt(100))));
        TextView textView = findViewById(R.id.textView);
        textView.setText((Integer.toString(num_1) + arithmeticSymbols[arithmeticOps] +
        Integer.toString(num_2) + " = ?"));
    }

    public void play_button(View view) {
        RelativeLayout RL1 = findViewById(R.id.menuLayout);
        RelativeLayout RL2 = findViewById(R.id.gameLayout);
        RL1.setVisibility(View.INVISIBLE);
        RL2.setVisibility(View.VISIBLE);
        CDT.start();
        resetData();
    }
}
