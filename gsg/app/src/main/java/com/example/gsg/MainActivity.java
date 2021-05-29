package com.example.gsg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.SoftReference;

public class MainActivity extends AppCompatActivity {

    private EditText edit1,edit2;
    private TextView re;





    @Override
    protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            edit1 = findViewById(R.id.editTextNumber);
            edit2 = findViewById(R.id.editTextNumber2);
            re = findViewById(R.id.textView3);


            View.OnClickListener add = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str1 = edit1.getText().toString();
                    String str2 = edit2.getText().toString();
                    re.setText("");

                    if (str1.isEmpty() || str2.isEmpty()) return;


                    int inte1 = Integer.parseInt(str1);
                    int inte2 = Integer.parseInt(str2);

                    int res = inte1 + inte2;

                    re.setText(String.valueOf(res));


                    //

                    int nre= 0;
                    switch(v.getId()){
                        case R.id.
                    }
                }
            };


        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}