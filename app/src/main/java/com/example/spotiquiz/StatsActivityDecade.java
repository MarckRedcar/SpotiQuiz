package com.example.spotiquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class StatsActivityDecade extends AppCompatActivity {

    EditText decadeStr;
    Button viewBtn;
    ImageView decadePlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_decade);

        decadeStr = (EditText)findViewById(R.id.decade_str);
        viewBtn = (Button)findViewById(R.id.view_button);
        decadePlot = (ImageView)findViewById(R.id.decade_plot);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PyObject pyobj = py.getModule("decade");
                PyObject obj = pyobj.callAttr("plot", decadeStr.getText().toString());

                String str = obj.toString();

                byte data[] = android.util.Base64.decode(str, Base64.DEFAULT);

                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);

                decadePlot.setImageBitmap(bmp);

            }
        });
    }
}