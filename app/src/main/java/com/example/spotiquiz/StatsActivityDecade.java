package com.example.spotiquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class StatsActivityDecade extends AppCompatActivity {

    private EditText decadeStr;
    private Button viewBtn;
    private ImageView decadePlot;
    private Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_decade);

        decadeStr = (EditText)findViewById(R.id.decade_str);
        viewBtn = (Button)findViewById(R.id.view_button);
        decadePlot = (ImageView)findViewById(R.id.decade_plot);

        scaleUp = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this, R.anim.scale_down);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();

        //pressed view button animation
        viewBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    viewBtn.startAnimation(scaleUp);
                } else if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    viewBtn.startAnimation(scaleDown);
                }
                return false;
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PyObject pyobj = py.getModule("decade");
                PyObject obj = pyobj.callAttr("decade", decadeStr.getText().toString()).callAttr("plot");

                String str = obj.toString();

                byte data[] = android.util.Base64.decode(str, Base64.DEFAULT);

                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                decadePlot.setImageBitmap(bmp);

            }
        });
    }
}