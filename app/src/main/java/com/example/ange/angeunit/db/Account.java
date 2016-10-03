package com.example.ange.angeunit.db;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;


/**
 * Created by Administrator on 2016/10/3.
 */
@AutoValue
public abstract class Account implements Parcelable {

    public static final String TABLE_NAME="account";
    public static final String ID="_id";
    public static final String MOB="mob";
    public static final String PASS="pass";

    public abstract long id();
    public abstract String mob();
    public abstract String pass();

    public static final class Builder{
            private final ContentValues contentValues=new ContentValues();
        public Builder id(long id){
            contentValues.put(ID,id);
            return this;
        }

        public Builder mob(String mob){
            contentValues.put(MOB,mob);
            return this;
        }

        public Builder pass(String pass){
            contentValues.put(PASS,pass);
            return this;
        }

        public ContentValues build(){
            return contentValues;
        }
    }


}
