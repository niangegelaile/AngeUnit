package com.example.ange.angeunit.db.table;

import android.database.Cursor;
import android.support.annotation.NonNull;


import com.example.ange.angeunit.db.Db;
import com.google.auto.value.AutoValue;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/10/9.
 */
@AutoValue
public abstract class Person implements PersonModel {
    public final static Factory<Person> FACTORY=new Factory<>(new Creator<Person>() {
        @Override
        public Person create(long _id, @NonNull String name, long pid) {
            return new AutoValue_Person(_id,name,pid);
        }
    });
    public final static Func1<Cursor,Person> RXMAPPER=new Func1<Cursor, Person>() {
        @Override
        public Person call(Cursor cursor) {
            return FACTORY.creator.create(
                    Db.getInt(cursor,_ID),
                    Db.getString(cursor,NAME),
                    Db.getInt(cursor,PID));
        }
    };
}
