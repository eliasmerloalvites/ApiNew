package cfsuman.android.chaskii.com.apinew.ui.categoria.ui;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "" + input;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index+1);
    }

    public LiveData<String> getText() {
        return mText;
    }
}