package com.example.a13_animation;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView imgGun,imgClay,imgBullet;
    int screenWidth;
    final int NO_OF_CLAY =5; //날아가는 클레이 갯수

    //property animation 에서 object animation 클래스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         imgGun =findViewById(R.id.imageViewGun);
        imgClay =findViewById(R.id.imageViewClay);
         imgBullet =findViewById(R.id.imageViewBullet);
         //스크린 길이 알아오기
         screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;

        //애미메이션 셋 , xml불러 애니객체읽어오기
         AnimatorSet clayA= (AnimatorSet)AnimatorInflater.loadAnimator(this,R.animator.clayanim);
        clayA.setTarget(imgClay);
        clayA.start(); //클레이 애니메이션

        /* 애니메이터 클래스안의 애니메이터 리스너 사용했었음
           애니메이터셋 클래스 안에도 add리스너 메소드가 있는데
           스타트,엔드 말고 리핏은 잘 못받습니다
           그래서 애니메이터셋에 개별적 애니메이터 객체를 끌어내 각각 리스너 등록*/

         //클레이A 세트에 있는 모든 애니객체 얻기
        ArrayList<Animator> childAnimations = clayA.getChildAnimations();
        //첫번째 오브젝트 애니메이터 꺼내기
        childAnimations.get(0).addListener(new Animator.AnimatorListener() {
                @Override
               public void onAnimationStart(Animator animation) {
                   imgClay.setVisibility(View.VISIBLE);
               }
               @Override
               public void onAnimationEnd(Animator animation) {
                   Toast.makeText(getApplicationContext(),"게임종료!"
                           ,Toast.LENGTH_LONG).show();
               }
               @Override
               public void onAnimationCancel(Animator animation) { }
               @Override
               public void onAnimationRepeat(Animator animation) {
                   imgClay.setVisibility(View.VISIBLE);
               }
           });

//클래스를 이용한 애니메이션 구현
        //회전하며 X축 이동한다
        //이동 애니메이터
        ObjectAnimator clayX= ObjectAnimator.ofFloat(imgClay,"translationX",-200f,screenWidth+100f);
        //회전 에니메이터 각각 만든다 ,360넘어 여러번 회전
        ObjectAnimator clayR = ObjectAnimator.ofFloat(imgClay,"rotation",0f,1080f);
        clayX.setDuration(3000);
        clayX.setRepeatCount(NO_OF_CLAY-1); //기본으로 1추가됨
        clayX.setInterpolator(new AccelerateInterpolator()); //변화율 빠르게

        clayR.setDuration(3000);
        clayR.setRepeatCount(NO_OF_CLAY-1);
        clayR.setInterpolator(new LinearInterpolator());

         //이동 애니메이션 시작과 종료 캐치해서 이벤트처리
         clayX.addListener(new Animator.AnimatorListener() {
             @Override
             public void onAnimationStart(Animator animation) {
                 imgClay.setVisibility(View.VISIBLE);//시작할때부터 보여주게
                 //효과를 발휘하기 위해선 레이아웃에 visibility 속성 기본 안보이게 조정
             }
             @Override
             public void onAnimationEnd(Animator animation) {
                 Toast.makeText(getApplicationContext(),"게임종료!",Toast.LENGTH_LONG).show();
              } //리핏카운트 끝날때
             @Override
             public void onAnimationCancel(Animator animation) {}
             @Override
             public void onAnimationRepeat(Animator animation) {
                 imgClay.setVisibility(View.VISIBLE);
             } //인터페이스 재정의
         });

        clayX.start(); //각각 스타트 할때
        clayR.start();
    //

        //총 클릭이벤트 등록
        imgGun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgBullet.setVisibility(View.VISIBLE);
                //총알애니/ 세로,가로길이 축소 , 세로축으로 이동  애니메이션이 3개다
                ObjectAnimator scaleX = ObjectAnimator.ofFloat(imgBullet, "scaleX", 1.0f, 0.0f);//불렛크기 가로
                ObjectAnimator scaleY = ObjectAnimator.ofFloat(imgBullet, "scaleY", 1.0f, 0.0f); //불렛세로
                ObjectAnimator transY = ObjectAnimator.ofFloat(imgBullet, "translationY", imgGun.getY() -100.0f, 20); //총길이부터

                //명중 애니메이션 = 애니 대상값 변경시마다 호출(검사)
                transY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        //총알 중점과 클레이 중점
                        int bullet_width = imgBullet.getWidth();
                        int bullet_height = imgBullet.getHeight();
                        float bullet_cx = imgBullet.getX() + bullet_width / 2; //좌측상단에서 반지름을 더해준다
                        float bullet_cy = imgBullet.getY() + bullet_height / 2;

                        int clay_width = imgClay.getWidth();
                        int clay_height = imgClay.getHeight();
                        float clay_cx = imgClay.getX() + clay_width / 2;
                        float clay_cy = imgClay.getY() + clay_height / 2;

                        // 이 중점과 클레이와  총알과의 거리
                        // 피타고라스정의 직각삼각형 나머지 두변으로 빗변(거리)구하기  a^2 +b^2 = c^2
                        float distX = Math.abs(clay_cx - bullet_cx); //마이너스값 방지
                        float distY = Math.abs(clay_cy - bullet_cy);
                        float dist = (float) Math.sqrt(distX * distX + distY * distY); //원래 더블형

                        if (dist <= 150) {
                            Toast.makeText(getApplicationContext(), "명중!!", Toast.LENGTH_LONG).show();

                            imgClay.setVisibility(View.INVISIBLE);
                        }
                    }
                }); //클릭이벤트 안에 업데이트이벤트가 있다


                //애니메이터들 묶어서 사용할때
                AnimatorSet bulletA = new AnimatorSet();
                bulletA.playTogether(scaleX, scaleY, transY);
                bulletA.setDuration(500);
                bulletA.start();
            }
        });
}//온크리에이트
}//메인