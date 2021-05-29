package com.example.shd2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private ImageView cir1,cir2,cir3,cir4;
private int traffic =1;

//누르면 소스 다음으롷 바꾸기
public void onclick(View v) {
traffic++;

   if(traffic>3) traffic=1;

cir1.setImageResource(R.drawable.circle4);
    cir2.setImageResource(R.drawable.circle4);
    cir3.setImageResource(R.drawable.circle4);

    if (traffic==1){
        cir1.setImageResource(R.drawable.circle1);
    }
    else if (traffic==2){
        cir2.setImageResource(R.drawable.circle2);
    }
    else {
        cir3.setImageResource(R.drawable.circle3);
    }


    /*
    int a = v.getId();


    if (a == R.id.imageView1) {
        String str = getString(R.string.red);
        Toast.makeText(getApplicationContext(), R.string.red, Toast.LENGTH_SHORT).show();
    }
    else if (a == R.id.imageView2) {
        String str1 = getString(R.string.green);
        Toast.makeText(getApplicationContext(), str1, Toast.LENGTH_SHORT).show();
    }
    else if (a == R.id.imageView3) {
        String str2 = getString(R.string.sblue);
        Toast.makeText(getApplicationContext(), str2, Toast.LENGTH_SHORT).show();
    }*/
}


  //d온 크리에잇서 뷰탖ㅂ기 뷰 찾기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cir1= findViewById(R.id.imageView1);
        cir2= findViewById(R.id.imageView2);
        cir3= findViewById(R.id.imageView3);


    }
}