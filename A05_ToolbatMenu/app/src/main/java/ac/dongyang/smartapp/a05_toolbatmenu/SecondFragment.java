package ac.dongyang.smartapp.a05_toolbatmenu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
//            }
//        });

        //first프래그먼트 네비컨트롤러를 받는다
        Bundle bundle = getArguments();
        if (bundle != null) {
            int nId = bundle.getInt("type");

            TextView textView = view.findViewById(R.id.textView); //상단 합격자 확인 타이틀부분
            //여기서 뷰는 View가 아닌 view 뷰 로 적어야 오류가 안남

            if (nId == R.id.textViewSusi) {
                textView.setText(R.string.susi);
                textView.setBackgroundColor(Color.rgb(0x21, 0x96, 0xF3));
            } else {
                textView.setText(R.string.jungsi);
                textView.setBackgroundColor(Color.rgb(0x4C, 0xAF, 0x50));
            }
        }

            Button btnCancel = view.findViewById(R.id.buttonCancel);
            btnCancel.setOnClickListener(new View.OnClickListener(){
                @Override
                    public void onClick(View v) {
                    //기능 : 취소버튼을 누르면 1번 프래그먼트로 이동
                     NavController navCon = NavHostFragment.findNavController(SecondFragment.this);
                     navCon.popBackStack();
                     //백버튼과 같은 효과 (백스택을 꺼내고 지금 세컨드는 제거)
                }
            });

        final EditText editNo = view.findViewById(R.id.editTextNumber);
         final EditText editName = view.findViewById(R.id.editTextName);
        //아래 익명클래스의 메소드 안에서 지역변수 접근하기 위해선 final

        Button btnOk = view.findViewById(R.id.buttonOk);
        btnOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //기능: 확인버튼 누르면 third 로 이름 보내기
                String sName = editName.getText().toString().trim();
                String sNo = editNo.getText().toString().trim();//스페이스제거

                if (sName.isEmpty()|| sNo.isEmpty()){
                    Toast.makeText(getActivity(),R.string.input,Toast.LENGTH_LONG).show();
                    return;
                      //*여기는 액티비티가 아닌 프래그먼트라서getActivity
                }
                Bundle bundle = new Bundle();
                bundle.putString("name",sName);

                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_thirdFragment,bundle);
            }
        });
        }//뷰크리에잇티드
}