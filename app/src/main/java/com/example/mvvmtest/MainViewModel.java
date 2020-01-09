package com.example.mvvmtest;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

public class MainViewModel {

    private DataModel dataModel = new DataModel();
    public final ObservableField<String> mStringData = new ObservableField<>();
    public final ObservableBoolean isLoading = new ObservableBoolean(false);


    public void refresh() {
        isLoading.set(true);
        dataModel.retrieveData(new DataModel.onDataReadyCallback() {
            @Override
            public void onDataReady(String data) {
                mStringData.set(data);
                isLoading.set(false);
            }
        });
    }
}