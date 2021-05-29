package ac.dongyang.smartapp.a07_listview2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivityR extends AppCompatActivity {
    //fna룸 사용을 위해 복사함

    //이 클래스 안에서만 사용하기 위해 프라이빗
    //멤버변수 앞에 m을 붙이는것 권장
    private ListView mListview;
    private SimpleAdapter mSAdapter;
    private ArrayList<HashMap<String, String>> mListData;
    //세개의 문자 한번에 보여줄 것인데 그때 레이아웃과 해쉬맵의 키 갯수 중요

    private int mISelectedItem = -1; //선택항목 인덱스


    public void onClickAdd(View v) {//추가버튼
        Intent inMtoE = new Intent(getApplicationContext(), editActivity.class);
        inMtoE.putExtra("item", -1); //mISelectedItem을 넣어도 됩니다.지금은 add라 선택항목이 없음
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
        Intent inMtoE = new Intent(MainActivityR.this, editActivity.class);

        inMtoE.putExtra("name", item.get("name"));
        inMtoE.putExtra("pwd", item.get("pwd"));
        inMtoE.putExtra("loginId", item.get("loginId"));
        inMtoE.putExtra("url", item.get("url"));
        inMtoE.putExtra("item", mISelectedItem); // 멤버변수라

        startActivityForResult(inMtoE, 200); //메인이라 두 이벤트 모두 200

    }//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// name , id , password,  url  을 저장하려함. 그중 3개만 리스트뷰에 보여줄거임

        mListData = new ArrayList<>();// 자료형은 위에서 선언함
        mSAdapter = new SimpleAdapter(this, mListData, R.layout.simple_list_item_activated_3,
                new String[]{"name", "loginId", "url"}, new int[]{R.id.text1, R.id.text2, R.id.text3});
        //키 배열을 사용해서 3개를 - 뷰에 있는 데이터를 투입
        //해쉬맵에 들어있는 - 여러개의 항목에서(인덱스로 구분) -각 항목은 같은 종류의 키를 3개 가지는것

        mListview = findViewById(R.id.listView);
        mListview.setAdapter(mSAdapter);


        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//항목의 인덱스, 항목의 아이디

                mISelectedItem = position; //멤버에 항목 인덱스 저장
                //그 항목의정보를 가진 해쉬맵객체를 가져와서 데이터를 받아야함

                HashMap<String, String> item =
                        (HashMap<String, String>) mSAdapter.getItem(position);//getItem인자는 리스트뷰 항목 인덱스
                //getItem()이 오브젝트로 오는거라 해쉬맵에 저장이 안되서 변환

                Toast.makeText(getApplicationContext(), item.get("name"), Toast.LENGTH_SHORT).show();
                //웹사이트 이름 보여주기
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != 200)
            return;

        if (resultCode == RESULT_OK) {
            int item = data.getIntExtra("item", -1);
            HashMap<String, String> hitem = new HashMap<>();
            hitem.put("name", data.getStringExtra("name"));
            hitem.put("loginId", data.getStringExtra("loginId"));
            hitem.put("pwd", data.getStringExtra("pwd"));
            hitem.put("url", data.getStringExtra("url"));

            if(item == -1){ //추가하는경우
                mListData.add(hitem);}
                //아니면 변경 경우
            else {mListData.set(item, hitem);}
            mSAdapter.notifyDataSetChanged();

            //해쉬맵 객체가 아이템이 변경되었을 경우

        } else //cancel 호출
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();


    }//액티리절트끝

}