package com.example.spotiquiz;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spotiquiz.R;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    //Button defBtn = new Button(this);
    private TextView question;
    private LinearLayout optionsContainer;
    private Button nextBtn;
    private int count = 0;
    private List<QuestionModel> list;
    private int position = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        question = findViewById(R.id.text_question);
        optionsContainer = findViewById(R.id.options_container);
        nextBtn = findViewById(R.id.next_button);

        list = new ArrayList<>();
        list.add(new QuestionModel("\nquestion 1", "a", "b", "c", "d", "a"));
        list.add(new QuestionModel("\nquestion 2", "a", "b", "c", "d", "d"));
        list.add(new QuestionModel("\nquestion 3", "a", "b", "c", "d", "b"));
        list.add(new QuestionModel("\nquestion 4", "a", "b", "c", "d", "a"));
        list.add(new QuestionModel("\nquestion 5", "a", "b", "c", "d", "c"));
        list.add(new QuestionModel("\nquestion 6", "a", "b", "c", "d", "a"));
        list.add(new QuestionModel("\nquestion 7", "a", "b", "c", "d", "d"));
        list.add(new QuestionModel("\nquestion 8", "a", "b", "c", "d", "b"));
        list.add(new QuestionModel("\nquestion 9", "a", "b", "c", "d", "c"));
        list.add(new QuestionModel("\nquestion 10", "a", "b", "c", "d", "c"));

        for (int i = 0; i < 4; i++) {
            optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer((Button) v);
                }
            });
        }

        playAnim(question, 0, list.get(position).getQuestion());
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBtn.setEnabled(false);
                nextBtn.setAlpha(0);
                enableOption(true);
                position++;
                if (position == list.size()) {
                    //score activity
                    return;
                }
                count = 0;
                playAnim(question, 0, list.get(position).getQuestion());
            }
        });
    }

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

    private void checkAnswer(Button selectedOption) {
        enableOption(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if (selectedOption.getText().toString().equals(list.get(position).getCorrectANS())) {
            //correct
            score++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        } else {
            //incorrect
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctOption = (Button) optionsContainer.findViewWithTag(list.get(position).getCorrectANS());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
        }
    }

    private void enableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            optionsContainer.getChildAt(i).setEnabled(enable);
            if (enable) {
                //optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
                //optionsContainer.getChildAt(i).setBackground(defBtn.getBackground());
                //optionsContainer.getChildAt(i).setBackgroundResource(android.R.drawable.btn_default);
                optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));
                //optionsContainer.getChildAt(i).getBackground().clearColorFilter();
                //optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));

            }
        }
    }
}