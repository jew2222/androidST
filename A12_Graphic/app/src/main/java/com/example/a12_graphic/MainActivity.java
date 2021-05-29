package com.example.a12_graphic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //setContentView(R.layout.activity_main); //뷰로만든 자바 작동시
       BallShape ball = new BallShape(this);
        setContentView(ball); //화면에 보일 내용물 ,쉐입드러블 자바로 작동시
    }
}