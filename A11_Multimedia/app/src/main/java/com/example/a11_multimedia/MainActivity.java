package com.example.a11_multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton imgBtnPlay;
    RadioGroup rgSong;  //라디오 버튼 그룹
    MediaPlayer mp;

    public void onClickInfo(View v){
        Toast.makeText(getApplicationContext(), R.string.info,Toast.LENGTH_LONG).show();
    }

    public void onClickPlay(View v){
        if(mp !=null && mp.isPlaying()){
            //음악이 재생중일땐 스탑
            mp.stop();
            //이미지버튼의 이미지를 바꿈
            imgBtnPlay.setImageResource(android.R.drawable.ic_media_play);
            return;
        }
        //재생이 아닐땐 시작
        int nSong =rgSong.getCheckedRadioButtonId();
        if (nSong == R.id.radioButtonA)
                mp= MediaPlayer.create(getApplicationContext(),R.raw.a);
       else if (nSong == R.id.radioButtonC)
                mp= MediaPlayer.create(getApplicationContext(),R.raw.c); //선택  생성
       else
                mp= MediaPlayer.create(getApplicationContext(),R.raw.i);
                 imgBtnPlay.setImageResource(android.R.drawable.ic_media_pause);

       mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
           //컴플레이션 리스너가 없으면 재생이 끝나도 이미지가 바뀌지 않는다
           @Override
           public void onCompletion(MediaPlayer mp) {
               imgBtnPlay.setImageResource(android.R.drawable.ic_media_play);
               Toast.makeText(getApplicationContext(),"종료되었습니다",Toast.LENGTH_LONG).show();
           }
       });
       mp.start();
    }
//예고편 누르면
    public void onClickPreview(View v){
        Intent intenMtoP = new Intent(getApplicationContext(),PreviewActivity.class);
        startActivity(intenMtoP);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgBtnPlay =findViewById(R.id.imageButtonPlay);
        rgSong= findViewById(R.id.rgSong);
        //이미지뷰 클릭시 드라마 정보 토스트
    }

    @Override
    protected void onDestroy() {  //
        super.onDestroy();

        if(mp!=null){
            if(mp.isPlaying()==true){ //실행중이 면 파괴
                mp.stop();
                mp.release(); //자원해제
            }
        }
    }
}