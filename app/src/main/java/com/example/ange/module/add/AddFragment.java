package com.example.ange.module.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ange.R;
import com.example.ange.databinding.FragmentAddBinding;

/**
 * Created by niangegelaile on 2017/12/31.
 */

public class AddFragment extends Fragment {


    public static final String ARGUMENT_EDIT_TASK_ID ="ARGUMENT_EDIT_TASK_ID";

    private FragmentAddBinding mViewDataBinding;

    private AddViewModel mViewModel;

    public static AddFragment newInstance() {
        return new AddFragment();
    }

    public AddFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_add, container, false);
        if (mViewDataBinding == null) {
            mViewDataBinding = FragmentAddBinding.bind(root);
        }

        mViewModel = AddActivity.obtainViewModel(getActivity());

        mViewDataBinding.setViewmodel(mViewModel);

        setRetainInstance(false);

        return mViewDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupSave();
    }

    private void setupSave() {
        mViewDataBinding.butSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.saveRecord();
            }
        });

    }
}
