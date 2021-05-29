package com.example.navermapapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;

import java.io.IOException;
import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
//프래그먼트 포함 액티비티 상속원은 프래그먼트 액티비티
    //온 맵레디 이벤트 처리 위한 implements

    NaverMap mMap;
    MapFragment mMapfragment;
    Marker mMarker,mMarkerl; //현재위치, 마지막위치
    //통합위치관리제공자
    FusedLocationProviderClient mFLPC;
    LocationCallback mLocationCallback; //이벤트처리기



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //공통부분 (맵프래그먼트랑 통합위치관리자랑)
        mMapfragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        //프래그먼트 매니저 객체 . 프래그먼트 아이디로 프래그먼트 가져오기, 프래그먼트를 반환하기 떄문에 MapFragment 로 형변환
        //이벤트 처리기 등록
        mMapfragment.getMapAsync(this); //준비가 되면 아래 온맵레디 메소드 호출됨

    } //온크리에이트끝


    //반복때문에 새로 만드는 메소드
    @SuppressLint("MissingPermission")
    void getLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //높은 정확도
        locationRequest.setInterval(5 * 1000);  //밀리 단위로 5초
        mFLPC.requestLocationUpdates(locationRequest, mLocationCallback, Looper.getMainLooper());
        //위에 로케이션 리퀘스트 와 세트로 붙여넣기 / 경고에 suppress 도 추가
    }



        @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

            mMap =naverMap;
            mMap.setSymbolScale(1f); //1f 는 표준 사이즈 (디펄트)
            mMap.setMapType(NaverMap.MapType.Basic);// 하이브리드 등의 타입 있음
            mMap.moveCamera(CameraUpdate.zoomTo(15)); //카메라 업데이트 객체.줌 투 메소드가 카메라업데이트 객체 반환



        //실습1
        Geocoder geocoder = new Geocoder(this); //인자: 콘텍스트
        List<Address> list = null;

        //익셉션 처리를 해야함
        try {
            list = geocoder.getFromLocationName("동양미래대학교",5);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"주소변환 오류",Toast.LENGTH_SHORT).show();
        }

        if ( list==null || list.size() == 0){

            Toast.makeText(getApplicationContext(),"해당되는 주소 정보는 없습니다",Toast.LENGTH_SHORT).show();
            return;

        }

        final Address address = list.get(0); //첫번째 주소 가져오기
        double lat = address.getLatitude(); //라티튜드 위도
        double lng = address.getLongitude(); //롱기튜드 경도
        LatLng latLngDY = new LatLng(lat,lng);

        Marker mMarker = new Marker();
        mMarker.setPosition(latLngDY);
        mMarker.setMap(mMap);
        mMarker.setCaptionText("동양 미래 대학교");
        mMarker.setCaptionColor(Color.BLUE);
        mMarker.setCaptionHaloColor(Color.MAGENTA);//테두리 색

        mMarker.setSubCaptionText("도서관");
        mMarker.setSubCaptionColor(Color.GREEN);

        mMap.moveCamera(CameraUpdate.scrollTo(latLngDY)); //카메라 위치 이동


        //인포윈도우 - 오픈시 환경에 따라 바뀔 수 있음
        InfoWindow infoWindow = new InfoWindow();
        infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(this) { //디펄트뷰 어뎁터도 있음
                                  @NonNull
                                  @Override
                                  public CharSequence getText(@NonNull InfoWindow infoWindow) {//인포 윈도우 열릴 떄 호출됩니다
                                      return address.getAddressLine(0);  //겟어드레스라인은 전체 주소
                                      //리턴값을 널 말고 다른걸로 바꿔주기

                                  }
                              });
        infoWindow.open(mMarker); //인포윈도우는 혼자 못뜨기 떄문에 마커와 연결



        }//맵레디 끝
}