package me.nereo.multi_image_selector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import me.nereo.multi_image_selector.bean.Image;

/**
 * Multi image selector
 * Created by Nereo on 2015/4/7.
 * Updated by nereo on 2016/1/19.
 * Updated by nereo on 2016/5/18.
 */
public class MultiImageSelectorActivity extends AppCompatActivity
        implements MultiImageSelectorFragment.Callback{

    public static final String ACTION_FINISH="ACTION_FINISH";
    //默认图片数
    private static final int DEFAULT_IMAGE_SIZE = 9;

    private ArrayList<String> resultList = new ArrayList<>();

    private ArrayList<Image> mCurrentFoldImages;

    private int mDefaultCount = DEFAULT_IMAGE_SIZE;

    private RelativeLayout mRelativeLayoutBack;

    private TextView mTextViewYuLan;

    private Button mSubmitButton;

    private MultiImageSelectorFragment multiImageSelectorFragment;

    public static final int REQUEST_IMAGE=0x99;

    private FinishReceiver mFinishReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.MIS_NO_ACTIONBAR);//设置主题
        setContentView(R.layout.mis_activity_default);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#3399ff"));//设置状态栏颜色
        }
        mRelativeLayoutBack= (RelativeLayout) findViewById(R.id.rl_back);
        mTextViewYuLan= (TextView) findViewById(R.id.tv_yuLan);
        bindEvent();
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final Intent intent = getIntent();
        mDefaultCount = intent.getIntExtra(Extra.EXTRA_SELECT_COUNT, DEFAULT_IMAGE_SIZE);
        final int mode = intent.getIntExtra(Extra.EXTRA_SELECT_MODE, SelectMode.MODE_MULTI);
        final boolean isShow = intent.getBooleanExtra(Extra.EXTRA_SHOW_CAMERA, true);
        if(mode == SelectMode.MODE_MULTI && intent.hasExtra(Extra.EXTRA_DEFAULT_SELECTED_LIST)) {
            resultList = intent.getStringArrayListExtra(Extra.EXTRA_DEFAULT_SELECTED_LIST);
        }

        mSubmitButton = (Button) findViewById(R.id.commit);
        if(mode ==SelectMode. MODE_MULTI){
            updateDoneText(resultList);
            mSubmitButton.setVisibility(View.VISIBLE);
            mSubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(resultList != null && resultList.size() >0){
                        // Notify success
                        Intent data = new Intent();
                        data.putStringArrayListExtra(Extra.EXTRA_RESULT, resultList);
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

        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putInt(Extra.EXTRA_SELECT_COUNT, mDefaultCount);
            bundle.putInt(Extra.EXTRA_SELECT_MODE, mode);
            bundle.putBoolean(Extra.EXTRA_SHOW_CAMERA, isShow);
            bundle.putStringArrayList(Extra.EXTRA_DEFAULT_SELECTED_LIST, resultList);
            multiImageSelectorFragment= (MultiImageSelectorFragment) Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.image_grid,multiImageSelectorFragment)
                    .commit();
        }
        mFinishReceiver=new FinishReceiver();
        registerReceiver(mFinishReceiver,new IntentFilter("ACTION_FINISH"));
    }

    private void bindEvent() {
        mRelativeLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTextViewYuLan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resultList==null||resultList.size()==0){
                    return;
                }
                ArrayList<Image> images=new ArrayList<>();
                for(String s:resultList){
                    images.add(new Image(s," ",0));
                }
                MultiImageSelector.create()
                        .count(mDefaultCount)   // 最大图片选择数量
                        .multi()  // 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
                        .origin(resultList) // 默认选择图片,回填选项(支持String ArrayList)
                        .showCamera(false) // 是否显示调用相机拍照
                        .imageDatas(images)
                        .start(MultiImageSelectorActivity.this,LookAPhotoActivity.class,REQUEST_IMAGE);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onSingleImageSelected(String path) {
        Intent data = new Intent();
        resultList.add(path);
        data.putStringArrayListExtra(Extra.EXTRA_RESULT, resultList);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onImageSelected(String path) {
        if(!resultList.contains(path)) {
            resultList.add(path);
        }
        updateDoneText(resultList);
    }

    @Override
    public void onImageUnselected(String path) {
        if(resultList.contains(path)){
            resultList.remove(path);
        }
        updateDoneText(resultList);
    }

    @Override
    public void onCameraShot(File imageFile) {
        if(imageFile != null) {
            // notify system the image has change
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));
            Intent data = new Intent();
            resultList.add(imageFile.getAbsolutePath());
            data.putStringArrayListExtra(Extra.EXTRA_RESULT, resultList);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    public void onImageFold(ArrayList<Image> images) {
        mCurrentFoldImages=images;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case  REQUEST_IMAGE:
                if(resultCode==RESULT_OK){
                    resultList=data.getStringArrayListExtra(Extra.EXTRA_RESULT);
                    multiImageSelectorFragment.setDefaultSelect(resultList);
                    updateDoneText(resultList);
                }
                break;
        }
    }
    class FinishReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if(ACTION_FINISH.equals(intent.getAction())){
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mFinishReceiver);
    }
}
