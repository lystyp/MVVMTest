package com.example.mvvmtest.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmtest.model.DataModel;
import com.example.mvvmtest.model.Repo;

import java.util.List;

public class RepoViewModel extends ViewModel {
    final private String TAG = "RepoViewModel";

    private DataModel mDataModel;
    public final MutableLiveData<Boolean> isLoading;
    private final MutableLiveData<List<Repo>> repos;

    public RepoViewModel(DataModel dataModel) {
        mDataModel = dataModel;
        repos = new MutableLiveData<>();
        isLoading = new MutableLiveData<>(false);
    }


    public void searchRepo(String s) {
        isLoading.setValue(true);
        mDataModel.searchRepo(s, new DataModel.onDataReadyCallback() {
            @Override
            public void onDataReady(List<Repo> data) {
                isLoading.setValue(false);
                Log.d(TAG, "onDataReady, data length = " + data.size());
                repos.setValue(data);
                for (Repo r : data) {
                    Log.d(TAG, "onDataReady: " + r.name);
                }
            }
        });
    }

    public LiveData<List<Repo>> getRepos() {
        return repos;
    }
}