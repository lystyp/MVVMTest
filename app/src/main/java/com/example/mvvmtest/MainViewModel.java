package com.example.mvvmtest;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel {

    private DataModel dataModel = new DataModel();
    public final MutableLiveData<Integer> mStringData;
    public final ObservableBoolean isLoading;

    public MainViewModel(@NonNull final Application application) {
        super(application);
        mStringData = new MutableLiveData<>();
        isLoading = new ObservableBoolean(false);

    }


    public void refresh() {
        isLoading.set(true);
        dataModel.retrieveData(new DataModel.onDataReadyCallback() {
            @Override
            public void onDataReady(int data) {
                mStringData.setValue(data);
                isLoading.set(false);
            }
        });
    }
}