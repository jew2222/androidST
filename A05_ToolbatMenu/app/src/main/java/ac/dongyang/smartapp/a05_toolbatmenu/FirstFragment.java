package ac.dongyang.smartapp.a05_toolbatmenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment { //프래그먼트 상속

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 액티비티 안이 아닌 프래그먼트라 메소드를 사용불가해서 최상위 View 를 통해 버튼 가져옴
//        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//                //온클릭 이벤트에서 프래그먼트간 탐색
//            }
//        });
        //프래그먼트는 온클릭속성 불가
        //기능: 정시,수시 합격 확인 클릭시 second 프래그먼트로 이동
        //익명처리 . 처리기 공유

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle(); //인텐트 역 번들
                bundle.putInt("type",v.getId());

                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
                //번들도 매개변수로 넣어줌
            }
        };

        view.findViewById(R.id.textViewSusi).setOnClickListener(ocl);
        view.findViewById(R.id.textViewJungsi).setOnClickListener(ocl);
    }
}