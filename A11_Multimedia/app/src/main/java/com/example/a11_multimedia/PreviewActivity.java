package com.example.a11_multimedia;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class PreviewActivity extends AppCompatActivity {

    VideoView vv;

 public void onClickMain(View v){
     finish();
 }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);




        vv = findViewById(R.id.videoView);


        MediaController mc =new MediaController(this);
        //인자로 전체를 넣으면 오류
        vv.setMediaController(mc);
        vv.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.hospital)); //동영상 리소스 할당
        //패키지 이름 직접 쓰지않고 메소드로 가져왔다
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Toast.makeText(getApplicationContext(),"종료되었습니다.",Toast.LENGTH_LONG).show();
                }
            });//종료 이벤트처리기 등록

        vv.start(); //프리뷰 액티비티로 오자마자 플레이된다
    }
}