package com.example.a07_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TodayActivity extends AppCompatActivity {
//투데이에는 왜 n변수가 없지? //인텐트 아래에 있음
    public void onClickGood(View v){  //굿을 누르면 뒤로가지며 굿 전송
        Intent intentTre = new Intent();
        intentTre.putExtra("result","굿!");
        setResult(RESULT_OK,intentTre); //응답받는 애 위해서
        finish(); //스타트 액티가 아닌 피니쉬
    }

    public void onClickNozam(View v){
        Intent intentTre = new Intent();
        intentTre.putExtra("result","노잼!");
        setResult(RESULT_OK,intentTre);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        TextView textInterpark,textFacebook,textYoutube, textNaver;

        textInterpark = findViewById(R.id.textViewInterpark);
        textFacebook = findViewById(R.id.textViewFacebook);
        textYoutube = findViewById(R.id.textViewYoutube);
        textNaver = findViewById(R.id.textViewNaver);

        Intent intentM = getIntent();
        if(intentM!=null){
           int nInterpark= intentM.getIntExtra("interpark",0);
            int nFacebook= intentM.getIntExtra("facebook",0);
            int nYoutube= intentM.getIntExtra("youtube",0);
            int nNaver = intentM.getIntExtra("naver",0);

            textInterpark.setText(nInterpark+"회 방문");
            textYoutube.setText(nYoutube+"회 방문");
            textFacebook.setText(nFacebook+"회 방문");
            textNaver.setText(nNaver+"회 방문");
        }
    }
}