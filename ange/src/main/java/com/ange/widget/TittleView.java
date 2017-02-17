package com.ange.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ange.R;


/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class TittleView extends RelativeLayout {
    private Context mContext;
    private String tittle;
    private boolean canBack;
    private boolean canSave;
    private RelativeLayout mRlBack;
    private TextView mTvSave;
    private TextView mTvTittle;
    public TittleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        parseAttr(attrs);
        initView();
    }

    private void initView() {
       View view= LayoutInflater.from(mContext).inflate(R.layout.view_title,this);
        mRlBack= (RelativeLayout) view.findViewById(R.id.rl_title_left);
        mTvSave= (TextView) view.findViewById(R.id.tv_title_right);
        mTvTittle= (TextView) view.findViewById(R.id.tv_title);
        if(canBack){
            mRlBack.setVisibility(VISIBLE);
        }
        if(canSave){
            mTvSave.setVisibility(VISIBLE);
        }
        mTvTittle.setText(tittle);
    }

    private void parseAttr(AttributeSet attrs) {
        TypedArray typedArray=mContext.obtainStyledAttributes(attrs, R.styleable.TittleView);
        tittle= typedArray.getString(R.styleable.TittleView_tittle_title);
        canBack=typedArray.getBoolean(R.styleable.TittleView_tittle_back,false);
        canSave=typedArray.getBoolean(R.styleable.TittleView_tittle_save,false);
        typedArray.recycle();
    }
}
