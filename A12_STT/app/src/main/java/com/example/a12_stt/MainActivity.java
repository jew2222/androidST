package com.example.a12_stt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    ImageButton mBtton;
    Intent mIntent;
    SpeechRecognizer mSR;



   public void onClickDirect(View v){ //꼭 퍼블릭이여야 오류가 안납니다 (다른 패키지 클래스에서 호출 가능하도록)

       //음성인식 기능 액티비티로 이동 (암시적 인텐트:액티비티 명을 모르나 기능을 아는 다른.)
       Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

       i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName()); //com.example.a12_stt;도 되지만
       i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");
       i.putExtra(RecognizerIntent.EXTRA_PROMPT,"말을 해주세요...");

       try {
           startActivityForResult(i, 101); //폰에 음성인식이 설치가 안된 경우도 있을 수 있음
       }catch (Exception e){
           Toast.makeText(this,"STT 기능이 지원되지 않습니다.",Toast.LENGTH_LONG).show();
       }


   }


    void doStt(){  //백그라운드 처리용 메소드
        mIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        mIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        mSR = SpeechRecognizer.createSpeechRecognizer(this); //음성인식위한 객체 생성
        mSR.setRecognitionListener((new RecognitionListener() { //리스너 등록
            @Override
            public void onReadyForSpeech(Bundle params) {  //음성인식이 준비가 되었을때

                mTextView.setText("명령하세요!");

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {  //에러가 난 경우

                if(error==SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS)
                    mTextView.setText("권한이 없습니다.");
                else if (error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT)
                    mTextView.setText("시간이 초과되었습니다.");

                Toast.makeText(getApplicationContext()," 에러 : " + error , Toast.LENGTH_LONG).show();

            }

            @Override
            public void onResults(Bundle bundle) {

                ArrayList<String> sResults =  bundle.getStringArrayList( SpeechRecognizer.RESULTS_RECOGNITION );

                if(sResults.size() > 0){
                    String sResult = sResults.get(0);
                    mTextView.setText( sResult );
                    doSomething( sResult );
                }

            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        }));

        mBtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(" 명령 입력중.....");
                mSR.startListening(mIntent);
            }
        });

        //진행되는중의 이벤트 처리 위해 등록해야함

    } //

    void doSomething( String sResult ){

        if(sResult.contains("전화")) {//스트링 클래스 안의 콘테인, 포함을 묻는 메소드

            Intent intent = new Intent(Intent.ACTION_DIAL);

            startActivity(intent);
        }else if(sResult.contains("메시지")) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:")); //프로토콜임, 누구에게 일지는 정하지 않은
            startActivity(intent);
        }else
            Toast.makeText(this, "처리할 수 없습니다." ,Toast.LENGTH_LONG).show();


    } //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextView = findViewById(R.id.textView);
        mBtton = findViewById(R.id.imageButtonBackground);

        //사용자 설정 권한을 위한 코드
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            //인자  :z 컨텍스트,     권한의 종류

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 100); //여러 권한요청중 구별 위해
            return;
        } // 권한허용이 아닌경우 권한 요청
        doStt();
    }

    @Override //리퀘스트 권한 요청 결과를 받는 메소드
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode != 100)
            return;
        if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){ //하나를 요청해서 [0] 하나만 검사
            doStt();
        }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //데이터는 하나가 아니고 여럿이오고 , 스트링으로 옴
        super.onActivityResult(requestCode, resultCode, data);

        //인식된 음성 가져온

        if ( requestCode == 101 && resultCode == RESULT_OK){

            ArrayList<String> sttResults = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);  //리스트로 받고


            if(sttResults.size() > 0){
                String sttResult = sttResults.get(0); //하나를 떼어서
                mTextView.setText(sttResult);

                Toast.makeText(this, sttResult, Toast.LENGTH_LONG).show();

                doSomething(sttResult); // 호출
            }


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSR != null) {
             mSR.destroy();
        }
    }
}