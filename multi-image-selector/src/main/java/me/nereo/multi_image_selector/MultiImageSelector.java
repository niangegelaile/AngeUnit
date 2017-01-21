package me.nereo.multi_image_selector;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.bean.Image;

/**
 * 图片选择器
 * Created by nereo on 16/3/17.
 */
public class MultiImageSelector {

    public static final String EXTRA_RESULT = Extra.EXTRA_RESULT;
    //访问是否有权限的请求码
    public  static final int READ_EXTERNAL_STORAGE_REQUEST_CODE =0x11 ;
    //是否需要显示拍照按钮
    private boolean mShowCamera = true;
    //最多可以选择多少张
    private int mMaxCount = 9;
    //选择模式
    private int mMode = SelectMode.MODE_MULTI;
    //传进来的，即已经选了的
    private List<String> mOriginData;
    private static MultiImageSelector sSelector;
    //提供选择的所有图片
    private List<Image> mImageDatas;
    //单张图显示时选中的第几张
    private int index;
    private MultiImageSelector(){}

    public static MultiImageSelector create(){
        if(sSelector == null){
            sSelector = new MultiImageSelector();
        }
        return sSelector;
    }


    public MultiImageSelector imageDatas(List<Image> mImageDatas){
        this.mImageDatas=mImageDatas;
        return sSelector;
    }

    public MultiImageSelector index(int index){
        this.index=index;

        return sSelector;
    }


    public MultiImageSelector showCamera(boolean show){
        mShowCamera = show;
        return sSelector;
    }

    public MultiImageSelector count(int count){
        mMaxCount = count;
        return sSelector;
    }

    public MultiImageSelector single(){
        mMode = SelectMode.MODE_SINGLE;
        return sSelector;
    }

    public MultiImageSelector multi(){
        mMode = SelectMode.MODE_MULTI;
        return sSelector;
    }

    public MultiImageSelector origin(List<String> images){
        mOriginData = images;
        return sSelector;
    }

    /**
     * 启动activity
     * @param activity
     * @param requestCode
     */
    public void start(Activity activity,Class target  ,int requestCode){
        final Context context = activity;
        if(hasPermission(context)) {
            if(target.getName().equals(MultiImageSelectorActivity.class.getName())){
                activity.startActivityForResult(createIntent(context), requestCode);
            }else if(target.getName().equals(LookAPhotoActivity.class.getName())){
                activity.startActivityForResult(createIntentForSingle(context), requestCode);
            }

        }else{
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_REQUEST_CODE);
            Toast.makeText(context, R.string.mis_error_no_permission, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断是否有权限
     * @param context
     * @return
     */
    private boolean hasPermission(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            // Permission was added in API Level 16
            return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    /**
     * 把参数装进intent(gridview那个界面)
     * @param context
     * @return
     */
    private Intent createIntent(Context context){
        Intent intent = new Intent(context, MultiImageSelectorActivity.class);
        intent.putExtra(Extra.EXTRA_SHOW_CAMERA, mShowCamera);
        intent.putExtra(Extra.EXTRA_SELECT_COUNT, mMaxCount);
        if(mOriginData != null){
            intent.putStringArrayListExtra(Extra.EXTRA_DEFAULT_SELECTED_LIST, (ArrayList<String>) mOriginData);
        }
        intent.putExtra(Extra.EXTRA_SELECT_MODE, mMode);
        return intent;
    }

    /**
     * 把参数装进intent(一张全屏图那个界面)
     * @param context
     * @return
     */
    private Intent createIntentForSingle(Context context){
        Intent intent = new Intent(context, LookAPhotoActivity.class);
        intent.putExtra(Extra.EXTRA_SHOW_CAMERA, mShowCamera);
        intent.putExtra(Extra.EXTRA_SELECT_COUNT, mMaxCount);
        if(mOriginData != null){
            intent.putStringArrayListExtra(Extra.EXTRA_DEFAULT_SELECTED_LIST, (ArrayList<String>) mOriginData);
        }
        if(mImageDatas!=null){
            intent.putParcelableArrayListExtra(Extra.EXTRA_DATAS,(ArrayList<Image>)mImageDatas);
        }
        intent.putExtra(Extra.INDEX,index);
        intent.putExtra(Extra.EXTRA_SELECT_MODE, mMode);
        return intent;
    }
}
