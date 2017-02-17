package com.ange.db.table;

import android.database.Cursor;

import com.ange.db.Db;

import rx.functions.Func1;

/**
 * Created by Administrator on 2016/10/15.
 */

public class PersonAndPosition {
    public static final Func1<Cursor, PersonAndPosition> RXMAPPER=new Func1<Cursor, PersonAndPosition>() {
        @Override
        public PersonAndPosition call(Cursor cursor) {
            Position position=Position.FACTORY.creator.create(Db.getInt(cursor,Position.PID),Db.getString(cursor,Position.PNAME));
            Person person=Person.FACTORY.creator.create(Db.getInt(cursor,Person._ID),Db.getString(cursor,Person.NAME),
                    Db.getInt(cursor,Person.PID));
            PersonAndPosition personAndPosition=new PersonAndPosition();
            personAndPosition.setPerson(person);
            personAndPosition.setPosition(position);
            return personAndPosition;
        }
    };
    private Person person;
    private Position position;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
