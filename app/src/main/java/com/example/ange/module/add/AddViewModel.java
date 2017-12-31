package com.example.ange.module.add;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.ange.db.Db;
import com.ange.db.IDB;
import com.example.ange.app.Repository;
import com.example.ange.db.Person;
import com.example.ange.db.Position;

import java.util.List;

/**
 * Created by niangegelaile on 2017/12/31.
 */

public class AddViewModel extends AndroidViewModel{

    private final static String TAG= "AddViewModel";

    private final Repository repository;

    private final IDB mDb;

    public final ObservableField<String> name = new ObservableField<>();

    public final ObservableField<String> position = new ObservableField<>();


    public AddViewModel(@NonNull Application application,Repository repository) {
        super(application);
        this.repository=repository;
        mDb=this.repository.getDb();
    }

    public void saveRecord() {
        Log.e(TAG,"name:"+name.get());
        Log.e(TAG,"position:"+position.get());
        if(TextUtils.isEmpty(name.get())||TextUtils.isEmpty(position.get())){
            return;
        }
        List<Position> positions=null;
        positions= mDb.find(Position.class,"select * from position where "+ Position.PNAME+" = "+"\""+position+"\"");
        long pid;
        if(positions==null||positions.size()<1){
            pid=mDb.add(Position.TABLE_NAME,Position.FACTORY.marshal().pname(position.get()).asContentValues());
        }else {
            pid= positions.get(0).pid();
        }
        long id = mDb.add(Person.TABLE_NAME, Person.FACTORY.marshal().name(name.get()).pid(pid).asContentValues());
        Log.e(TAG,"id:"+id);
    }
}
