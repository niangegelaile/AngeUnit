package com.example.ange.module.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ange.utils.ActivityUtils;
import com.example.ange.R;
import com.example.ange.ViewModelFactory;
import com.example.ange.module.add.AddActivity;


/**
 * Created by niangegelaile on 2017/12/31.
 */

public class ListActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(),obtainViewFragment(),R.id.contentFrame);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


    }

    public static ListViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        ListViewModel viewModel =
                ViewModelProviders.of(activity, factory).get(ListViewModel.class);

        return viewModel;
    }
    @NonNull
    private ListFragment obtainViewFragment() {
        // View Fragment
        ListFragment fragment = (ListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (fragment == null) {
            fragment = ListFragment.newInstance();
        }
        return fragment;
    }
}
