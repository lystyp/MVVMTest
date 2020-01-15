package com.example.mvvmtest.viewmodel;

import android.text.TextUtils;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mvvmtest.model.DataModel;
import com.example.mvvmtest.model.Repo;
import com.example.mvvmtest.utils.AbsentLiveData;

import java.util.List;

public class RepoViewModel extends ViewModel {
    final private String TAG = "RepoViewModel";

    private DataModel mDataModel;
    public final MutableLiveData<Boolean> isLoading;
    private final MutableLiveData<String> mQuery;
    private final LiveData<List<Repo>> repos;

    public RepoViewModel(final DataModel dataModel) {
        mDataModel = dataModel;
        isLoading = new MutableLiveData<>(false);
        mQuery = new MutableLiveData<>();
        repos = Transformations.switchMap(mQuery, new Function<String, LiveData<List<Repo>>>() {
            @Override
            public LiveData<List<Repo>> apply(String userInput) {
                if (TextUtils.isEmpty(userInput)) {
                    return AbsentLiveData.create();
                } else {
                    return mDataModel.searchRepo(userInput);
                }
            }
        });

    }


    public void searchRepo(String s) {
        mQuery.setValue(s);
    }

    public LiveData<List<Repo>> getRepos() {
        return repos;
    }
}