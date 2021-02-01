package com.example.spotiquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    private Button homeBtn;
    //private LinearLayout scoreLayout;
    private int score;
    private ImageView levelAvatar;
    private TextView levelScore;
    //private List<LevelModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        homeBtn = findViewById(R.id.home_button);
        levelAvatar = findViewById(R.id.level_avatar);
        levelScore = findViewById(R.id.level_score);
        //scoreLayout = findViewById(R.id.score_layout);

        checkScoreLayout((int) score, (ImageView) levelAvatar, (TextView) levelScore);
        //list = new ArrayList<>();
        //list.add(new LevelModel(scoreLayout == ""));

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();;
            }
        });
    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void checkScoreLayout(int score, ImageView levelAvatar, TextView levelScore) {
        //n = 0;
        score = 9;
        if (score < 4) {
            levelAvatar.setBackgroundResource(R.drawable.rookie);
            levelScore.setText("ROOKIE");
        } else if (score > 3 && score < 7){
            levelAvatar.setBackgroundResource(R.drawable.veteran);
            levelScore.setText("VETERAN");
        } else if (score > 6 && score < 10){
            levelAvatar.setBackgroundResource(R.drawable.master);
            levelScore.setText("MASTER");
        } else {
            levelAvatar.setBackgroundResource(R.drawable.veteran);
            levelScore.setText("LEGEND");
        }


    }
}