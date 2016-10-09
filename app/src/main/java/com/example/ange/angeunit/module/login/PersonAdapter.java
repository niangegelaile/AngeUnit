package com.example.ange.angeunit.module.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ange.angeunit.R;
import com.example.ange.angeunit.db.table.Person;

import java.util.List;

/**
 * Created by Administrator on 2016/10/9.
 */
public class PersonAdapter extends BaseAdapter {
    private List<Person> mDatas;
    private Context mContext;
    private int mLayoutId;
    private LayoutInflater mLayoutInflater;
    public PersonAdapter(Context context,int layoutId){
        mContext=context;
        mLayoutId=layoutId;
        mLayoutInflater=LayoutInflater.from(context);
    }

    public void setDatas( List<Person> datas){
        this.mDatas=datas;
        notifyDataSetChanged();
    }

     @Override
    public int getCount() {
        return mDatas==null?0:mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas==null?null:mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mViewHolder=null;
        if(view==null){
           view= mLayoutInflater.inflate(mLayoutId,viewGroup,false);
            mViewHolder=new ViewHolder();
            mViewHolder.mTvPhone= (TextView) view.findViewById(R.id.tv_phone);
            view.setTag(mViewHolder);
        }else {
            mViewHolder= (ViewHolder) view.getTag();
        }
        mViewHolder.mTvPhone.setText(mDatas.get(i).phone());
        return view;
    }
    class ViewHolder{
        TextView mTvPhone;
    }

}
