package com.example.navermapapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import needle.Needle;
import needle.UiRelatedTask;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
                                            //프래그먼트 액티비티로 변경
                                                //맵레디 이벤트 처리를 위해 ,이름이 있는 이벤트 처리
  NaverMap mMap;
  InfoWindow mInfoWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);

        mapFragment.getMapAsync(MainActivity.this); //맵레디 메소드 쓰는 이벤트 처리기 등록
    } //온크리에잇 끝

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mMap = naverMap;
        mMap.setMapType(NaverMap.MapType.Basic);
        mMap.moveCamera(CameraUpdate.zoomTo(5.5));//우리나라 지도 전체가 나오도록 축소
        mMap.moveCamera(CameraUpdate.scrollTo(new LatLng(36.50172, 127.28872)));
                                                //지도의 중점정보 표시 메소드 스크롤투


        readCoronaCenters();
    }


    //데이터 메소드

    private void readCoronaCenters(){
        //비동기로 정보 가지고 오기 위해 스레드 생성.시작(런네블)
        Needle.onBackgroundThread().execute(new UiRelatedTask<String>() {
            @Override
            protected String doWork() { //데이터 가공

                String sUrl ="https://api.odcloud.kr/api/15077586/v1/centers?page=1&perPage=5&serviceKey=tNZXs4V9gYoD5NbTfA7wtadozeiVPxzXoHO4nJXL5jcZNtiKld92t8V96fOdKlI2wD94G0MVioQPU55JalMpbw%3D%3D";
                                                                            // 퍼페이지 5개

                StringBuffer sBuf = new StringBuffer();
                HttpURLConnection connection = null;

                try {
                    URL url = new URL(sUrl);
                    connection = (HttpURLConnection)url.openConnection();

                    //통로 열기
                    BufferedInputStream bufStream = new BufferedInputStream(connection.getInputStream());
                    //문자단위로
                    BufferedReader bufReader =new BufferedReader(new InputStreamReader(bufStream,"utf-8"));
                    String sLine ="";
                    //라인단위로
                    while ((sLine = bufReader.readLine()) != null) {
                        sBuf.append(sLine);
                    }

                    bufReader.close();
                    bufStream.close();
                    connection.disconnect();
                    //자원이 한정되어있기 때문에


                } catch (Exception e) {
                    e.printStackTrace();
                }


                return sBuf.toString();
            }

            @Override
            protected void thenDoUiRelatedWork(String s) { //세개의 자료형 일치
                //두 워크의 결과값을 받음
                JsonObject jResult = (JsonObject)JsonParser.parseString(s);
                JsonArray jCenters = (JsonArray)jResult.get("data"); //데이터란 엘리먼트의 정보들을 어레이로 저장

                if(jCenters == null){
                    //두 워크에선 불가하지만 두 유아이릴렛 워크에선 가능한: UI 요소 다루기
                    Toast.makeText(getApplicationContext(),"센터정보가 없습니다. ",Toast.LENGTH_LONG).show();
                    return;
                }
                //어레이에서
                for(int i=0 ; i<jCenters.size(); i++){
                    JsonObject center = (JsonObject)jCenters.get(i); //각센터 정보
                    String centerName =  center.get("centerName").getAsString();//"centerName": "서울 중앙센터"
                    double lat = center.get("lat").getAsDouble();
                    double lng = center.get("lng").getAsDouble();
                    //주소정보와, 퍼실리티 보여줄 예정

                    final Marker marker = new Marker();//인포윈도 클릭이벤트에서 사용위해 파이널
                    LatLng latLng = new LatLng(lat,lng);
                    marker.setPosition(latLng);
                    marker.setMap(mMap);

                    marker.setCaptionText(centerName);
                    final String address = center.get("address").getAsString();
                    final String facilityName =center.get("facilityName").getAsString();


                    marker.setOnClickListener(new Overlay.OnClickListener() {
                        @Override
                        public boolean onClick(@NonNull Overlay overlay) {
                            //infoWindow
                            if (mInfoWindow ==null)
                                mInfoWindow = new InfoWindow();
                            else//인포윈도 떠있던 상태
                                mInfoWindow.close();

                            mInfoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(MainActivity.this){

                                @NonNull
                                @Override
                                public CharSequence getText(@NonNull InfoWindow infoWindow) {
                                    //인포윈도 오픈 순간 호출됨
                                    return address + "\n" + facilityName;
                                    //반환 내용을 보여줌
                                }
                            });

                            mInfoWindow.open(marker); //지도아니고 마커에서 연다
                            return true; //처리완료해서 상위객체에서 더이상 클릭이벤트 처리할 필요없다
                        }
                    });


                } //포문 끝
            //오브젝트>엘리먼트(데이터)>데이타배열값들 >데이터[i](어레이 한개=센터1)>>데이터[]{내부 각 엘리먼트들


            }
        });

    } //리드코로나 센터 메소드

}