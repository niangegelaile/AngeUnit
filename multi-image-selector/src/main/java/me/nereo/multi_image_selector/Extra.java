package me.nereo.multi_image_selector;

/**
 * intent 传参 Key
 * Created by liquanan on 2017/1/18.
 * email :1369650335@qq.com
 */
public interface Extra {

    // 共能选择多少张照片数
    String EXTRA_SELECT_COUNT = "max_select_count";
    //选择模式
     String EXTRA_SELECT_MODE = "select_count_mode";
    //是否显示拍照选项
     String EXTRA_SHOW_CAMERA = "show_camera";
    //返回路径list
     String EXTRA_RESULT = "select_result";
    //带进来的路径list
     String EXTRA_DEFAULT_SELECTED_LIST = "default_list";
    //选中图片的下标
     String INDEX="INDEX";
    //需要展示的图片list,即viewpager的data
    String EXTRA_DATAS="paths";

}
