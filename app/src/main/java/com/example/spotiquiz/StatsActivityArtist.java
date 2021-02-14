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

import static android.util.Base64.*;

public class StatsActivityArtist extends AppCompatActivity {

    EditText artistStr;
    Button viewBtn;
    ImageView artistPlot, artistPie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_artist);

        artistStr = (EditText)findViewById(R.id.artist_str);
        viewBtn = (Button)findViewById(R.id.view_button);
        artistPlot = (ImageView)findViewById(R.id.artist_plot);
        artistPie = (ImageView)findViewById(R.id.artist_pie);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PyObject pyobj1 = py.getModule("artistPlot");
                PyObject pyobj2 = py.getModule("artistPie");
                PyObject obj1 = pyobj1.callAttr("plot", artistStr.getText().toString());
                PyObject obj2 = pyobj2.callAttr("pie", artistStr.getText().toString());

                String str1 = obj1.toString();
                String str2 = obj2.toString();

                byte data1[] = android.util.Base64.decode(str1, Base64.DEFAULT);
                byte data2[] = android.util.Base64.decode(str2, Base64.DEFAULT);

                Bitmap bmp1 = BitmapFactory.decodeByteArray(data1, 0, data1.length);
                Bitmap bmp2 = BitmapFactory.decodeByteArray(data2, 0, data2.length);

                artistPlot.setImageBitmap(bmp1);
                artistPie.setImageBitmap(bmp2);

            }
        });
    }
}