package com.example.spotiquiz;

import android.animation.Animator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotiquiz.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    private TextView question;
    private LinearLayout optionsContainer;
    private Button nextBtn;
    private List<QuestionModel> list;
    private Animation scaleUp, scaleDown;
    private ProgressBar progBar;
    private TextView nQuestProg;
    private int count = 0;
    private int position = 0;
    private int score = 0;
    private int nQuest = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        question = findViewById(R.id.text_question);
        optionsContainer = findViewById(R.id.options_container);
        nextBtn = findViewById(R.id.next_button);
        progBar = findViewById(R.id.progress_bar);
        nQuestProg = findViewById(R.id.n_quest_progress);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        list = new ArrayList<>();
        list.add(new QuestionModel("\n" +
                "What is the most popular\nartist/band of all time?", "CJ", "Queen", "Elton John", "Pink Floyd", "CJ"));
        list.add(new QuestionModel("\nWhich of the following\ndecades is the most\npopular ever?", "1990s", "2010s", "1980s", "2000s", "2000s"));
        list.add(new QuestionModel("\nIn the 2010s\nwhich was the most\npopular artist?", "Regard", "Topic & A7S", "Trevor Daniel", "Ed Sheeran", "Topic & A7S"));
        list.add(new QuestionModel("\n" +
                "Which of the following\ngenres belongs\nto Sum 41?", "Pop", "Punk", "Rock", "Metal", "Punk"));
        list.add(new QuestionModel("\n" +
                "What is the most\npopular song\nof all time?", "DAKITI", "Whoopty", "drivers license", "WHITOUT YOU", "drivers license"));
        list.add(new QuestionModel("\nIn which year\nEd Sheeran released\nthe most songs?", "2017", "2011", "2014", "2019", "2017"));
        list.add(new QuestionModel("\nWhich artist\nwas the most popular\nin 1975?", "Pink Floyd", "Francesco De Gregori", "Mike Oldfield", "Hot Chocolate", "Hot Chocolate"));
        list.add(new QuestionModel("\n" +
                "Which of the following\ngenres belongs\nto Eminem?", "Pop", "Rap", "Rock", "Urban contemporary", "Rap"));
        list.add(new QuestionModel("\nWho is the most popular\npop artist?", "Tones And I", "Calum Scott", "Sam Fischer", "Alexander 23", "Tones And I"));
        list.add(new QuestionModel("\nWhat is the most popular\ngenre ever?", "Irish pop", "Afroswing", "Chinese eletropop", "Estonian pop", "Chinese eletropop"));

        for (int i = 0; i < 4; i++) {
            optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer((Button) v);
                }
            });
        }

        progBar.setMax(list.size());
        nQuestProg.setText(nQuest + "/" + list.size());
        playAnim(question, 0, list.get(position).getQuestion());

        //pressed next button animation
        nextBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    nextBtn.startAnimation(scaleUp);
                } else if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    nextBtn.startAnimation(scaleDown);
                }
                return false;
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                nextBtn.setEnabled(false);
                nextBtn.setAlpha(0);
                enableOption(true);
                position++;
                nQuest++;
                //score activity
                if (position == list.size()) {
                    nextBtn.setText("SCORE");
                    Intent scoreIntent = new Intent(GameActivity.this,ScoreActivity.class);
                    scoreIntent.putExtra("score", score);
                    scoreIntent.putExtra("total", list.size());
                    startActivity(scoreIntent);
                    finish();
                    return;
                }
                count = 0;
                progBar.setProgress(nQuest);
                nQuestProg.setText(nQuest + "/" + list.size());
                playAnim(question, 0, list.get(position).getQuestion());
            }
        });
    }
/*
    public boolean onKeyDown(int key_code, KeyEvent key_event) {
        if  (key_code == KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(key_code, key_event);
            return true;
        }
        return false;
    }*/

    //question change animation
    private void playAnim(View view, final int value, String data) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (value == 0 && count < 4) {
                    String option = "";
                    if (count == 0) {
                        option = list.get(position).getOptionA();
                    } else if (count == 1) {
                        option = list.get(position).getOptionB();
                    } else if (count == 2) {
                        option = list.get(position).getOptionC();
                    } else if (count == 3) {
                        option = list.get(position).getOptionD();
                    }
                    playAnim(optionsContainer.getChildAt(count), 0, option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (value == 0) {
                    try {
                        ((TextView)view).setText(data);
                    } catch (ClassCastException ex) {
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view, 1, data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    // Alert Dialog Exit Game
    @Override
    public void onBackPressed() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        TextView quitTxt = new TextView(this);
        quitTxt.setText("QUIT GAME\n\nAre you sure?");
        quitTxt.setGravity(Gravity.CENTER);
        quitTxt.setPadding(50, 30, 50, 30);
        quitTxt.setTextSize(18F);
        quitTxt.setTextColor(Color.WHITE);

        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

        builder.setCancelable(true);
        builder.setCustomTitle(quitTxt);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog quit = builder.create();
        quit.show();
        quit.getWindow().setLayout((int)(width*.6), (int)(height*.2));
        quit.getWindow().setBackgroundDrawableResource(R.drawable.info);

        Button btnPositive = quit.getButton(AlertDialog.BUTTON_POSITIVE);
        Button btnNegative = quit.getButton(AlertDialog.BUTTON_NEGATIVE);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
        layoutParams.weight = 10;
        btnPositive.setLayoutParams(layoutParams);
        btnNegative.setLayoutParams(layoutParams);
    }

    private void checkAnswer(Button selectedOption) {
        enableOption(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if (selectedOption.getText().toString().equals(list.get(position).getCorrectANS())) {
            //correct
            score++;
            selectedOption.setBackgroundResource(R.drawable.bottone_verde);
        } else {
            //incorrect
            selectedOption.setBackgroundResource(R.drawable.bottone_rosso);
            Button correctOption = (Button) optionsContainer.findViewWithTag(list.get(position).getCorrectANS());
            correctOption.setBackgroundResource(R.drawable.bottone_verde);
        }
    }

    private void enableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            optionsContainer.getChildAt(i).setEnabled(enable);
            if (enable) {
                optionsContainer.getChildAt(i).setBackgroundResource(R.drawable.bottone);
            }
        }
    }
}