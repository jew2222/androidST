package ac.dongyang.smartapp.a04_activity2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//app 에서 new Activity 눌러서 메니페, 자바, 레이아웃을 한번에 추가합니다
public class InputActivity extends AppCompatActivity {

    EditText editSNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);


        TextView textView = findViewById(R.id.texTitle);
        final Intent intentM = getIntent();
        if(intentM != null){
            int nId = intentM.getIntExtra("type",R.id.textSusi); // ,디펄트
            switch(nId){
                case R.id.textSusi:
                    textView.setText("수시 합격자 확인");
                    textView.setBackgroundColor(Color.rgb(0x21,0x96,0xF3));
                    //#2196F3을 16진수 표식을 넣어서 rgb로 나눔
                    break;
                case R.id.textJungsi:
                    textView.setText("정시 합격자 확인");
                    textView.setBackgroundColor(Color.rgb(0x4c,0xaf,0x50));
                    break;
            }

        }

        Button btnCancel = findViewById(R.id.buttonCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        editSNo =findViewById(R.id.editNo); //수험번호
        final EditText editSName =findViewById(R.id.editName); //이름
        Button btnOk = findViewById(R.id.buttonOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sSNo =editSNo.getText().toString();
                String sSName =editSName.getText().toString();
                //양옆 빈칸 정리하기
                sSNo = sSNo.trim();
                sSName =sSName.trim();

                if (sSNo.isEmpty()||sSName.isEmpty()){
                    //입력 없을시 오류메시지
                    Toast.makeText(getApplicationContext(),"수험번호와 이름을 정확히 입력해 주세요.",Toast.LENGTH_LONG).show();
                return;
                }

                Intent intentToM = new Intent();
                intentToM.putExtra("name",sSName);
                setResult(RESULT_OK,intentToM); //인텐트 성공 명백히 전달
                finish();
            }
        });

    }//onCreate 끝




}