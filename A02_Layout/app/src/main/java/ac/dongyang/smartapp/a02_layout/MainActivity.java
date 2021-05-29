package ac.dongyang.smartapp.a02_layout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
   TextView mTextPatient , mTextNewPatient; //확진자 수 , 신규확진자 수
   TextView mTextDeath ,mTextNewDeath; //사망자 수 , 신규 사망자 수

    public void onClickTitle(View v){
        String sSymptom =getString(R.string.symptom);

        //Toast toast = Toast.makeText(getApplicationContext(),sSymptom, Toast.LENGTH_LONG);
        //toast.show();
        Toast.makeText(getApplicationContext(),sSymptom,Toast.LENGTH_LONG).show();
    }


    public void onClickDaily(View v){
        //버튼 누르면 신규확진자 토스트
        Toast toast = Toast.makeText(MainActivity.this,R.string.daily_info,Toast.LENGTH_SHORT);
        //액티비티별로 콘텍스트를 가지고 있음
        toast.setGravity(Gravity.CENTER, 0,0); //기준점 그대로 //void 를 반환해서 쇼()는 따로 씀
        toast.show();
    }


    public void onClickLayout(View v){
        //스낵바 *라이브러리 필요
        Snackbar.make(v,R.string.level,Snackbar.LENGTH_LONG).
                setAction("보기", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),R.string.level_info, Toast.LENGTH_SHORT).show();

                    }
                }).show();//스낵바 쇼
        //메이크()와 setAction() 모두 스낵바를 반환하여 한번에 가능

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //파인드 뷰 바이 아이디는 항상 레이아웃 인플레이션 밑에서!!
        mTextPatient = findViewById(R.id.textPatient_no);
        mTextNewPatient =findViewById(R.id.textPatient_diff);
        mTextDeath = findViewById(R.id.textDeath_no);
        mTextNewDeath = findViewById(R.id.textDeath_diff);

        Button btnWorld = findViewById(R.id.buttonWorld);

        btnWorld.setText(R.string.world);
        btnWorld.setTextSize(20.0f); //20sp
        btnWorld.setTypeface(btnWorld.getTypeface(), Typeface.BOLD);
        btnWorld.setTextColor(Color.WHITE);
        btnWorld.setGravity(Gravity.BOTTOM |Gravity.CENTER_HORIZONTAL); //메소드에 여럿 적용

/*3주차 내용
* 이미지 클릭시 증상 아럴트
* 바탕화면 누르면 일별통계 스낵바
* 국내 , 새계현황 따라 다른 정보 출력 */

//이벤트 처리기 등록,생성,구현 한번에
        btnWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //세계 현황
                String patient_no = getString(R.string.patientw_no);
                String patient_new = getString(R.string.patientw_diff);
                String death_no = getString(R.string.deathw_no);
                String death_new = getString(R.string.deathw_diff);

                mTextPatient.setText(patient_no);
                mTextNewPatient.setText(patient_new);
                mTextDeath.setText(death_no);
                mTextNewDeath.setText(death_new);
            }
        });

        //국내현황
        Button btnKorea =findViewById(R.id.buttonKorea);
        btnKorea.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mTextPatient.setText(R.string.patient_no);
                mTextNewPatient.setText(R.string.patient_diff);
                mTextDeath.setText(R.string.death_no);
                mTextNewDeath.setText(R.string.death_diff);
            }
        });

        //알러트 창
        ImageView imgLogo = findViewById(R.id.imageLogo);
        imgLogo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adBuilder = new AlertDialog.Builder(MainActivity.this);
                adBuilder.setTitle("증상");
                adBuilder.setMessage(R.string.symptom);
              adBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //매개변수(다이얼로그 , 눌려진버튼)
                        dialog.dismiss();
                    }
                });//생성동시에 등록
                adBuilder.show(); //create+show
}      });




    }

    @Override
    public void onBackPressed() {
        //콜백 메소드 부모에 설정을 주석처리해서
        //뒤로가기를 눌러도 꺼지지 않음

       //super.onBackPressed();
    }
}