package ac.dongyang.smartapp.a07_listview2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class editActivity extends AppCompatActivity {

    private EditText mEditName , mEditId, mEditPwd , mEditUrl;
    private int mItem = -1; //넘겨져오는 인덱스값 저장 위한
    //새항목이면 -1 , 수정중이면 인덱스가 넘겨옴

    private DBHelper mDBHelper;// 디비헬퍼 객체 변수
    private SQLiteDatabase mDB;

    private int mISelectedID= 0; //현재선택된 항목 아이디



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        mEditName =findViewById(R.id.editTextName); //웹이름
        mEditId =findViewById(R.id.editTextLoginId);
        mEditPwd = findViewById(R.id.editTextTextPassword);
        mEditUrl= findViewById(R.id.editTextTextUrl);

        mDBHelper= new DBHelper(getApplicationContext());


        Intent inMtoE = getIntent();

        if(inMtoE != null){
            mItem = inMtoE.getIntExtra("item",-1); //인덱스받기
            mISelectedID=inMtoE.getIntExtra("id", 0); //추가인경우 0으로 옴

            if (mItem != -1) { //항목이 선택이 된 경우
                mEditName.setText(inMtoE.getStringExtra("name"));
                mEditPwd.setText(inMtoE.getStringExtra("pwd"));
                mEditId.setText(inMtoE.getStringExtra("loginId"));
                mEditUrl.setText(inMtoE.getStringExtra("url"));
            }
        }

        //취소버튼
        Button btnCancel = findViewById(R.id.buttonCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //메인으로 돌아가기 ,백스텍에서 소멸
            }
        });


        //저장버튼
         Button btnSave = findViewById(R.id.buttonSave);
         btnSave.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v) {
                 //에딧 읽어와서 저장
                 String sName = mEditName.getText().toString().trim();
                 String sId = mEditId.getText().toString().trim();
                 String sPwd = mEditPwd.getText().toString().trim();
                 String sUrl = mEditUrl.getText().toString().trim();
                 //비어있으면 안되지만 유알엘은 상관없


                 if (sName.isEmpty() || sId.isEmpty() || sPwd.isEmpty()) {

                     //저장되었나 조회

                     Toast.makeText(getApplicationContext(), "url을 제외한 항목들은 빠짐없이 입력해 주세요."
                             , Toast.LENGTH_SHORT).show();
                     return;
                 }


//입력한 웹이름이 중복되면 같으면 안됨
                 mDB = mDBHelper.getReadableDatabase();
                 Cursor cursor = mDB.rawQuery(DBContract.SQL_SELECT_ID, new String[] {sName}); //sName이 입력한 웹
                 if(cursor.getCount() != 0) { //결과 중복 가능성 있는경우

                 cursor.moveToNext(); //첫번째 레코드를 가르키키 위해

                 //추가경우 -1
                 //수정경우 0이상
                 if (mItem == -1) { //중복이 되었다는 얘기
                     Toast.makeText(getApplicationContext(), "중복된 항목이 있습니다.", Toast.LENGTH_SHORT).show();
                     return;

                 } else if (mISelectedID != cursor.getInt(0)) {//찾아온 레코드를 수정하려는 경우 일 수 있음
                     Toast.makeText(getApplicationContext(), "중복된 항목이 있습니다.", Toast.LENGTH_SHORT).show();
                     return;
                 }
             }

                         //아이디 컬럼만 가져옴


                 //컬럼에 데이터 저장하는 부분

                 Intent inEtoM = new Intent(); //기본생성자
                 inEtoM.putExtra("item",mItem); //현재 편집한 인덱스 알려줌 -1이면 새로 추가한경우고 , 아니면 수정우 경우라
                 inEtoM.putExtra("name",sName);
                 inEtoM.putExtra("loginid",sId);// 로그인 아이디
                 inEtoM.putExtra("pwd",sPwd);
                 inEtoM.putExtra("url",sUrl);

                 setResult(RESULT_OK,inEtoM);
                 finish();

             }//온클릭 저장 버튼 끝
         });

    }//온크리 끝
}