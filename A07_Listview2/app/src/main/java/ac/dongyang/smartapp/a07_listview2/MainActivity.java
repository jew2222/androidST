package ac.dongyang.smartapp.a07_listview2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //이 클래스 안에서만 사용하기 위해 프라이빗
    //멤버변수 앞에 m을 붙이는것 권장
    private ListView mListview;
    private SimpleAdapter mSAdapter;
    private ArrayList<HashMap<String, String>> mListData;
    //세개의 문자 한번에 보여줄 것인데 그때 레이아웃과 해쉬맵의 키 갯수 중요

    private int mISelectedItem = -1; //선택항목 인덱스


    private DBHelper mDBHelper;// 디비헬퍼 객체 변수
    private SQLiteDatabase mDB;
    private int mISelectedID= 0; //현재선택된 항목 아이디
    private int mID =0; //저장 레코드중 아이디 가장큰 것 저장 (값을 하나씩 증가해서 중복 막음)



    public void onClickAdd(View v) {//추가버튼
        Intent inMtoE = new Intent(getApplicationContext(), editActivity.class);
        inMtoE.putExtra("item", -1); //mISelectedItem을 넣어도 됩니다.지금은 add라 선택항목이 없음
        inMtoE.putExtra("id",0);//현재 추가눌러서 항목선택 없기때문 0은 mIselecteID 랑 같

        startActivityForResult(inMtoE, 200);


        //수정을 해도, 츄가를 해도 에딧액티로 넘어가기 때문에 구분 필요
        //애딧액티에서 값을 받아올거라 리절트 필요
    }


    //수정버튼
    public void onClickEdit(View v) {
        //선택된 항목 읽어서 보내줘야함

        if (mISelectedItem == -1) {
            Toast.makeText(getApplicationContext(), "항목을 선택해 주세요.", Toast.LENGTH_SHORT).show();
            return;

        }
        //선택항목의 정보를 어뎁터 통해 가져오기
        HashMap<String, String> item = ((HashMap<String, String>) mSAdapter.getItem(mISelectedItem));//괄호 묶기
        Intent inMtoE = new Intent(MainActivity.this, editActivity.class);
      //기존값들
        inMtoE.putExtra("name", item.get("name"));
        inMtoE.putExtra("pwd", item.get("pwd"));
        inMtoE.putExtra("loginid", item.get("loginid"));
        inMtoE.putExtra("url", item.get("url"));
        inMtoE.putExtra("item", mISelectedItem); // 멤버변수라
        inMtoE.putExtra("id", mISelectedID);

        startActivityForResult(inMtoE, 200); //메인이라 두 이벤트 모두 200

    }//

    private void loadTable() {
        //디비헬퍼클래스 사용하지만 sqle디비객체 필요
        mDB = mDBHelper.getReadableDatabase(); //읽어오기떄문
        mListData.clear();
//ㄱ모두 가져오기
        Cursor cursor = mDB.rawQuery(DBContract.SQL_LOAD, null);//와일드카드없었음
        while (cursor.moveToNext()) {
            //레코드 가져와서 리스브뷰(어레이리스트)에 넣기
            HashMap<String, String> hitem = new HashMap<>();
            int nID = cursor.getInt(0);//인자:레코드 순서
            mID = Math.max(mID, nID);//현재 테이블안 레코드중 가장큰것의 아이디 저장 (이전에큰것,지금)
            //아이디도 해쉬맵 개체에 저장
            hitem.put("id", String.valueOf(nID)); //정수인데 스트링으로 저장해야함 레코드 아이디
            hitem.put("name", cursor.getString(1));
            hitem.put("loginid", cursor.getString(2));
            hitem.put("pwd", cursor.getString(3));
            hitem.put("url", cursor.getString(4));

            mListData.add(hitem); //어레이에 추가
        }
        mSAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// name , id , password,  url  을 저장하려함. 그중 3개만 리스트뷰에 보여줄거임

        mListData = new ArrayList<>();// 자료형은 위에서 선언함
        mSAdapter = new SimpleAdapter(this, mListData, R.layout.simple_list_item_activated_3,
                new String[]{"name", "loginid", "url"}, new int[]{R.id.text1, R.id.text2, R.id.text3});
        //키 배열을 사용해서 3개를 - 뷰에 있는 데이터를 투입
        //해쉬맵에 들어있는 - 여러개의 항목에서(인덱스로 구분) -각 항목은 같은 종류의 키를 3개 가지는것

        mListview = findViewById(R.id.listView);
        mListview.setAdapter(mSAdapter);
        //
   //10주차에
        mDBHelper = new DBHelper(getApplicationContext());
        //이전 저장 데이터 보여주기
        loadTable();


//리스트뷰 항목 클릭시 이벤
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//항목의 인덱스, 항목의 아이디

                mISelectedItem = position; //멤버에 항목 인덱스 저장
                //그 항목의정보를 가진 해쉬맵객체를 가져와서 데이터를 받아야함

                HashMap<String, String> item =
                        ((HashMap<String, String>)mSAdapter.getItem(position));//getItem인자는 리스트뷰 항목 인덱스
                //getItem()이 오브젝트로 오는거라 해쉬맵에 저장이 안되서 변환

                Toast.makeText(getApplicationContext(), item.get("name"), Toast.LENGTH_SHORT).show();
                //웹사이트 이름 보여주기

                //+아이디도 저장하기
                mISelectedID =Integer.parseInt(item.get("id"));//아이디가 스트링이라 오류 이니 바꿈
            }
        });


        //보기 버튼
        Button btnInfo = findViewById(R.id.buttonInfo);
        btnInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mISelectedItem == -1) {
                    Toast.makeText(getApplicationContext(), "항목을 선택해 주세요.",
                            Toast.LENGTH_SHORT).show();
                    return;
                 }

            HashMap<String,String> item = ((HashMap<String, String>)mSAdapter.getItem(mISelectedItem));
                //항목 읽어오기(비번만 필요)
                Toast.makeText(getApplicationContext(),"비밀번호 : "+ item.get("pwd"),
                        Toast.LENGTH_LONG).show();
                //스트링간 +는 사실 효율적이지 않아서 원래 스트링버퍼사용

            }
        });
    }//온크리


//
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != 200)
            return;

        if (resultCode == RESULT_OK) {
            int item = data.getIntExtra("item", -1);

            HashMap<String, String> hitem = new HashMap<>();

            hitem.put("name", data.getStringExtra("name"));
            hitem.put("loginid", data.getStringExtra("loginid"));
            hitem.put("pwd", data.getStringExtra("pwd"));
            hitem.put("url", data.getStringExtra("url"));

            ContentValues values = new ContentValues();
            //인텐트에서 읽어와 컴럼에 저장
            values.put(DBContract.COL_NAME,data.getStringExtra("name"));
            values.put(DBContract.COL_LID,data.getStringExtra("loginid"));
            values.put(DBContract.COL_PWD,data.getStringExtra("pwd"));
            values.put(DBContract.COL_URL,data.getStringExtra("url"));

            mDB = mDBHelper.getWritableDatabase();
            if(item == -1) {//추가
                values.put(DBContract.COL_ID, ++mID);
                hitem.put("id", String.valueOf(mID));
                mDB.insert(DBContract.TABLE_NAME, null, values);
                mListData.add(hitem);

            }else {//수정
                hitem.put("id", String.valueOf(mISelectedID));
                mDB.update(DBContract.TABLE_NAME, values, "id=" + mISelectedID, null);
                mListData.set(item, hitem);
            }
            mSAdapter.notifyDataSetChanged();





//
//            if(item == -1){ //추가하는경우
//                mListData.add(hitem);}
//                //아니면 변경 경우
//            else {mListData.set(item, hitem);}
        //    mSAdapter.notifyDataSetChanged();

            //해쉬맵 객체가 아이템이 변경되었을 경우

        }else //cancel 호출
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();


    }//액티리절트끝


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDBHelper !=null)
            mDBHelper.close();
    }
}