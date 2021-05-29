package com.example.imgvt;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView i1,i2,i3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ImageView  i1= findViewById(R.id.imageView1);
        ImageView  i2= findViewById(R.id.imageView2);
        ImageView  i3= findViewById(R.id.imageView3);


        i1.setScaleType(ImageView.ScaleType.MATRIX);


        android.graphics.Matrix mtx  =  new android.graphics.Matrix();
            mtx.setScale(4.0f,4.0f);

            i1.setImageMatrix(mtx);

        mtx.setRotate(45.0f);
        i2.setImageMatrix(mtx);




    }
}