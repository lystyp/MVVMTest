package com.example.mvvmtest;

import android.os.Handler;

public class DataModel {
    private int mData = 0;

    public void retrieveData(final onDataReadyCallback callback) {
        mData = mData + 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onDataReady(mData);
            }
        }, 5000);
    }

    interface onDataReadyCallback {
        void onDataReady(int data);
    }
}