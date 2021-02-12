package com.example.spotiquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class StatsActivity extends AppCompatActivity {
    private Button artistBtn;
    private Button genreBtn;
    private Button decadeBtn;
    private Button top10Btn;
    private Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        artistBtn = findViewById(R.id.artist_button);
        genreBtn = findViewById(R.id.genre_button);
        decadeBtn = findViewById(R.id.decade_button);
        top10Btn = findViewById(R.id.top10_button);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        //pressed artist button animation
        artistBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    artistBtn.startAnimation(scaleUp);
                } else if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    artistBtn.startAnimation(scaleDown);
                }
                return false;
            }
        });

        //pressed genre button animation
        genreBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    genreBtn.startAnimation(scaleUp);
                } else if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    genreBtn.startAnimation(scaleDown);
                }
                return false;
            }
        });

        //pressed decade button animation
        decadeBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    decadeBtn.startAnimation(scaleUp);
                } else if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    decadeBtn.startAnimation(scaleDown);
                }
                return false;
            }
        });

        //pressed top 10 button animation
        top10Btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    top10Btn.startAnimation(scaleUp);
                } else if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    top10Btn.startAnimation(scaleDown);
                }
                return false;
            }
        });

        artistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStatsActivityArtist();
            }
        });

        genreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStatsActivityGenre();
            }
        });

        decadeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStatsActivityDecade();
            }
        });

        top10Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openStatsActivityTop10();
            }
        });
    }

    public void openStatsActivityArtist() {
        Intent intent = new Intent(this, StatsActivityArtist.class);
        startActivity(intent);
    }

    public void openStatsActivityGenre() {
        Intent intent = new Intent(this, StatsActivityGenre.class);
        startActivity(intent);
    }

    public void openStatsActivityDecade() {
        Intent intent = new Intent(this, StatsActivityDecade.class);
        startActivity(intent);
    }

    public void openStatsActivityTop10() {
        Intent intent = new Intent(this, StatsActivityTop10.class);
        startActivity(intent);
    }
}