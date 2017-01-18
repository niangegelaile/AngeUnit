package me.nereo.multi_image_selector.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import me.nereo.multi_image_selector.PicFragment;
import me.nereo.multi_image_selector.bean.Image;

/***
 * 功能描述: 图片预览fragment adapter
 * 作者:liquanan
 * 时间:2016/8/27
 * 版本:
 * ==============================================
 ***/
public class PicFragmentAdapter extends FragmentStatePagerAdapter {

    private List<Image> list;

    public PicFragmentAdapter(FragmentManager fm, List<Image> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return PicFragment.newInstance(position,list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
