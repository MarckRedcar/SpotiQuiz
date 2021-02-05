package com.example.spotiquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    private Button homeBtn;
    private TextView scored, total;
    private ImageView levelAvatar;
    private TextView levelScore;
    private float perc_score;
    private Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        levelAvatar = findViewById(R.id.level_avatar);
        levelScore = findViewById(R.id.level_score);
        scored = findViewById(R.id.scored);
        total = findViewById(R.id.total);
        homeBtn = findViewById(R.id.home_button);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        scored.setText(String.valueOf(getIntent().getIntExtra("score", 0)));
        total.setText(String.valueOf(getIntent().getIntExtra("total", 0)));

        checkScoreLayout((float) perc_score, (ImageView) levelAvatar, (TextView) levelScore);

        homeBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    homeBtn.startAnimation(scaleUp);
                } else if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    homeBtn.startAnimation(scaleDown);
                }
                return false;
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                //openMainActivity();
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void checkScoreLayout(float perc_score, ImageView levelAvatar, TextView levelScore) {
        String scoredString = scored.getText().toString();
        String totalString = total.getText().toString();
        int score = Integer.parseInt(scoredString);
        int tot = Integer.parseInt(totalString);
        perc_score = (score * 100) / tot;
        if (perc_score <= 30) {
            levelAvatar.setBackgroundResource(R.drawable.rookie);
            levelScore.setText("ROOKIE");
        } else if (perc_score > 30 && perc_score <= 60){
            levelAvatar.setBackgroundResource(R.drawable.veteran);
            levelScore.setText("VETERAN");
        } else if (perc_score > 60 && perc_score < 100){
            levelAvatar.setBackgroundResource(R.drawable.master);
            levelScore.setText("MASTER");
        } else {
            levelAvatar.setBackgroundResource(R.drawable.legend);
            levelScore.setText("LEGEND");
        }
    }

    @Override
    public void onBackPressed() { }
}