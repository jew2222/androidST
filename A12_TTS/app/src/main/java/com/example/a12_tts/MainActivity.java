package com.example.a12_tts;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech mTTs;
    EditText mEditText;
    Button mBtnStart;
    float mTone = 1.0f;
    float mSpeed = 1.0f;


        public void onCLickStart(View v) {

            String sNotify = mEditText.getText().toString().trim(); //에딧 읽어서

            if(sNotify.isEmpty()) {
                Toast.makeText(this,"내용이 없습니다",Toast.LENGTH_LONG).show();
                return;
            }

            //음성이 나오고 있지 않는경우에만 스픽 호출
            if( !mTTs.isSpeaking() ){
                mTTs.speak(sNotify, TextToSpeech.QUEUE_FLUSH, null, "DMUTTS_ID"); //인자: 데이터 , 중지하고할지 말 다하고할지 여부, 추가 기능 , 음성아이디
            }else //음성이 나오고 있던 경우 ,중지
                mTTs.stop();


    }

    public void onCLickTup(View v) { //업 버튼
        if(mTone <2.5f)  //2.5를 최대로
            mTone +=0.5f;

        mTTs.setPitch(mTone);

    }

    public void onCLickTdown(View v) {
        if(mTone > 0.5f)
            mTone -= 0.5f; //최소

        mTTs.setPitch(mTone);
    }

    public void onCLickSup(View v) {

        if(mSpeed <2.5f)
            mSpeed +=0.5f;

        mTTs.setSpeechRate(mSpeed);
    }

    public void onCLickSdown(View v) {
        if(mSpeed > 0.5f)
            mSpeed -=0.5f;

        mTTs.setSpeechRate(mSpeed);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStart = findViewById(R.id.buttonStart);
        mEditText = findViewById(R.id.editTextNotify);

        mTTs = new TextToSpeech(this, new TextToSpeech.OnInitListener() { // 생성, 이벤트 처리기 등록 역할
            @Override
            public void onInit(int status) {

                if (status == TextToSpeech.ERROR) {
                    return;
                }

                mTTs.setLanguage(Locale.KOREAN);
                mTTs.setPitch(mTone);
                mTTs.setSpeechRate(mSpeed);

                Voice voice = new Voice("ko-kr-x-ism#male_2-local", Locale.KOREA, Voice.QUALITY_HIGH, Voice.LATENCY_NORMAL, false, null);
                //음성명, 퀄리티, 네트웍 레턴시, 네크웍 사용여부,나머지 기능
                mTTs.setVoice(voice);

                //변환 과정상의 이벤트 등록
                mTTs.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                    @Override
                    public void onStart(String utteranceId) { //소리 시작할때

                        if(utteranceId.equals("DMUTTS_ID")) //스픽메서드 에서 전달한 아이디
                        mBtnStart.setText("방송중지");

                    }

                    @Override
                    public void onDone(String utteranceId) { //소리 끝났을때
                        if(utteranceId.equals("DMUTTS_ID"))
                            mBtnStart.setText("방송시작");

                    }

                    @Override
                    public void onStop(String utteranceId, boolean interrupted) {
                        if(utteranceId.equals("DMUTTS_ID"))
                            mBtnStart.setText("방송시작");


                    }

                    @Override
                    public void onError(String utteranceId) {
                        if(utteranceId.equals("DMUTTS_ID"))
                            Toast.makeText(getApplicationContext(), "에러발생",Toast.LENGTH_LONG).show();

                    }

                });
            }


        });
    }

}
