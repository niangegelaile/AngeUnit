package me.nereo.multi_image_selector;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import me.nereo.multi_image_selector.adapter.PicFragmentAdapter;
import me.nereo.multi_image_selector.bean.Image;

/***
 * 功能描述:预览照片
 * 作者:liquanan
 * 时间:2016/8/27
 * 版本:
 * ==============================================
 ***/

public class LookAPhotoActivity extends AppCompatActivity {
    // Single choice
    public static final int MODE_SINGLE = 0;
    // Multi choice
    public static final int MODE_MULTI = 1;
    // 默认图片选择数
    private static final int DEFAULT_IMAGE_SIZE = 9;
    //当前设置图片选择数
    private int mDefaultCount = DEFAULT_IMAGE_SIZE;
    //当前选中的图片集合
    private ArrayList<String> resultList = new ArrayList<>();
    private ViewPager mViewPager;
    private PicFragmentAdapter mAdapter;
    //相册集合
    private ArrayList<Image> pathList;
    //当前fragment的下标
    private int pos;
    private Button mSubmitButton;
    private CheckBox mCheckBox;
    private LinearLayout mLinearLayoutBack;
    private TextView mTextViewNum_num;

    int mode;//当前单选or多选模式
    boolean isShow;//是否显示拍照按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MIS_NO_ACTIONBAR);
        setContentView(R.layout.mis_activity_yulanandchoose);
        initView();
        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.mis_statusbar_color));
        }
        final Intent intent = getIntent();
        //获取参数
        parseIntent(intent);

        if(mode == MODE_MULTI){
            updateDoneText(resultList);
            setPosTv();
            mSubmitButton.setVisibility(View.VISIBLE);
        }else{
            mSubmitButton.setVisibility(View.GONE);
        }
        mAdapter=new PicFragmentAdapter(getSupportFragmentManager(),pathList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(pos);
        bindEvents();
        if( isSelected(pathList.get(pos).path)){
            mCheckBox.setChecked(true);
        }else {
            mCheckBox.setChecked(false);
        }
    }



    /**
     *解析出intent参数
     * @param intent
     */
    private void parseIntent(Intent intent) {
        mDefaultCount = intent.getIntExtra(Extra.EXTRA_SELECT_COUNT, DEFAULT_IMAGE_SIZE);
         mode = intent.getIntExtra(Extra.EXTRA_SELECT_MODE, MODE_MULTI);
         isShow = intent.getBooleanExtra(Extra.EXTRA_SHOW_CAMERA, true);
        if(mode == MODE_MULTI && intent.hasExtra(Extra.EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(Extra.EXTRA_DEFAULT_SELECTED_LIST);
        }
        pathList= getIntent().getParcelableArrayListExtra(Extra.EXTRA_DATAS);
        pos=getIntent().getIntExtra(Extra.INDEX,0);
    }

    private void initView() {
        mSubmitButton= (Button) findViewById(R.id.commit);
        mViewPager= (ViewPager) findViewById(R.id.vp_pic);
        mCheckBox= (CheckBox) findViewById(R.id.cb_select);
        mLinearLayoutBack= (LinearLayout) findViewById(R.id.ll_back);
        mTextViewNum_num= (TextView) findViewById(R.id.tv_numbynum);
    }


    public void bindEvents() {
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCheckBox.isChecked()){
                    if(resultList.size()>=mDefaultCount){
                        mCheckBox.setChecked(false);
                        Toast.makeText(LookAPhotoActivity.this,"你最多只能添加"+mDefaultCount+"张照片",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    onImageSelected(pathList.get(pos).path);
                }else {
                    onImageUnselected(pathList.get(pos).path);
                }
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("ACTION_PicReceiver");
                intent.putStringArrayListExtra(Extra.EXTRA_RESULT,resultList);
                sendBroadcast(intent);
                Intent intentfinish=new Intent(MultiImageSelectorActivity.ACTION_FINISH);
                sendBroadcast(intentfinish);
                finish();
            }
        });
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pos=position;
                if( isSelected(pathList.get(pos).path)){
                    mCheckBox.setChecked(true);
                }else {
                    mCheckBox.setChecked(false);
                }
                setPosTv();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        /**
         * 返回时，把选中的图片路径传递给上一个页面
         */
        mLinearLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnChoiceDatas();
            }
        });

    }
    /**
     * 完成按钮的数据变化
     * @param resultList selected image data
     */
    private void updateDoneText(ArrayList<String> resultList){
        int size = 0;
        if(resultList == null || resultList.size()<=0){
            mSubmitButton.setText(R.string.mis_action_done);
            mSubmitButton.setEnabled(false);
        }else{
            size = resultList.size();
            mSubmitButton.setEnabled(true);
        }
        mSubmitButton.setText(getString(R.string.mis_action_button_string,
                getString(R.string.mis_action_done), size, mDefaultCount));
    }

    /**
     * 选中的图add进结果里
     * @param path
     */
    public void onImageSelected(String path) {
        if(!isSelected(path)) {
            resultList.add(path);
        }
        updateDoneText(resultList);
    }

    /**
     * 取消选中，从结果中移除
     * @param path
     */
    public void onImageUnselected(String path) {
        if(isSelected(path)){
            resultList.remove(path);
        }
        updateDoneText(resultList);
    }

    /**
     * 是否选中
     * @param path
     * @return
     */
    private boolean isSelected(String path){
        return resultList.contains(path);
    }
    private void setPosTv(){
        mTextViewNum_num.setText((pos+1)+"/"+pathList.size());
    }

    /**
     * 返回时，把选中的图片路径传递给上一个页面
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
           returnChoiceDatas();
        }
        return true;
    }

    /**
     * 返回时，把选中的图片路径传递给上一个页面
     */
    private void returnChoiceDatas(){
        Intent intent=new Intent();
        intent.putExtra(Extra.EXTRA_RESULT,resultList);
        setResult(RESULT_OK,intent);
        finish();
    }
}
