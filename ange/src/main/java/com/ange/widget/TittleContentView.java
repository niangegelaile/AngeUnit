package com.ange.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ange.R;


/**
 * Created by Administrator on 2017/1/3 0003.
 */

public class TittleContentView extends LinearLayout {
    private TextView tvTitle;
    private EditText tvContent;
    private String title;
    private String content;
    private String hint;
    private Context mContext;
    private boolean canEdit;
    private String isPassword;
    public TittleContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        parseAttr(attrs);
        initView();
    }

    private void initView() {
        View view= LayoutInflater.from(mContext).inflate(R.layout.view_title_edit,this) ;
        tvTitle= (TextView) view.findViewById(R.id.tv_title);
        tvContent= (EditText) view.findViewById(R.id.tv_content);
        tvTitle.setText(title);
        tvContent.setText(content);
        tvContent.setHint(hint);
        if(!canEdit){
            tvContent.setEnabled(false);
        }
        if(!TextUtils.isEmpty(isPassword)){
            tvContent.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);

        }
    }

    private void parseAttr(AttributeSet attrs) {
        TypedArray typedArray=mContext.obtainStyledAttributes(attrs, R.styleable.TittleContentView);
        title= typedArray.getString(R.styleable.TittleContentView_tcv_title);
        content=typedArray.getString(R.styleable.TittleContentView_tcv_content);
        canEdit=typedArray.getBoolean(R.styleable.TittleContentView_tcv_canEdit,false);
        hint=typedArray.getString(R.styleable.TittleContentView_tcv_hint);
        isPassword=typedArray.getString(R.styleable.TittleContentView_tcv_inputType);
        typedArray.recycle();
    }


    public void setContent(String content){
        this.content=content;
        tvContent.setText(content);
    }

    public String getContent() {
        this.content= tvContent.getText().toString();
        return content;
    }
}
