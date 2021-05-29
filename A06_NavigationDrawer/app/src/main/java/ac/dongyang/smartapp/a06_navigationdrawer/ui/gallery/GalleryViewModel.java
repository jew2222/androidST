package ac.dongyang.smartapp.a06_navigationdrawer.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }
    //뷰모델은 프래그먼트에서 사용 할 데이터를 저장해서 프래그간에 데이터를 주고 받을 수 있다


    public LiveData<String> getText() {
        return mText;
    }
}