package ac.dongyang.smartapp.a04_activity2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void onClickLogo(View v){
        //이벤트 소스:로고 이미지
        //로고 클릭시 입학처로 메시지를 보낼수 있는 기능
        //암시적 인텐트
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:01037257248"));
        intent.putExtra("sms_body","질문있습니다.");
        startActivity(intent);
    }

    public void onClickResult(View v){
        //명시적 인텐트
        //이벤트 소스: 수시 합격자, 정시합격자 텍스트뷰
        //기능:인풋 액티비티로 이동

        Intent intent = new Intent(getApplicationContext(),InputActivity.class);
         intent.putExtra("type",v.getId()); //R.id.textSusi / R.id.textJungsi
        //startActivity(intent);
        startActivityForResult(intent,101);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== 101){
            //인풋액티비티에서 넘어온 경우
            if(resultCode==RESULT_OK){
                String sName = data.getStringExtra("name");
                Toast.makeText(this,sName+"학생 합격을 축하합니다.",Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(getApplicationContext(),"취소되었습니다",Toast.LENGTH_SHORT).show();
        }
    }
}