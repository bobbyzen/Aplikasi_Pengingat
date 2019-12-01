package com.bzen.aplikasi_pengingat.ui.pengingat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PengingatViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PengingatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}