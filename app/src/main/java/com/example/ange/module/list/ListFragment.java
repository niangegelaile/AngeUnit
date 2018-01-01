package com.example.ange.module.list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

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
        fragmentListBinding.lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                tip(adapter.getItem(position).getPerson()._id());

                return false;
            }
        });
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


    void tip(final long id) {
        new AlertDialog.Builder(getActivity())
                .setTitle("删除提示")
                .setView(getLayoutInflater().inflate(getDialogRes(), null))
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listViewModel.deleteInfo(id);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    public int getDialogRes() {
        return R.layout.dialog_delete_tip;
    }
}
