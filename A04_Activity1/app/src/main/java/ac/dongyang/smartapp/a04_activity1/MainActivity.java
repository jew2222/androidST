package ac.dongyang.smartapp.a04_activity1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    ImageView imageDiary;
    TextView textDiary;
    EditText editDiary;

    public void onClickInput(View v){
        String sInput = editDiary.getText().toString();
        textDiary.append(sInput); //setText(sInput);하면 바뀜
        editDiary.getText().clear(); //setText("");

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDiary =findViewById(R.id.textDiary);
        editDiary = findViewById(R.id.editDiary);
        imageDiary =findViewById(R.id.imageDiary);

        imageDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //암시적 인텐트

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //원하는 기능 - 외부앱에서 콘텐트 가져오기
                intent.setType("image/*"); //jpg 등 따로 설정도 가능
                startActivityForResult(intent,101);
        }
        });


        Button btnClear = findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                textDiary.setText("");
                editDiary.setText("");
            }
        });

    }//온크리에잇 끝

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101){
            if(resultCode==RESULT_OK){
                ContentResolver contentResolver = getContentResolver();
                //콘텐트프로바이더 역을 하는 앱(갤러리,연락처)등에서 데이터 가져오기 위한 객체
                //파일 내용 읽기 위한 객체 인풋스트림
                try {
                    InputStream inputStream = contentResolver.openInputStream(data.getData());
                                                 //겟데이타()가 데이터 위치값 알려줌
                    Bitmap img = BitmapFactory.decodeStream(inputStream);
                    imageDiary.setImageBitmap(img);
                    //드러블 소스는 setImageResource 였음

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                   Toast.makeText(getApplicationContext(),"이미지 로드 오류",Toast.LENGTH_LONG).show();
                }
                //import java.io.InputStream;해주기
                //트라이 캣치문 해주기
            }
        }
    }
}