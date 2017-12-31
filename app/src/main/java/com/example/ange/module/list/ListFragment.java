package com.example.ange.module.list;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ange.R;
import com.example.ange.databinding.FragmentListBinding;
import com.example.ange.module.add.PersonAdapter;

/**
 * Created by niangegelaile on 2017/12/31.
 */

public class ListFragment extends Fragment {
    private PersonAdapter adapter;
    private FragmentListBinding fragmentListBinding;
    private ListViewModel listViewModel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_list, container, false);
        if(fragmentListBinding==null){
            fragmentListBinding=FragmentListBinding.bind(root);
        }
        listViewModel=ListActivity.obtainViewModel(getActivity());
        fragmentListBinding.setViewmodel(listViewModel);

        setRetainInstance(false);

        return fragmentListBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupListView();
        listViewModel.loadList();
    }

    private void setupListView() {
        adapter=new PersonAdapter(getActivity(),R.layout.item_lv_person);
        fragmentListBinding.lv.setAdapter(adapter);
        listViewModel.items.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                adapter.setDatas(listViewModel.items.get());
            }
        });
    }


    public static ListFragment newInstance() {
        return new ListFragment();
    }
}
