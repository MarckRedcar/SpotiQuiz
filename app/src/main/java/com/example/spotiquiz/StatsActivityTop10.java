package com.example.spotiquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class StatsActivityTop10 extends AppCompatActivity {

    private ImageView artistPlot;
    private ImageView genrePlot;
    private ImageView songPlot;
    private ImageView decadePlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_top10);

        artistPlot = (ImageView)findViewById(R.id.top10_artist_plot);
        genrePlot = (ImageView)findViewById(R.id.top10_genre_plot);
        songPlot = (ImageView)findViewById(R.id.top10_song_plot);
        decadePlot = (ImageView)findViewById(R.id.top10_decade_plot);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();

        PyObject pyobj1 = py.getModule("top10_artist");
        PyObject pyobj2 = py.getModule("top10_genre");
        PyObject pyobj3 = py.getModule("top10_song");
        PyObject pyobj4 = py.getModule("top10_decade");
        PyObject obj1 = pyobj1.callAttr("plot");
        PyObject obj2 = pyobj2.callAttr("plot");
        PyObject obj3 = pyobj3.callAttr("plot");
        PyObject obj4 = pyobj4.callAttr("plot");

        String str1 = obj1.toString();
        String str2 = obj2.toString();
        String str3 = obj3.toString();
        String str4 = obj4.toString();

        byte data1[] = android.util.Base64.decode(str1, Base64.DEFAULT);
        byte data2[] = android.util.Base64.decode(str2, Base64.DEFAULT);
        byte data3[] = android.util.Base64.decode(str3, Base64.DEFAULT);
        byte data4[] = android.util.Base64.decode(str4, Base64.DEFAULT);

        Bitmap bmp1 = BitmapFactory.decodeByteArray(data1, 0, data1.length);
        Bitmap bmp2 = BitmapFactory.decodeByteArray(data2, 0, data2.length);
        Bitmap bmp3 = BitmapFactory.decodeByteArray(data3, 0, data3.length);
        Bitmap bmp4 = BitmapFactory.decodeByteArray(data4, 0, data4.length);

        artistPlot.setImageBitmap(bmp1);
        genrePlot.setImageBitmap(bmp2);
        songPlot.setImageBitmap(bmp3);
        decadePlot.setImageBitmap(bmp4);
    }
}