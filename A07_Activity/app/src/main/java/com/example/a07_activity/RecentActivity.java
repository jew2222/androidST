package com.example.a07_activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecentActivity extends AppCompatActivity {

    private ImageView imgView1,imgView2,imgView3;
    private TextView textTitle, textView1,textView2, textView3;
    private String web;//메인 인텐트가 보낸 스트링데이터,뭘 눌렀는지

    public void onClickHome(View v){
        Intent intentRre = new Intent(); //응답 보내기
        intentRre.putExtra("result",web);
        setResult(RESULT_OK,intentRre);
        finish(); //이전 액티비티로

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        imgView1 = findViewById(R.id.imageView1);
        imgView2 = findViewById(R.id.imageView2);
        imgView3 = findViewById(R.id.imageView3);
        textTitle = findViewById(R.id.textViewTitle);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);


       //D인텐트 처리 출력
        Intent intentM = getIntent();
        if (intentM != null) {
          web = intentM.getStringExtra("web");
          //메인 인텐트가 보낸 스트링데이터

            switch(web) {
                case "interpark":
                    textTitle.setText(R.string.title_interpark);
                    imgView1.setImageResource(R.drawable.img1_1);
                    imgView2.setImageResource(R.drawable.img1_2);
                    imgView3.setImageResource(R.drawable.img1_3);
                    textView1.setText(R.string.img1_1);
                    textView2.setText(R.string.img1_2);
                    textView3.setText(R.string.img1_3);
                    break;


                case "naver_comic":
                    textTitle.setText(R.string.title_naver);
                    imgView1.setImageResource(R.drawable.img4_1);
                    imgView2.setImageResource(R.drawable.img4_2);
                    imgView3.setImageResource(R.drawable.img4_3);
                    textView1.setText(R.string.img2_1);
                    textView2.setText(R.string.img2_2);
                    textView3.setText(R.string.img2_3);
                    break;

                default:return;
            }
        }

          //클릭이벤트 처리에 따른 인텐트 생성 , 화면 전환  (액티비티가 같아서 이렇게 각뷰마다 두갈래로 설정)
        //이미 화면 출력상태지만 시스템은 모르니까

        textView1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intentRtoI = new Intent(getApplicationContext(),InfoActivity.class);
                //명시적 인텐트

                if(web.equals("interpark")){
                    intentRtoI.putExtra("img",R.drawable.img1_1); //아이디 상태
                    intentRtoI.putExtra("text",getString(R.string.img1_1)); //문자형태
                } else { //네이버인 경우
                    intentRtoI.putExtra("img",R.drawable.img4_1);
                    intentRtoI.putExtra("text",getString(R.string.img2_1));
                }

                startActivity(intentRtoI);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intentRtoI = new Intent(getApplicationContext(),InfoActivity.class);
                //명시적 인텐트

                if(web.equals("interpark")){
                    intentRtoI.putExtra("img",R.drawable.img1_2);
                    intentRtoI.putExtra("text",getString(R.string.img1_2));
                } else { //네이버인 경우
                    intentRtoI.putExtra("img",R.drawable.img4_2);
                    intentRtoI.putExtra("text",getString(R.string.img2_2));
                }

                startActivity(intentRtoI);
            }
        });

        textView3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intentRtoI = new Intent(getApplicationContext(),InfoActivity.class);
                //명시적 인텐트

                if(web.equals("interpark")){
                    intentRtoI.putExtra("img",R.drawable.img1_3);
                    intentRtoI.putExtra("text",getString(R.string.img1_3));
                } else { //네이버인 경우
                    intentRtoI.putExtra("img",R.drawable.img4_3);
                    intentRtoI.putExtra("text",getString(R.string.img2_3));
                }

                startActivity(intentRtoI);
            }
        });

//10주차
        //각 이미지뷰클릭시 이벤트 ,처리기 공유하는 뷰
        View.OnClickListener ocl =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nVid = v.getId();  //뷰아이디
                Intent intentRtoP = new Intent(getApplicationContext(), PurchaseActivity.class);
                //명시적 인텐트 //앱 내 이동
                switch(nVid){
                    case R.id.imageView1:
                        intentRtoP.putExtra("productNo", 1);
                        break;
                    case R.id.imageView2:
                        intentRtoP.putExtra("productNo", 2);
                        break;
                    case R.id.imageView3:
                        intentRtoP.putExtra("productNo", 3);
                        break;
                } //어떤 이미지뷰로 넘어가는지 알려줌 응답 받을것
                startActivityForResult(intentRtoP,101);
            }
        };

        imgView1.setOnClickListener(ocl);
        imgView2.setOnClickListener(ocl);
        imgView3.setOnClickListener(ocl);

    }//온크리에잇 끝

    //응답받기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101){ //펄차스 액티비티가 보낸 응ㄱ답
            if(resultCode==RESULT_OK){
                String sResult =data.getStringExtra("result"); //구매내역 합텨진거 보여줌

                Toast.makeText(getApplicationContext(),sResult,Toast.LENGTH_LONG).show();
            } else Toast.makeText(getApplicationContext(),"구매 취소되었습니다.",Toast.LENGTH_LONG).show();
            //리절트 오케이 아닐때 뒤로 눌롰을때
        }
    }
}