package com.example.a07_activity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
public class PurchaseActivity extends AppCompatActivity {

    //상품을 몇개 사는지에 따라 다른 가격
    int nPrice, nTotalPrice; //하나의 가격, 합계
    TextView textProduct, textPrice;
    EditText editNumber;
    RadioGroup rgPayment, rgSize; //지불방식, 사이즈
    CheckBox cbPoint;

    int nProductNo;//어떤 이미지뷰 선택했는지

    public void onClickInc(View v){
        //에디트 넘버의 값을 증가하고
        //토탈 프라이스 값을 증가함
        String sNum = editNumber.getText().toString();  //에딧넘버 내용을 문자열로
        int nNum = Integer.parseInt(sNum); //문자열을 정수로
        nNum++;
        nTotalPrice = nPrice*nNum;

        editNumber.setText(String.valueOf(nNum));
        textPrice.setText("가격 : "+String.valueOf(nTotalPrice)+"원");
    }
    public void onClickDec(View v){
        String sNum = editNumber.getText().toString();
        int nNum = Integer.parseInt(sNum);
        if(nNum > 1) nNum--;
        nTotalPrice = nPrice * nNum;

        editNumber.setText(String.valueOf(nNum));
        textPrice.setText("가격 : "+String.valueOf(nTotalPrice)+"원");
   }
////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        textProduct = findViewById(R.id.textViewProduct);
        textPrice = findViewById(R.id.textViewPrice);
        editNumber = findViewById(R.id.editTextNumber);
        rgPayment = findViewById(R.id.rgPayment);
        rgSize = findViewById(R.id.rgSize);
        cbPoint = findViewById(R.id.checkPoint);

        Intent intentRtoP = getIntent();
        if (intentRtoP != null) {
            nProductNo = intentRtoP.getIntExtra("productNo", 1);
            //상품에 따라
            // 단위가격,토탈 가격,텍스트프로덕트뷰  달라져야함
            String sProduct = ""; //타이틀
            String sPrice = ""; //오른쪽 텍스트뷰
            //결과 넣을 자리

            switch (nProductNo) {
                case 1: //textProduct.setText("미마마스크"); 직접적
                    sProduct = "미마마스크";
                    nPrice = 990;
                    nTotalPrice = nPrice * 1;
                    sPrice = "가격 : " + nTotalPrice + "원";
                    break;
                case 2:
                    sProduct = "어린이용 마스크";
                    nPrice = 36000;
                    nTotalPrice = nPrice * 1;
                    sPrice = "가격 : " + nTotalPrice + "원";
                    break;
                case 3:
                    sProduct = "데일리 마스크";
                    nPrice = 1400;
                    nTotalPrice = nPrice * 1;
                    sPrice = "가격 : " + nTotalPrice + "원";
                    break;
            }
            textProduct.setText(sProduct);
            textPrice.setText(sPrice);
            editNumber.setText("1");

            rgPayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            //선택 라디오 버튼에 따른 토스트
                            String sPayment = "";

                    switch (checkedId) {
                        case R.id.radioCash:
                            sPayment = "결제방식 : 현금";
                            break;
                        case R.id.radioCredit:
                            sPayment = "결제방식 : 신용카드";
                            break;
                        case R.id.radioMobile:
                            sPayment = "결제방식 : 모바일";
                            break;
                    }
                    Toast.makeText(getApplicationContext(), sPayment, Toast.LENGTH_SHORT).show();
                }
            });

            rgSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    //체크드 아이디 를 통해 처리한다
                    String sSize = "";
                    switch (checkedId) {
                        case R.id.radioLarge:
                            sSize = "대";
                            break;
                        case R.id.radioMid:
                            sSize = "중";
                            break;
                        case R.id.radioSmall:
                            sSize = "소";
                            break;
                    }

                    Toast.makeText(getApplicationContext(), sSize, Toast.LENGTH_LONG).show();
                }
            });


            //11주
            cbPoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) //이스 테크드로 여부
                        Toast.makeText(getApplicationContext(), "포인트 적용", Toast.LENGTH_LONG).show();
                }
            });

            final CheckBox cbPack = findViewById(R.id.checkPack);
            cbPack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        Toast.makeText(getApplicationContext(), "선물용 포장", Toast.LENGTH_LONG).show();
                }
            });

            Button btnOk = findViewById(R.id.buttonOk);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String sPrice = textPrice.getText().toString();
                    String sResult = textProduct.getText().toString() + "\n"; //여러줄을 추가하기위해
                    //지불방식 라디오그룹
                    int nPayment = rgPayment.getCheckedRadioButtonId();
                    //사이즈 라디오그룹
                    int nSize = rgSize.getCheckedRadioButtonId();

                    switch (nPayment) {
                        case R.id.radioCash:
                            sResult += "결제방식 : 현금\n";
                            break;
                        case R.id.radioCredit:
                            sResult += "결제방식 : 카드\n";
                            break;
                        case R.id.radioMobile:
                            sResult += "결제방식 : 모바일\n";
                            break;
                    }

                    if (nSize == R.id.radioLarge) sResult += "크기 : 대\n";
                    else if (nSize == R.id.radioMid) sResult += "크기 : 중\n";
                    else if (nSize == R.id.radioSmall) sResult += "크기 : 소\n";

                    if (cbPoint.isChecked()) sResult += "포인트 적립\n";
                    if (cbPack.isChecked()) sResult += "선물용 포장\n";
                    sResult += sPrice;

                    Intent intentRb = new Intent();
                    intentRb.putExtra("result", sResult);  // 여러 조건문으로 열심히 여러 결괴 더해서 문장보냄
                    setResult(RESULT_OK, intentRb);
                    finish();
                }
            });
            Button btnCancel = findViewById(R.id.buttonCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_CANCELED);
                    finish();
                    //취소이기때문에 보낼 데이터 없음, 객체 생성도 필요가 없다
                }
            });

        }//인텐트 낫 눌
    } //온크리에잇끝
} //class