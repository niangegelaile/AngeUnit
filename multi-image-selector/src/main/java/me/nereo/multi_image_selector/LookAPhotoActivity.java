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

    /** Max image size，int，{@link #DEFAULT_IMAGE_SIZE} by default */
    public static final String EXTRA_SELECT_COUNT = "max_select_count";
    /** Select mode，{@link #MODE_MULTI} by default */
    public static final String EXTRA_SELECT_MODE = "select_count_mode";
    /** Whether show camera，true by default */
    public static final String EXTRA_SHOW_CAMERA = "show_camera";
    /** Result data set，ArrayList&lt;String&gt;*/
    public static final String EXTRA_RESULT = "select_result";
    /** Original data set */
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "default_list";

    public static final String INDEX="INDEX";

    // Default image size
    private static final int DEFAULT_IMAGE_SIZE = 9;
    private int mDefaultCount = DEFAULT_IMAGE_SIZE;
    private ArrayList<String> resultList = new ArrayList<>();
    private ViewPager mViewPager;
    private PicFragmentAdapter mAdapter;
    private ArrayList<Image> pathList;
    private int pos;
    private Button mSubmitButton;
    private CheckBox mCheckBox;
    private LinearLayout mLinearLayoutBack;
    private TextView mTextViewNum_num;
    public static final int REQUEST_IMAGE=0x99;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MIS_NO_ACTIONBAR);
        setContentView(R.layout.mis_activity_yulanandchoose);
        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#3399ff"));
        }
        final Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(EXTRA_SELECT_COUNT, DEFAULT_IMAGE_SIZE);
        final int mode = intent.getIntExtra(EXTRA_SELECT_MODE, MODE_MULTI);
        final boolean isShow = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        if(mode == MODE_MULTI && intent.hasExtra(EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        }
        pathList= getIntent().getParcelableArrayListExtra("paths");
        pos=getIntent().getIntExtra(INDEX,0);
        if(mode == MODE_MULTI){
            updateDoneText(resultList);
            setPosTv();
            mSubmitButton.setVisibility(View.VISIBLE);
            mSubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(resultList != null && resultList.size() >0){
                        // Notify success
                        Intent data = new Intent();
                        data.putStringArrayListExtra(EXTRA_RESULT, resultList);
                        setResult(RESULT_OK, data);
                    }else{
                        setResult(RESULT_CANCELED);
                    }
                    finish();
                }
            });
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
                intent.putStringArrayListExtra(EXTRA_RESULT,resultList);
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
        mLinearLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra(LookAPhotoActivity.EXTRA_RESULT,resultList);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
    /**
     * Update done button by select image data
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

    public void onImageSelected(String path) {
        if(!resultList.contains(path)) {
            resultList.add(path);
        }
        updateDoneText(resultList);
    }


    public void onImageUnselected(String path) {
        if(resultList.contains(path)){
            resultList.remove(path);
        }
        updateDoneText(resultList);
    }
    private boolean isSelected(String path){
        return resultList.contains(path);
    }
    private void setPosTv(){
        mTextViewNum_num.setText((pos+1)+"/"+pathList.size());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent=new Intent();
            intent.putExtra(LookAPhotoActivity.EXTRA_RESULT,resultList);
            setResult(RESULT_OK,intent);
            finish();
        }
        return true;
    }

}
