package com.example.a05_event;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView img1, img2, img3;
    private int trafficLight =1; //1:파란불, 2:노란불,3:빨간불




    public void onClickScreen(View v) {
        switch(trafficLight){
            case 1: Toast.makeText(getApplicationContext(),R.string.go,Toast.LENGTH_SHORT).show();
                    break;
            case 2: Toast.makeText(getApplicationContext(),R.string.notice,Toast.LENGTH_SHORT).show();
                break;
            case 3: Toast.makeText(getApplicationContext(),R.string.stop,Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void onClickChange(View v) { //신호변경메소드
        trafficLight++;
        if (trafficLight >3) trafficLight=1;
        img1.setImageResource(R.drawable.off);
        img2.setImageResource(R.drawable.off);
        img3.setImageResource(R.drawable.off); //초기화 에
      //아래는 단순
        if(trafficLight==1) img1.setImageResource(R.drawable.green);
        else if(trafficLight==2) img2.setImageResource(R.drawable.yellow);
        else img3.setImageResource(R.drawable.red);
    }

    public void onClickGreen(View v){
        String str = getString(R.string.green);
        Toast toast = Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG);

        toast.show();
    }

        public void onClick(View v){

        int id = v.getId();

        if(id==R.id.imageViewYellow)
             Toast.makeText(this,R.string.yellow,Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,R.string.red,Toast.LENGTH_SHORT).show();
        }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img1=findViewById(R.id.imageViewGreen);
        img2=findViewById(R.id.imageViewYellow);
        img3=findViewById(R.id.imageViewRed);





    }
}