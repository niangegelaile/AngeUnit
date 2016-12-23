package com.example.ange.angeunit.db.table;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompatBase;
import android.support.v4.media.session.PlaybackStateCompat;

import com.example.ange.angeunit.db.Db;
import com.google.auto.value.AutoValue;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/10/15.
 */
@AutoValue
public  abstract class Position implements PositionModel {

    public final static Factory<Position> FACTORY=new Factory<>(new Creator<Position>() {
        @Override
        public Position create(long pid, @NonNull String pname) {
            return new AutoValue_Position(pid,pname);
        }
    });

    public final static Func1<Cursor,Position> RXMAPPER=new Func1<Cursor, Position>() {
        @Override
        public Position call(Cursor cursor) {
            return FACTORY.creator.create(Db.getInt(cursor,PID),Db.getString(cursor,PNAME));
        }
    };
    public static final Mapper<Position> MAPPER=new Mapper<>(FACTORY);



}
