package com.example.mvvmtest.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvmtest.model.DataModel;

public class GithubViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private DataModel mDataModel;

    public GithubViewModelFactory() {
        mDataModel = new DataModel();
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RepoViewModel.class)) {
            return (T) new RepoViewModel(mDataModel);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}