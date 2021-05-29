package com.example.a12_graphic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class BallView extends View {

    int x=50, y = 50; //공이 그려진는 영역의  좌측상단 기준점
    int width =100, height =100;//공 사각영역 가로 세로 길이
    int cx,cy; //원 중점
    int dirx=1, diry=1; //방향
    int dx =5 , dy =15; //움직일 단위
    int screenWidth ,screenHeight;
    Paint nPaint;

    public BallView(Context context) {
        super(context);
        screenWidth =  Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        nPaint =new Paint();
        nPaint.setColor(Color.rgb(0xff,0x00,0x00));
        nPaint.setStyle(Paint.Style.FILL);
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        screenWidth =  Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        nPaint =new Paint();
        nPaint.setColor(Color.rgb(0xff,0x00,0x00));
        nPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        cx= x + width/2; //중점은 기준점+반지름
        cy =y+ height/2;
        //방향
        if(cx <= width/2) dirx=1; //반지름보다 중점이 이하이면
        else if (cx>=screenWidth-width/2) dirx=-1;
        if(cy <= height/2) diry=1;
        else if (cy>=screenHeight-height/2) diry=-1;
        //기준점 위치 변화( 이동)
        x+=dirx*dx;
        y+=diry*dy;

        canvas.drawColor(Color.BLUE);
        canvas.drawCircle(cx,cy,width/2,nPaint);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x=(int)event.getX();//플롯타입반환이라, 클릭지점 설정
        int y=(int)event.getY();

        int action = event.getAction();

        if (action==MotionEvent.ACTION_UP) //업하는 경우의 기능
            //볼 중심과 클릭좌표를 비교해 공방향 구하기
            if(x>cx) dirx=1; //공의 중점보다 좌표가 크면 ->
            else dirx=-1;
            if(y>cy) diry =1; // ㅅ
            else diry= -1;

            //중점에서 멀수록 빠르게 이동
            dx= Math.abs(cx-x)/15;
            dy = Math.abs(cy-y)/15; //음수일 수 있어서 절대값 , 단위결정

        return true;//부모에 보내지 않는다
    }
}
