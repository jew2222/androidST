package com.example.a04_imageview;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView img1,img2,img3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img1 = findViewById(R.id.imageView1);
        img2 = findViewById(R.id.imageView2); //가운데 꽃병
        img3 = findViewById(R.id.imageView3); //오른쪽 침대

        img2.setImageResource(R.drawable.gogh5); //침대사진
        img3.setImageResource(R.drawable.gogh4); //뷰의 이미지를 꽃병 사진

        img1.setScaleType(ImageView.ScaleType.MATRIX);
        img2.setScaleType(ImageView.ScaleType.MATRIX);
        img3.setScaleType(ImageView.ScaleType.MATRIX);


        Matrix mtx  =  new Matrix();

        mtx.setScale(0.5f,0.5f); //0.5배 축소
        img1.setImageMatrix(mtx);

        Matrix mtx2  =  new Matrix();
        mtx2.setRotate(45.0f); //45도 회전
        img2.setImageMatrix(mtx2);

        Matrix mtx3  =  new Matrix();
        mtx3.setTranslate(50.0f,50.0f); //50만큼 이동
        img3.setImageMatrix(mtx3);
    }
}