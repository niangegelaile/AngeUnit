package com.example.ange.module.list;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.ange.base.RxBus;
import com.ange.db.IDB;
import com.example.ange.app.Repository;
import com.example.ange.db.Person;
import com.example.ange.db.PersonAndPosition;
import com.example.ange.module.add.PersonNew;

import java.util.Arrays;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by niangegelaile on 2017/12/31.
 */

public class ListViewModel extends AndroidViewModel{

    private final Repository repository;
    private final IDB mDb;
    public final ObservableField<List<PersonAndPosition>> items=new ObservableField<>();
    public final ObservableField<String> person=new ObservableField<>();
    public MutableLiveData<Boolean> refresh=new MutableLiveData<>();
    Subscription sb;
    public ListViewModel(@NonNull Application application,Repository repository) {
        super(application);
        this.repository=repository;
        this.mDb=this.repository.getDb();
        //测试LiveData
        sb= RxBus.getDefault().toObservable(PersonNew.class).subscribe(new Subscriber<PersonNew>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(PersonNew personAndPosition) {
                refresh.setValue(true);
            }
        });
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

    public void deleteInfo(long id) {
        mDb.delete(Person.TABLE_NAME,Person._ID +" = ?",String.valueOf(id));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        sb.unsubscribe();
    }
}
