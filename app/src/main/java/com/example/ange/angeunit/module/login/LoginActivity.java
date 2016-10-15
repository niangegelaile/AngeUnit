package com.example.ange.angeunit.module.login;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ange.angeunit.R;
import com.example.ange.angeunit.base.BaseActivity;
import com.example.ange.angeunit.base.RxBus;
import com.example.ange.angeunit.db.Db;
import com.example.ange.angeunit.db.DbOpenHelper;
import com.example.ange.angeunit.db.table.Person;
import com.example.ange.angeunit.app.ComponentHolder;
import com.example.ange.angeunit.db.table.PersonAndPosition;
import com.example.ange.angeunit.db.table.Position;
import com.squareup.sqlbrite.BriteDatabase;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;


/**
 * Created by Administrator on 2016/10/1.
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {
    @Inject
    LoginPresenter presenter;
    @Inject
    BriteDatabase db;
    @Inject
    DbOpenHelper openHelper;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.but_login)
    Button butLogin;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.lv)
    ListView lv;
    PersonAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ComponentHolder.getAppComponent().inject(this);
        register();
        adapter=new PersonAdapter(this,R.layout.item_lv_person);
        lv.setAdapter(adapter);
    }

    private void register() {
        Subscription sb = RxBus.getDefault().toObservable(String.class).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                tvMsg.setText(s);
            }
        });
        subscriptions.add(sb);
//        db.createQuery("person","SELECT * FROM person")
//                .mapToList(Person.RXMAPPER)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<List<Person>>() {
//            @Override
//            public void call(List<Person> persons) {
//                adapter.setDatas(persons);
//            }
//        });
        db.createQuery(Arrays.asList("person","position"),"SELECT * from person a,position b where a.pid = b.pid")
                .mapToList(PersonAndPosition.RXMAPPER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<PersonAndPosition>>() {
                    @Override
                    public void call(List<PersonAndPosition> personAndPositions) {
                        adapter.setDatas(personAndPositions);
                    }
                });
    }

    @OnClick(R.id.but_login)
    void login() {
        String name = etAccount.getText().toString();
        String position = etPass.getText().toString();
//        presenter.login(mob, pass);
        if(TextUtils.isEmpty(name)||TextUtils.isEmpty(position)){
            return;
        }
        long pid=db.insert(Position.TABLE_NAME,Position.FACTORY.marshal().pname(position).asContentValues());
        long id = db.insert(Person.TABLE_NAME, Person.FACTORY.marshal().phone("").name(name).pid(pid).asContentValues());
        Toast.makeText(this, String.valueOf(id), Toast.LENGTH_SHORT).show();
    }


}
