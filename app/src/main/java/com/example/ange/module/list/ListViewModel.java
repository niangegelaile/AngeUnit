package com.example.ange.module.list;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.ange.db.IDB;
import com.example.ange.app.Repository;
import com.example.ange.db.PersonAndPosition;

import java.util.Arrays;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by niangegelaile on 2017/12/31.
 */

public class ListViewModel extends AndroidViewModel{

    private final Repository repository;
    private final IDB mDb;
    public final ObservableField<List<PersonAndPosition>> items=new ObservableField<>();
    public ListViewModel(@NonNull Application application,Repository repository) {
        super(application);
        this.repository=repository;
        this.mDb=this.repository.getDb();
    }


    public void loadList(){
                mDb.getBriteDatabase().createQuery(Arrays.asList("person","position"),"SELECT * from person a,position b where a.pid = b.pid")
                .mapToList(PersonAndPosition.RXMAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PersonAndPosition>>() {
                    @Override
                    public void call(List<PersonAndPosition> personAndPositions) {
                        items.set(personAndPositions);
                    }
                });
    }





}
