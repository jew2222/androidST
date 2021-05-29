package com.example.a12_graphic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class BallShape extends View { //커스텀 뷰를 만들것
    //오버라이드 단축키 누르고 맨위 생성자 두개를 선택합니다
    ShapeDrawable nBall;
    int x=50, y = 50; //공이 그려진는 명역의 좌측상단 기준점
                     //포인트 클래스를 사용해도 됩니다
    int width =100, height =100;//공 사각영역 가로 세로 길이
    int cx,cy; //원 중점
    int dirx=1, diry=1; //좌표의 방향
    int dx =5 , dy =15; //움직일 단위
    int screenWidth ,screenHeight;

    public BallShape(Context context) {
        super(context);
        nBall =new ShapeDrawable( new OvalShape());
        Paint paint = nBall.getPaint();
        paint.setColor(Color.RED);
        screenWidth =  Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public BallShape(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        nBall =new ShapeDrawable( new OvalShape());
        //쉐입드러블 객체에 페인트객체가 미리 정의 되어있다
        Paint paint = nBall.getPaint();
        paint.setColor(Color.RED);
        //시스템리소스 객체를 통해 디스플레이매트릭스 객체를 사용 스크린 길이구하기
        screenWidth =  Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //그려진 객체들 이용 동작
    /*   cx= x + width/2;  //중점은 기준점+반지름
        cy =y+ height/2;

        //방향
        if(cx <= width/2) dirx=1; //반지름보다 중점이 이하이면
        else if (cx>=screenWidth-width/2) dirx=-1;
        if(cy <= height/2) diry=1;
        else if (cy>=screenHeight-height/2) diry=-1;

        //기준점 위치 변화( 이동)
        x+=dirx*dx;
        y+=diry*dy;

        //영역
        canvas.drawColor(Color.BLUE);
       nBall.setBounds(x,y,x+width,y+height);//원그리기
        nBall.draw(canvas);

        invalidate(); //반복 가동
*/

       //터치이벤트 적용시 onDraw에 이것만
            //정지상태로 터치위치로 이동
             //영역
             canvas.drawColor(Color.BLUE);
             nBall.setBounds(x,y,x+width,y+height);//원그리기
             nBall.draw(canvas);
        //

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int ix=(int)event.getX();
        int iy=(int)event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                x= ix;
                y= iy;  //좌표를 터치위치로 갖기
                break;
        }
        invalidate(); //지우고 다시 그려주기
        return true;
    }
}
