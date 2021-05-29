package com.example.a07_activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    int nInterpark, nFacebook, nYoutube ,nNaver;

    final static int MOVE_TODAY_REQ = 100; //투데이 액티비티 리퀘스트 코드
    final static int MOVE_RECENT_REQ=101; //리센트 액티비티 리퀘스트 코드


    public void onClickInterpark(View v){

        //String str = getString(R.string.interpark_msg);
        //Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();

        Intent intentMtoR =new Intent(getApplicationContext(),RecentActivity.class);
        intentMtoR.putExtra("web","interpark"); //송신
        //startActivity(intentM); //액티비티 시작
        startActivityForResult(intentMtoR,MOVE_RECENT_REQ);
    }


    //처리기 공유
    public void onClickImg(View v){
        int nid =v.getId();

        switch (nid){
            case R.id.imageViewYoutube:
                Toast.makeText(getApplicationContext(),R.string.youtube_msg,Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageViewFacebook:
                Toast.makeText(getApplicationContext(),R.string.facebook_msg,Toast.LENGTH_SHORT).show();
                break;
            case R.id.imageViewNaver:
                //Toast.makeText(getApplicationContext(), R.string.naver_comic_msg,Toast.LENGTH_SHORT).show();
            {
                Intent intentM = new Intent(this,RecentActivity.class);
                intentM.putExtra("web","naver_comic"); //송신
                //startActivity(intentM);
                startActivityForResult(intentM,MOVE_RECENT_REQ);
            }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textInterpark = findViewById(R.id.textViewInterpark);
        TextView textFacebook = findViewById(R.id.textViewFacebook);
        TextView textYoutube = findViewById(R.id.textViewYoutube);
        TextView textNaver = findViewById(R.id.textViewNaver);

        TextView textTitle = findViewById(R.id.textViewTitle);


        textTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMtoT =new Intent(getApplicationContext(),TodayActivity.class);
                intentMtoT.putExtra("interpark",nInterpark);
                intentMtoT.putExtra("facebook",nFacebook);
                intentMtoT.putExtra("youtube",nYoutube);
                intentMtoT.putExtra("naver",nNaver);

                startActivityForResult(intentMtoT,MOVE_TODAY_REQ);
            }
        });


        textInterpark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.interpark.com"));
                nInterpark++;
                startActivity(intent);
            }
        });
        textFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
                nFacebook+=1;
                startActivity(intent);
            }
        });

        //암시적 인테늩
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.textViewYoutube) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"));
                    nYoutube=nYoutube+1;
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://comic.naver.com"));
                    nNaver++;
                    startActivity(intent);
                }
            }
        };
        textNaver.setOnClickListener(ocl);
        textYoutube.setOnClickListener(ocl);
    }

//여러곳에서 응답 올거라
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {



        if (requestCode == MOVE_TODAY_REQ) {

            if (resultCode == RESULT_OK) {

                String strResult = data.getStringExtra("result");
                Toast.makeText(getApplicationContext(), "오늘" + strResult, Toast.LENGTH_LONG).show();
            } else { //의미가 없는 경우
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == MOVE_RECENT_REQ){ //액티비티 확인
            if (resultCode==RESULT_OK){//결과 확인
                String strResult = data.getStringExtra("result");
                Toast.makeText(getApplicationContext(),strResult,Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(this,"취소 되었습니다.",Toast.LENGTH_LONG).show();
            }
        }
            super.onActivityResult(requestCode, resultCode, data);
    }
}