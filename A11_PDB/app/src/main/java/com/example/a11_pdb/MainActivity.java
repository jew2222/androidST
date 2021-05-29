package com.example.a11_pdb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import needle.Needle;
import needle.UiRelatedTask;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView);
        final String sUrl ="https://api.odcloud.kr/api/15077586/v1/centers?page=1&perPage=10&serviceKey=tNZXs4V9gYoD5NbTfA7wtadozeiVPxzXoHO4nJXL5jcZNtiKld92t8V96fOdKlI2wD94G0MVioQPU55JalMpbw%3D%3D";

        //데이터 요청 주소 , ?파라미터에 페이지, 퍼 페이지 , 서비스키 있음, 파라미터 값을 10으로 바꿈

        //니들라이브러리로 (백그라운드로 돌아가고, 서브스레드에서 Ui 구성요소에 접근)
        Needle.onBackgroundThread().execute(new UiRelatedTask<String>() { //오류 오버라이딩해주기
            @Override
            protected String doWork() { //공공DB에 접근한 가져오는
                HttpURLConnection connection =null;

                try {
                    URL url = new URL(sUrl);//스트링으로 URl 객체생성 ,예외처리 요함
                    connection = (HttpURLConnection)url.openConnection(); //예외처리 요함, 아래 모든 익셉션으로 묶어주기
                    InputStream inputStream = connection.getInputStream(); //연결 코넥션을 통해 통로를 염 (바이트 단위)
                    BufferedInputStream bufStream = new BufferedInputStream(inputStream); //버퍼로 바꾸기(3바이트 단위)
                    InputStreamReader inputStreamReader = new InputStreamReader(bufStream,"utf-8");// (2바이트 문자단위)
                    BufferedReader bufReader = new BufferedReader(inputStreamReader);//문자 단위 버퍼링
                    //
                    StringBuffer sBuf = new StringBuffer();
                    String sLine ="";
                    //라인별로 읽기
                    while((sLine = bufReader.readLine()) != null){
                        sBuf.append(sLine);
                    }

                      //열어둔 통로 닫기
                    bufReader.close();
                    inputStreamReader.close();
                    bufStream.close();
                    inputStream.close();
                    connection.disconnect();

                    return sBuf.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null; //오류시 null반환
            }

            @Override
            protected void thenDoUiRelatedWork(String s) { //위 메소드의 반환값으로 json 파싱해 추출함

            if (s==null)
                return;
            JsonObject jsonObject = (JsonObject)JsonParser.parseString(s);//((제이슨오브젝트)엘리먼트) 반환
                //오브젝트 > 엘리먼트ss
                JsonArray centers = (JsonArray)jsonObject.get("data"); //((제이슨어레이)엘리먼트) 반환

                if (centers ==null){
                    Toast.makeText(getApplicationContext(),"센터정보가 없습니다.",Toast.LENGTH_SHORT).show();
                    //화면 구성요소 접근 가능
                    return;
                }
                //센터 정보는 내부에 제이슨엘리먼트들이 괸장히 많음




                StringBuffer sBuf = new StringBuffer();

                for (int i = 0; i<centers.size(); i++){
                    JsonObject center = (JsonObject)centers.get(i); //센터 하나 가져옴

                    JsonElement jAddress =center.get("address");
                    JsonElement jFacilityName = center.get("facilityName");
                    JsonElement jCenterName = center.get("centerName");
                    JsonElement jLat = center.get("lat");
                    JsonElement jLng = center.get("lng");
                   //엘리멘트에서 값을 읽어 사용
                    String address =jAddress.getAsString();
                    String facilityName =jFacilityName.getAsString();
                    String centerName =jCenterName.getAsString();

                    double lat =jLat.getAsDouble();
                    double lng =jLng.getAsDouble();

                    //텍스트뷰에 보여주기 위해 그때 그때 어펜드도 좋지만.한꺼번에 쌓아 문자열로 바꿔 보여주게
                    sBuf.append("*******************************\n"); //센터간 사이의 구분선
                    sBuf.append(centerName).append("\n");
                    sBuf.append("장 소 : ").append(facilityName).append("\n");
                    sBuf.append("주 소 : ").append(address).append("\n");
                    sBuf.append("위 치 : ").append(lat).append(", ").append(lng).append("\n");
                }
                mTextView.setText(sBuf.toString());
            }
        });
        //문자열로 읽어 데이터를 다루기 때문에 <스트링> -> 자료형 두 메소드들에서도 일치함



    }//온크리 끝
}