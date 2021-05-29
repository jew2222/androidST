package ac.dongyang.smartapp.a06_navigationdrawer;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //아래 담당자 연결을 알리기 위한 코드
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
         // Passing each menu ID as a set of Ids because each
         // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.lectureFragment, R.id.noticeFragment, R.id.msgFragment)
                .setDrawerLayout(drawer)//구번전이며 신버전은 setOpenableLayout(drawer)
                .build();
                         //앱바에 최상단 페이지 설정, 앱바에 줄세개 버튼을 나타낼지 말지 결정 가능
                           //빌더에 넣은 (화면들)은 이동시 줄세개 버튼이 보이지만(메뉴선택), 나머진 이전 버튼(홈버튼)
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
                           //프래그먼트가 아닌 액티비티라 Navigation.findNavController(액티빝티,네브호스트프래그먼)
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
         //담당자를 서로 엮어 알게하는 메소드들
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //이 메소드가 없으면 메뉴가 생성이 안된다
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        //줄세개 버튼 클릭시 네비뷰 |이전화면 동작시키는 메소드
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}