package com.example.mvvmtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mvvmtest.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private MainViewModel mViewModel;
    private ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mActivityMainBinding.setViewModel(mViewModel);

        mViewModel.mStringData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d(TAG, "onChanged: " + integer);
                mActivityMainBinding.txtHelloWord.setText(Integer.toString(integer));
                Toast.makeText(MainActivity.this, "下載完成", Toast.LENGTH_SHORT).show();
            }
        });
    }
}