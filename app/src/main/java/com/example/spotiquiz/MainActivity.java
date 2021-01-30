package com.example.spotiquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.spotiquiz.GameActivity;
import com.example.spotiquiz.R;

public class MainActivity extends AppCompatActivity {
    private Button gameBtn;
    private Button statsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBtn = (Button) findViewById(R.id.game_button);
        gameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGameActivity();
            }
        });

        statsBtn = (Button) findViewById(R.id.stats_button);
        statsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openStatsActivity(); }
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
}