package com.example.spotiquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.spotiquiz.GameActivity;
import com.example.spotiquiz.R;

public class MainActivity extends AppCompatActivity {
    private Button gameBtn;
    private Button statsBtn;
    private TextView infoTxt;
    private Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBtn = (Button) findViewById(R.id.game_button);
        statsBtn = (Button) findViewById(R.id.stats_button);
        infoTxt = (TextView) findViewById(R.id.info_txt);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        //pressed game button animation
        gameBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    gameBtn.startAnimation(scaleUp);
                } else if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    gameBtn.startAnimation(scaleDown);
                }
                return false;
            }
        });

        //pressed trivia button animation
        statsBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    statsBtn.startAnimation(scaleUp);
                } else if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    statsBtn.startAnimation(scaleDown);
                }
                return false;
            }
        });

        gameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGameActivity();
            }
        });

        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openStatsActivity(); }
        });

        infoTxt.setPaintFlags(infoTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        infoTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { showInfo(); }
        });
    }

    public void openGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void openStatsActivity() {
        Intent intent = new Intent(this, StatsActivity.class);
        startActivity(intent);
    }

    void showInfo() {

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        TextView messageTxt = new TextView(this);
        messageTxt.setText("SpotiQuiz is an App which was built for a university project." +
                "\nIn GAME there are 10 multiple choice questions." +
                "\nIn TRIVIA there are the solutions for the " +
                "questions in GAME mode.");
        messageTxt.setPadding(50, 20, 50, 30);
        messageTxt.setTextSize(18F);
        messageTxt.setTextColor(Color.WHITE);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setCancelable(true);
        builder.setCustomTitle(messageTxt);

        AlertDialog info = builder.create();
        info.show();
        info.getWindow().setLayout((int)(width*.9), (int)(height*.2));
        info.getWindow().setBackgroundDrawableResource(R.drawable.info);
    }

}