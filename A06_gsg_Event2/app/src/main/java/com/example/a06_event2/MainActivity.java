package com.example.a06_event2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber1, editTextNumber2;
    private TextView textResult;


    //이름있는 클래스 사용
    class AddListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String sNumber1 = editTextNumber1.getText().toString();
            String sNumber2 = editTextNumber2.getText().toString();

            textResult.setText("");
            if (sNumber1.isEmpty() || sNumber2.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"숫자를 올바르게 입력해주세요.",Toast.LENGTH_SHORT).show();
                return;
            }

            int nNum1 = Integer.parseInt(sNumber1);
            int nNum2 = Integer.parseInt(sNumber2);
            int nResult = nNum1+nNum2;

            textResult.setText(String.valueOf(nResult));
        }
    }



    //onClick 속성 콜백메소드 사용 : 리스너가 등록된 경우에는 리스너가 우선 처리되므로 이 메소드는 호출안됨
    public void onClickAdd(View v){
        String sNumber1 = editTextNumber1.getText().toString();
        String sNumber2 = editTextNumber2.getText().toString();

        textResult.setText("");
        if (sNumber1.isEmpty() || sNumber2.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"숫자를 입력해주세요.",Toast.LENGTH_SHORT).show();
            return;
        }

        int nNum1 = Integer.parseInt(sNumber1);
        int nNum2 = Integer.parseInt(sNumber2);
        int nResult = nNum1+nNum2;

        textResult.setText(String.valueOf(nResult));
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        textResult =findViewById(R.id.textViewResult);


        //이름있는 클래스로 이벤트 처리
        /*
        AddListener addListener = new AddListener();

        Button btnAdd = findViewById(R.id.buttonAdd);
        btnAdd.setOnClickListener(addListener);
         */
        //익명클래스 첫번째 방법
        Button btnAdd =findViewById(R.id.buttonAdd);
        View.OnClickListener addListener2= new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String sNumber1 = editTextNumber1.getText().toString();
                String sNumber2 = editTextNumber2.getText().toString();

                textResult.setText("");
                if (sNumber1.isEmpty() || sNumber2.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"숫자를 입력해주세요.(익명클래스1)",Toast.LENGTH_SHORT).show();
                    return;
                }

                int nNum1 = Integer.parseInt(sNumber1);
                int nNum2 = Integer.parseInt(sNumber2);
               // int nResult = nNum1+nNum2;
                int nResult =0;

                switch(v.getId()){
                    case R.id.buttonAdd :
                        nResult= nNum1+nNum2;
                        break;
                    case R.id.buttonSub:
                        nResult= nNum1- nNum2;
                        break;
                    case R.id.buttonMul:
                        nResult= nNum1*nNum2;
                    break;
                    case R.id.buttonDiv:
                        nResult= nNum1/nNum2;
                        break;

                }

                textResult.setText(String.valueOf(nResult));
            }

        };

        btnAdd.setOnClickListener(addListener2);

        Button btnSub =findViewById(R.id.buttonSub);
        Button btnDiv =findViewById(R.id.buttonDiv);
        Button btnMul =findViewById(R.id.buttonMul);




        btnSub.setOnClickListener(addListener2);
        btnDiv.setOnClickListener(addListener2);
        btnMul.setOnClickListener(addListener2);
        /*
        View.OnClickListener subListener =new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String sNumber1 = editTextNumber1.getText().toString();
                String sNumber2 = editTextNumber2.getText().toString();

                textResult.setText("");
                if (sNumber1.isEmpty() || sNumber2.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"숫자를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                int nNum1 = Integer.parseInt(sNumber1);
                int nNum2 = Integer.parseInt(sNumber2);
                int nResult = nNum1-nNum2;

                textResult.setText(String.valueOf(nResult));
            }


        };
        btnSub.setOnClickListener(subListener);
        */

        //익명 클래스 처리방법 2번째 : 생성 구현 등록을 동시에 수행
        Button btnClear = findViewById(R.id.buttonClear);
        btnClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editTextNumber1.setText("");
                editTextNumber2.setText("");
                textResult.setText("");
            }
        });
    }
}