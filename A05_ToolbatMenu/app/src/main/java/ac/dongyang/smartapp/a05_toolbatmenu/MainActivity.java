package ac.dongyang.smartapp.a05_toolbatmenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 툴바
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //플로팅액션버튼
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "동양미래대학교 입학처", Snackbar.LENGTH_INDEFINITE)
                        //밀기전에 사라지지 않도록
                        .setAction("전화걸기", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01037257248"));
                                //암시적 인텐트(외부앱으로)
                                startActivity(intent);
                            }
                        }).show();
                // 셋액션으로 이벤트 등록
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //메뉴인플레이터 this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//(메뉴구성 리소스,메뉴객체)
        return true;//메뉴 구성 성공시
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            Toast.makeText(getApplicationContext(),"전화걸기",Toast.LENGTH_LONG).show();

            return true;
        }
        else if(id==R.id.action_sms){
            Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:010000000"));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    } //옵션 선택 메소드 끝

    //메뉴항목 하나에 두개의 처리기지만 충돌안함, 온클릭만이 우선적으로 처리됩니다
    public void onClickMenuInfo(MenuItem item){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01000000000"));
        startActivity(intent);
    }

}
