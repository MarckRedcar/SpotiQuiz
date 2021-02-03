package com.example.spotiquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.spotiquiz.GameActivity;
import com.example.spotiquiz.R;

public class MainActivity extends AppCompatActivity {
    private Button gameBtn;
    private Button statsBtn;
    private TextView infoTxt;

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

        infoTxt = (TextView) findViewById(R.id.info_txt);
        infoTxt.setPaintFlags(infoTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        infoTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setCancelable(true);
                builder.setTitle("Prova");
                builder.setMessage("prova del messaggio da inserire");

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        infoTxt.setVisibility(View.VISIBLE);
                    }
                });

                builder.show();
            }
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