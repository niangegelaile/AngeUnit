package com.example.ange.angeunit.module.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ange.angeunit.R;
import com.example.ange.angeunit.base.BaseActivity;
import com.example.ange.angeunit.base.RxBus;
import com.example.ange.angeunit.db.Account;
import com.example.ange.angeunit.db.DbOpenHelper;
import com.example.ange.angeunit.dragger.ComponentHolder;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2016/10/1.
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {
    @Inject
    LoginPresenter presenter;
    @Inject
    SqlBrite sqlBrite;
    @Inject
    DbOpenHelper openHelper;
    @Bind(R.id.et_account)
    EditText etAccount;
    @Bind(R.id.et_pass)
    EditText etPass;
    @Bind(R.id.but_login)
    Button butLogin;
    @Bind(R.id.tv_msg)
    TextView tvMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        DaggerLoginComponent.builder()
//                .appComponent(ComponentHolder.getAppComponent())
//                .loginModule(new LoginModule())
//                .build().inject(this);
         ComponentHolder.getAppComponent().inject(this);
        register();
    }

    private void register() {
       Subscription sb= RxBus.getDefault().toObservable(String.class).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                tvMsg.setText(s);
            }
        });
        subscriptions.add(sb);
    }

    @OnClick(R.id.but_login)
    void login() {
        String mob = etAccount.getText().toString();
        String pass = etPass.getText().toString();
        presenter.login(mob, pass);
        BriteDatabase db =sqlBrite.wrapDatabaseHelper(openHelper, Schedulers.io());
       long id= db.insert(Account.TABLE_NAME,new Account.Builder().mob(mob).pass(pass).build());
        Toast.makeText(this,String.valueOf(id),Toast.LENGTH_SHORT).show();
    }


}
