package com.example.navermapapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
//프래그먼트 포함 액티비티 상속원은 프래그먼트 액티비티
    //온 맵레디 이벤트 처리 위한 implements

    NaverMap mMap;
    MapFragment mMapfragment;
    Marker mMarker, mMarkerl; //현재위치, 마지막위치
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
        mMapfragment.getMapAsync(MainActivity.this); //준비가 되면 아래 온맵레디 메소드 호출됨

    } //온크리에이트끝


    //2-4 반복때문에 새로 만드는 메소드
    @SuppressLint("MissingPermission")
    void getLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //높은 정확도
        locationRequest.setInterval(5 * 1000);  //밀리 단위로 초

        mFLPC.requestLocationUpdates(locationRequest, mLocationCallback, Looper.getMainLooper());
        //이벤트 처리기 등록
        //위에 로케이션 리퀘스트 와 세트로 붙여넣기 / 경고에 suppress 도 추가

        //마지막 위치 가져오는 부분
        mFLPC.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() { //성공여부를 귀기울여 듣기위한 이벤트리스너 등록
        @Override
        public void onSuccess (Location location){ //겟라스트로케이션 메소드가 성공시 호출됨

            if (location == null)
                return;

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMarkerl = new Marker();
            mMarkerl.setPosition(latLng);
            mMarkerl.setMap(mMap);
            mMarkerl.setCaptionText("마지막 위치");
            //현재위치에 따른 이동이라 마지막위치는 카메라 이동은 안합니다

        }
       });
    }

    //2-5 해제하기


    @Override
    protected void onDestroy() {
        super.onDestroy();
       if (mFLPC !=null) //통합위치관리자가 널이 아니면 이벤트 해제
           mFLPC.removeLocationUpdates(mLocationCallback);
    }

    //2-3 사용자 권한 허가 요청 후
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                       //인자:리퀘스트 코드, 권한 종류, 허가 여부도 배열로 받음
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode != 100)
            return;


        if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            //2개라 0말고 1보다 큰지 , 첫번쨰가 승인이 되었는지 확인
            getLocation();
//            LocationRequest locationRequest = LocationRequest.create();
//            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            locationRequest.setInterval(5 * 1000);
 //           mFLPC.requestLocationUpdates(locationRequest, mLocationCallback, Looper.getMainLooper());
                                 //   위에 로케이션 리퀘스트 와 세트로 붙여넣기 / 경고에 suppress 도 추가
        }

    } //온리퀘퍼미션 리절트 끝


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        mMap =naverMap;
        mMap.setSymbolScale(1f); //1f 는 표준 사이즈 (디펄트)
        mMap.setMapType(NaverMap.MapType.Basic);// 하이브리드 등의 타입 있음
        mMap.moveCamera(CameraUpdate.zoomTo(15)); //카메라 업데이트 객체.줌 투 메소드가 카메라업데이트 객체 반환


        //실습2-1 통합위치생성관리자
        mFLPC = LocationServices.getFusedLocationProviderClient(this); //인자: 액티비티

        //컬백객체 생성
        mLocationCallback = new LocationCallback() {//위치 정보 바뀔떄마다 이벤트 처리
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {//위치변경시마다 호출
                super.onLocationResult(locationResult);

                if (locationResult == null)
                    return;

                List<Location> list = locationResult.getLocations();

                if (list == null || list.size() == 0)
                    return;
                //결과가 있단 소리

                Location location = list.get(0); //첫번쨰 위치
                //마커에 뿌리기 위해 라트랜지  객체
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


                if (mMarker == null){  //계속 하나로 사용하기 위해
                    mMarker = new Marker();}

                    mMarker.setPosition(latLng);  //순서가 항상 셋 맵 위에 와야함
                    mMarker.setMap(mMap);

                    mMarker.setCaptionText("현재위치");

                    mMap.moveCamera(CameraUpdate.scrollTo(latLng));

            }
        };//컬백끝

        //시간 간격 , 정확도 설정을 한다

//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(5 * 1000);
        //반복되기 떄문에 메소드 만들겠습니다

        //2-2 이벤트 등록 mFLPC에
        //오류가 나는 이유는 권한 설정 사용자도 하도록 해야함 -add permission check 클릭
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권고된 주석처럼 리퀘스트퍼미션 메소드 호출해야합니다
             ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);
                 //인자: 액티비티, 권한2개라 배열,사용자가 허가 했는지 구분위한 리퀘스 코드(온 리퀘스트퍼미션리저트 메소드로 결과 받음)
                 //GPS 권한 하고, 네트워크 프로바이더 권한 허가 되었는지 체크 , 안되었으면 리퀘퍼미션() 을 호출해 사용자가 허용토록

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getLocation();
    //    mFLPC.requestLocationUpdates(locationRequest, mLocationCallback, Looper.getMainLooper());
                                          //  권한이 없으면 요것까지 내려오지 않음, 이것만하면 에러라 위 리퀘리절트



       /* //실습1
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

            */



        }//맵레디 끝
}