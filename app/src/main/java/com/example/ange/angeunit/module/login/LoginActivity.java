package com.example.ange.angeunit.module.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ange.base.BaseActivity;
import com.ange.base.RxBus;
import com.ange.db.table.PersonAndPosition;
import com.ange.utils.SubscriptionCollectUtil;
import com.example.ange.angeunit.app.ComponentHolder;
import com.example.ange.angeunit.app.MyApplication;
import com.example.ange.angeunit.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;




/**
 * 首页:使用dagger注入presenter,
 * Created by liquanan on 2016/10/1.
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    @Inject
    LoginPresenter presenter;

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
        bindEvent();
        register();
        adapter=new PersonAdapter(this,R.layout.item_lv_person);
        lv.setAdapter(adapter);
    }



    @Override
    protected void acceptIntent(Intent intent) {

    }

    @Override
    protected void buildComponentForInject() {
        ComponentHolder.getAppComponent().activityComponent(new LoginModule(this)).inject(this);
    }


    private void bindEvent() {
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               PersonAndPosition pp= adapter.getItem(i);
                tip(pp.getPerson()._id());
                return false;
            }
        });
    }


    private void register() {
        Subscription sb = RxBus.getDefault().toObservable(String.class).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                tvMsg.setText(s);
            }
        });
        SubscriptionCollectUtil.push(sb);
        presenter.queryPersonPosition();

    }

    @OnClick(R.id.but_login)
    void login() {
        String name = etAccount.getText().toString();
        String position = etPass.getText().toString();
        presenter.insertInfo(name,position);
    }

    void tip(final long id) {
        new AlertDialog.Builder(this)
                .setTitle("删除提示")
                .setView(getLayoutInflater().inflate(getDialogRes(), null))
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.deleteInfo(id);
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


    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter= (LoginPresenter) presenter;
    }

    @Override
    public void onNoNetWork() {

    }


    @Override
    public void setPersonInfoView(List<PersonAndPosition> personAndPositions) {
        adapter.setDatas(personAndPositions);
    }
}
