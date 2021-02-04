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
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spotiquiz.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    //Button defBtn = new Button(this);
    private TextView question;
    private LinearLayout optionsContainer;
    private Button nextBtn;
    private int count = 0;
    private List<QuestionModel> list;
    private int position = 0;
    private int score = 0;
    private Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        question = findViewById(R.id.text_question);
        optionsContainer = findViewById(R.id.options_container);
        nextBtn = findViewById(R.id.next_button);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        list = new ArrayList<>();
        list.add(new QuestionModel("\nquestion 1", "a", "b", "c", "d", "a"));
        list.add(new QuestionModel("\nquestion 2", "a", "b", "c", "d", "d"));
        list.add(new QuestionModel("\nquestion 3", "a", "b", "c", "d", "b"));
        list.add(new QuestionModel("\nquestion 4", "a", "b", "c", "d", "a"));
        //list.add(new QuestionModel("\nquestion 5", "a", "b", "c", "d", "c"));
        //list.add(new QuestionModel("\nquestion 6", "a", "b", "c", "d", "a"));
        //list.add(new QuestionModel("\nquestion 7", "a", "b", "c", "d", "d"));
        //list.add(new QuestionModel("\nquestion 8", "a", "b", "c", "d", "b"));
        //list.add(new QuestionModel("\nquestion 9", "a", "b", "c", "d", "c"));
        //list.add(new QuestionModel("\nquestion 10", "a", "b", "c", "d", "c"));

        for (int i = 0; i < 4; i++) {
            optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer((Button) v);
                }
            });
        }

        playAnim(question, 0, list.get(position).getQuestion());
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
                //score activity
                if (position == list.size()) {
                    Intent scoreIntent = new Intent(GameActivity.this,ScoreActivity.class);
                    scoreIntent.putExtra("score", score);
                    scoreIntent.putExtra("total", list.size());
                    startActivity(scoreIntent);
                    return;
                }
                count = 0;
                playAnim(question, 0, list.get(position).getQuestion());
            }
        });
    }

    public boolean onKeyDown(int key_code, KeyEvent key_event) {
        if  (key_code == KeyEvent.KEYCODE_BACK) {
            super.onKeyDown(key_code, key_event);
            return true;
        }
        return false;
    }

    /*
    public void onBackPressed() {

    }
    */
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

    @Override
    public void onBackPressed() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        TextView quitTxt = new TextView(this);
        quitTxt.setText("QUIT GAME\nAre you sure?");
        quitTxt.setGravity(Gravity.CENTER);
        quitTxt.setPadding(50, 20, 50, 30);
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
        quit.getWindow().setLayout((int)(width*.9), (int)(height*.2));
        quit.getWindow().setBackgroundDrawableResource(R.drawable.info);
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