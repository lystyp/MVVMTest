package com.example.mvvmtest.view;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mvvmtest.databinding.RepoFragmentBinding;
import com.example.mvvmtest.model.Repo;
import com.example.mvvmtest.viewmodel.GithubViewModelFactory;
import com.example.mvvmtest.viewmodel.RepoViewModel;

import java.util.ArrayList;
import java.util.List;


public class RepoFragment extends Fragment {
    public static final String TAG = "RepoFragment";

    private RepoFragmentBinding mBinding;
    private RepoViewModel mViewModel;
    private GithubViewModelFactory mFactory;
    private RepoAdapter mRepoAdapter;

    public static RepoFragment newInstance() {

        Bundle args = new Bundle();

        RepoFragment fragment = new RepoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = RepoFragmentBinding.inflate(inflater, container, false);
        mFactory = new GithubViewModelFactory();
        mRepoAdapter = new RepoAdapter(new ArrayList<Repo>());


        mBinding.edtQuery.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    doSearch();
                    return true;
                }
                return false;
            }
        });

        mBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch();
            }
        });

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerView.setAdapter(mRepoAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, mFactory).get(RepoViewModel.class);
        mBinding.setViewModel(mViewModel);
        mBinding.setLifecycleOwner(this);
        mViewModel.getRepos().observe(this, new Observer<List<Repo>>() {
            @Override
            public void onChanged(@Nullable List<Repo> repos) {
                mRepoAdapter.swapItems(repos);
                mViewModel.isLoading.setValue(false);
            }
        });
    }

    private void doSearch() {
        mViewModel.isLoading.setValue(true);
        String query = mBinding.edtQuery.getText().toString();
        if (TextUtils.isEmpty(query)) {
            mRepoAdapter.clearItems();
            return;
        }
        mViewModel.searchRepo(query);
        dismissKeyboard();
    }

    private void dismissKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}