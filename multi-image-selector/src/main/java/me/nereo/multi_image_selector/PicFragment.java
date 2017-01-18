package me.nereo.multi_image_selector;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import me.nereo.multi_image_selector.bean.Image;

/***
 * 功能描述: 图片预览fragemnet
 * 作者:liquanan
 * 时间:2016/8/27
 * 版本:
 * ==============================================
 ***/

public class PicFragment extends Fragment {

    private static final String TAG = "PicFragment";
    private ImageView mImageView;
    private int index;
    private String path;
    public static PicFragment newInstance(int index,Image image) {
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        bundle.putString("path",image.path);
        PicFragment fragment = new PicFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index= getArguments()==null?-1:getArguments().getInt("index");
        path=getArguments()==null?null:getArguments().getString("path");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.mis_fragment_pic,container,false);
        mImageView= (ImageView) v.findViewById(R.id.iv_pic);

        Glide.with(this).load(path).placeholder(R.drawable.mis_default_error).fitCenter().crossFade().into(mImageView);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



}
