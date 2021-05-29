package com.example.a07_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ImageView img = findViewById(R.id.imageView);
        TextView text = findViewById(R.id.textView);

        Intent intentR = getIntent();
        if(intentR != null){
            int nImg = intentR.getIntExtra("img",0); //인텐트 데이터 추출
            String sText= intentR.getStringExtra("text");
            img.setImageResource(nImg); //받은 이미지 보여주기
            text.setText(sText);  //받은 텍스트 보여주기
        }


        Button button =findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                finish();
            }
        });

    }
}