package com.ange.base;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ange.R;


/**
 * Created by Administrator on 2016/12/26 0026.
 */

public abstract class BaseFragment extends Fragment {
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
    }


    public void showTip(String msg) {
//        ToastUtil.show(msg);
    }
    public void showTip(String msg,int code){
        showTip(msg);
    }

    public void load(boolean flag){
        BaseActivity activity= (BaseActivity) getActivity();
        if(activity!=null){
            activity.loading(flag);
        }
    }
}
