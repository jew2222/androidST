package ac.dongyang.smartapp.a07_listview1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView1 = findViewById(R.id.listView1);
        ListView listView2 = findViewById(R.id.listView2);
        ListView listView3 = findViewById(R.id.listView3);

        //항목 클릭 이벤트
        //1. 엔트리 속성 리스트뷰 클릭시 토스트
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     //(리스트뷰 객체, 항목(텍스트뷰) , (전체데이터중)인덱스 , (어뎁관리)데이터 아이디)
                String sItem= ((TextView)view).getText().toString();
                                //겟 텍스트는 텍스트뷰만 지원하는 메소드
                Toast.makeText(getApplicationContext(),sItem,Toast.LENGTH_LONG).show();
                           //온클릭이면 리스트뷰 자체. 온아이템클릭은 항목당 이벤트
            }
        });

        ArrayList<String> list_data2 = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice,list_data2);
        //싱글 리스트 아이템 1은 선택한것이 라디오로 보이는 레이아웃

        //항목 삭제, 추가 는 어레이어뎁터 사용
        arrayAdapter.add("list2 item1");
        arrayAdapter.add("list2 item2");
        arrayAdapter.add("list2 item3");
        arrayAdapter.add("list2 item4");
        arrayAdapter.add("list2 item5");
        arrayAdapter.add("list2 item6");
        arrayAdapter.add("list2 item7");
        arrayAdapter.add("list2 item8");
        arrayAdapter.add("list2 item9");
        arrayAdapter.add("list2 item10");
        arrayAdapter.add("list2 item11");
        arrayAdapter.add("list2 item12");
        arrayAdapter.add("list2 item13");
        arrayAdapter.add("list2 item14");
        arrayAdapter.add("list2 item15");
        arrayAdapter.add("list2 item16");
        arrayAdapter.add("list2 item17");
        arrayAdapter.add("list2 item18");

        arrayAdapter.remove("list2 item4");

        //데이터 수정은 어레이리스트
        list_data2.set(0,"list22 item2"); //(항목순서, 스트링)

        listView2.setAdapter(arrayAdapter);

        //버튼 누름시 등의 수정 상황에만 원래 사용
        arrayAdapter.notifyDataSetChanged(); //변경은 어뎁터에 알려준다


        //2.
        ArrayList<HashMap<String,String>> list_data3 = new ArrayList<>();
        //심플 어뎁터는 <>안함
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list_data3,
                android.R.layout.simple_list_item_activated_2,
                        new String[] {"item1","item2"},new int[] {android.R.id.text1,android.R.id.text2});
                             //심플_리스트_아이템_액티베이티드 레이아웃은 선택시 백 색이 바뀌게 보임
                                  //(콘텍스트,데이터,레이아웃,키들 배열 , 뷰 아이디)
        //왜 Int[] 가 아닌 int[] 일까?
        //android. 이 붙고 안붙을 때의 차이는 무엇일까?
        //뷰 아이디는 어디서 보나?

        listView3.setAdapter(simpleAdapter);

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("item1","EKPark");
        hashMap.put("item2","010-111-1111");
        //두개만 풋
        // 만든 해쉬맵을 해쉬맵을 가지는 어레이리스트에 넣음
        list_data3.add(hashMap);
        //심플 어뎁터는 애드,리무브를 지원 안함


        //변수를 공유 (객체는 다시 생성됨)
        hashMap = new HashMap<>();
        hashMap.put("item1","HKPark");
        hashMap.put("item2","010-111-1111");

        list_data3.add(hashMap); //어레이 리스트에 저장시 키는 같아도 해쉬맵 0,1 이 됩
        simpleAdapter.notifyDataSetChanged();
        //아까 어뎁터를 이미 뷰에 관리자 임명은 했고 , 바뀐 데이터만 전달하면 반영됨




    } //온크리에잇 끝
}