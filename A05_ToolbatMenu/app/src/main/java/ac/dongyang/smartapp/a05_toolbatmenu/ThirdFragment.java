package ac.dongyang.smartapp.a05_toolbatmenu;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    //앞에서 이동시 put 된 데이터를 담는 용도

    private String mParam1;
    //넘겨 받는 데이터 담는 용도


    public ThirdFragment() {
        // Required empty public constructor
        //생성자
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance(String param1) {
        //third프래그먼트 타입의 객체를 반환(생성)해 주는 메소드
        //뉴인스텐스로 받은 값을 번들에다 저장한 객체
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1); // 키/밸루
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1); //왜 매개변수로 키값이 들어가지?
            //데이터(사용자입력 이름) 받음
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //텍뷰 클릭시 1번 프래그먼트로 돌아가도록 (백스텍의 세컨프래그도 삭제해야함)

        TextView textView = view.findViewById(R.id.cong_msg);
        String msg = getString(R.string.cong_msg1)+ mParam1 +getString(R.string.cong_msg2);
        //번들 데이터로 축하메시지 출력

        textView.setText(msg);

        textView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //기능:클릭시 이전화면으로 넘어가도록
                NavHostFragment.findNavController(ThirdFragment.this).popBackStack(R.id.FirstFragment,false);
                //false 의미는 돌아가는 펄스트 프래그먼트도 백스텍에서 제거하느냐(아님)
            }
        });

        //백버튼 수정: 액티비티를 통해 백버튼 이벤트 처리기를 가로채야함
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        });
       //requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) { //애드컬백은 등록
           // true는 백버튼 처리가 처음부터 유효한지 중간 세팅부터 유효한지 여부
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(ThirdFragment.this).popBackStack(R.id.FirstFragment,false);
           }
       });

    }
}