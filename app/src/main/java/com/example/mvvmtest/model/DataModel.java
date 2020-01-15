package com.example.mvvmtest.model;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmtest.api.GithubService;
import com.example.mvvmtest.api.RetrofitManager;
import com.example.mvvmtest.model.Repo;
import com.example.mvvmtest.model.RepoSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataModel {
    private final String TAG = "DataModel";

    private GithubService githubService = RetrofitManager.getAPI();

    public MutableLiveData<List<Repo>> searchRepo(String query) {
        final MutableLiveData<List<Repo>> repos = new MutableLiveData<>();
        githubService.searchRepos(query)
                .enqueue(new Callback<RepoSearchResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<RepoSearchResponse> call, @NonNull Response<RepoSearchResponse> response) {
                        repos.setValue(response.body().getItems());
                    }

                    @Override
                    public void onFailure(@NonNull Call<RepoSearchResponse> call, @NonNull Throwable t) {
                        // TODO: error handle
                    }
                });
        return repos;
    }
}