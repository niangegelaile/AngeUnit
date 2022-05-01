package com.ange.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.io.IOException;
import java.io.InputStream;

/**
 * https://www.jianshu.com/p/2b17fc020261
 * 这个控件被专门用来加载并显示大长图。
 * 它具有以下功能和特性：
 * 1、显示图片
 * 2、支持上下滑动
 * 3、滑动具有惯性
 * 4、触摸后，滑动可以停止
 * 5、性能较好，内存占用少
 *
 * @author Li Zongwei
 * @date 2020/9/17
 **/
public class MyBigView2 extends View implements GestureDetector.OnGestureListener, View.OnTouchListener {

    /**
     * 用于分块加载，需要跟 BitmapRegionDecoder 配合使用
     */
    private final Rect mRect;

    /**
     * 区域解码器，解码那些区域，由Rect决定
     */
    private BitmapRegionDecoder mBitmapRegionDecoder;

    /**
     * 用于对Bitmap的加载方式进行配置，这是Bitmap实现高效（低内存）加载需要用的一个核心类
     */
    private final BitmapFactory.Options mOptions;

    /**
     * 用于手势支持，要配合滑动使用
     */
    private final GestureDetector mGestureDetector;

    /**
     * 实现滑动
     */
    private final Scroller mScroller;

    /**
     * 图片的原始宽度
     */
    private int mOriginalImageWidth;

    /**
     * 图片的原始高度
     */
    private int mOriginalImageHeight;

    /**
     * 当前控件的宽度
     */
    private int mViewWidth;

    /**
     * 当前控件的高度
     */
    private int mViewHeight;

    /**
     * 缩放因子，就是原始图片相对控件大小的缩放
     */
    private float mScale;

    /**
     * 复用Bitmap
     */
    private Bitmap mBitmap;


    /**
     * @param context
     */
    public MyBigView2(Context context) {
        this(context, null);
    }

    public MyBigView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBigView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         * 第一步：初始化需要用的对象
         */
        // 1、内存复用
        mOptions = new BitmapFactory.Options();

        // 2、分块加载
        mRect = new Rect();

        // 3、分块加载，需要配合


        // 4、手势支持，因为要滑动嘛,这里的this是需要传入一个OnGestureListener接口实现，自然是当前的MyBigView2了。
        mGestureDetector = new GestureDetector(context, this);

        // 5、滑动
        mScroller = new Scroller(context);

        // 6、监听滑动事件，此View需要把滑动事件交给 mGestureDetector 去处理  TODO 这里现在还没有思考清除啊。。。标记一下
        setOnTouchListener(this);

        // 好了，到此，准备工作就做完了，下面可以拿图片进来处理了
    }

    /**
     * 第二步：获取图片
     * 这个不用多说了，这个View就是用来显示图片的嘛
     *
     * @param is 传入一个输入流，至于这里为什么用输入流，其实传个Bitmap、url也都是可以的，
     *           但这不是本例的重点哈，重点是拿到图片后的处理
     */
    public void setImage(InputStream is) {
        //哇，这个属性看过Bitmap优化的应该都不陌生，就是解析的时候只是拿到属性，并没有真正的加载到内存
        //这个属性设置完成后，我们就可以解析图片了，主要是为了拿到 图片的：宽、高
        //在真正获取图片前，记得设置为false
        mOptions.inJustDecodeBounds = true;
        // 获取图片，在这之前要对 mOptions 做一下设置
        BitmapFactory.decodeStream(is, null, mOptions);

        mOriginalImageWidth = mOptions.outWidth;
        mOriginalImageHeight = mOptions.outHeight;

        // 设置bitmap的解析格式
        // 这里涉及到一个知识点：
        // RGB_565：表示只有每个像素只有 RGB，没有A（透明通道），565表示 RGB 分别占 5、6、5个bit，也就是共16位，2字节（2byte）
        // ARGB_8888：即 ARGB 分别占 8、8、8、8个bit，也就是共32位，4个字节（4byte）
        // 自然 ARGB_8888 的颜色要比 RGB_565 丰富
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;

        // 设为true，表示内存复用，这个属性还没有做细致的了解
        mOptions.inMutable = true;

        mOptions.inJustDecodeBounds = false;

        // mOptions的设置到此就结束了，下面可以真正的加载图片了

        // 我们前面说了，要进行低内存加载，这需要用到两个对象：BitmapRegionDecoder和Rect，Rect是要作为

        try {
            //初始化区域解码器，解码器有了Bitmap的流，待会儿就可以从解码器中，获取特定区域的图片了，这个特定区域便是由Rect指定的。
            mBitmapRegionDecoder = BitmapRegionDecoder.newInstance(is, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //到这里，图片也有了，解码器也有了，mOptions也设置好了，万事俱备，只欠绘制

        // 当然，绘制前还得测量，然后进行绘制
        requestLayout();
    }

    /**
     * 第三步：测量
     * 用于获取控件宽高、缩放比，然后计算出取图片的区域Rect
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //测量，首先，自然是获取此控件的宽高了
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();

        //缩放比例，这里得注意 把 mOriginalImageWidth 转为float类型，缩放小数位是需要的，不然不准
        mScale = mViewWidth / (float) mOriginalImageWidth;

        // 有了控件的宽高，之前也得到了图片的原始宽高，就可以确定 Rect 的范围了，这个范围将会被解码器用来解析图片
        mRect.top = 0;
        mRect.left = 0;
        // 这里需要思考下，我们说了 mRect 是来规定解码器解析图片范围的，宽度自然要图片宽度 mOriginalImageWidth
        mRect.right = mOriginalImageWidth;
        // mRect.bottom 的话，我们需要根据 控件宽度和图片宽度的大小 缩放比例，进行一下计算
        // 这个计算得说明一下，当时还是蒙了好一会儿
        // 首先，bottom指定了要从解码器中得到的图片底部，我们从0开始取，底部自然应该是控件底部：ViewHeight
        // 那为什么还需要用 mViewHeight / mScale 呢？
        //
        // 举个例子来说明吧：
        // 现在有一张图片宽高为：500*3000   控件宽高为：1000*1000  此时：mScale = 1000 / 500  = 2
        // 我们现在要把这张图片完全显示在控件上，需要
        // 1、把宽度 放大两倍，因此为了保证图片宽高比一致，高度也需要放大 2
        // （图片宽高的缩放是在onDraw里面进行的），所以我们需要先在这里把高度缩小2倍
        // 2、我们现在只要知道，后面高度会放大两倍就好，因此，我们要在  高1000的控件上面显示出这张图片，
        //    实际上只要取图片高度的500就可以了，正好是 控件高度的一半
        // 总而言之：图片待会会进行一次  mScale 的缩放，为保证图片正好可以显示在控件上，高度需要先缩放一次
        //
        // 这么说吧，要在1000*1000的控件上显示500*3000的图片，我们每次只需要每次取 500*500 就好了，onDraw中一放大，图片正好是1000*1000，然后通过上下滑动显示，完美
        mRect.bottom = (int) (mViewHeight / mScale);

        // ok，有了 Rect 的取图区域，我们就可以去onDraw中绘制了
    }

    /**
     * 矩阵
     */
    private Matrix mMatrix;

    /**
     * 第四步：绘制Bitmap
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 这里还是判断下，解码器还在不，如果解码器没了，图片都没了，更别说显示了
        if (mBitmapRegionDecoder == null) {
            return;
        }

        // 高性能，低内存的主旨不能忘了,通过下面这句话来设置内存复用
        mOptions.inBitmap = mBitmap;

        // 然后就是获取要显示的图片了
        mBitmap = mBitmapRegionDecoder.decodeRegion(mRect, mOptions);

        // 在显示前，我们要进行一次缩放
        // 看，在这里设置了 对宽高进行一次缩放，因此啊，前面 mRect.bottom = (int) (mViewHeight / mScale);也就懂了。
        mMatrix = new Matrix();
        mMatrix.setScale(mScale, mScale);

        // 绘制图片
        canvas.drawBitmap(mBitmap, mMatrix, null);

        // 到此，可以运行看一下第一屏了
    }

    /**
     * 第五步：处理touch事件
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //交给 mGestureDetector 进行处理
        return mGestureDetector.onTouchEvent(event);
    }

    /**
     * 第六步：处理点击事件
     *
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        //如果正在滑动，则停止滑动
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
        //接收后续事件
        return true;
    }

    /**
     * 第七步：处理滑动事件
     * 滑动的本质是改变Rect的区域，然后重新绘制，也就达到了滑动的效果
     * 此View是要改变 mRect.top 和 mRect.bottom
     *
     * @param e1        开始事件
     * @param e2        即时事件
     * @param distanceX
     * @param distanceY
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //上下移动时，改变Rect显示区域
        mRect.offset(0, (int) distanceY);

        //判断是否到底
        if (mRect.bottom > mOriginalImageHeight) {
            mRect.bottom = mOriginalImageHeight;
            mRect.top = (int) (mOriginalImageHeight - mViewHeight / mScale);
        }

        //判断是否到顶
        if (mRect.top < 0) {
            mRect.top = 0;
            mRect.bottom = (int) (mViewHeight / mScale);
        }

        // 冲冲冲，开始绘制
        invalidate();

        // 到这里我们的View已经可以完整的显示图片了，但是呢，还没有滑动的惯性，我们接着完善


        return false;
    }

    /**
     * 第八步：处理惯性问题。呃，我怎么发现这个方法就没执行嘛。。。先放着吧，有空再分析下
     *
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(0, mRect.top, 0, (int) -velocityY, 0, 0,
                0, mOriginalImageHeight - (int) (mViewHeight / mScale));
        return false;
    }

    /**
     * 第九步：处理计算结果
     */
    @Override
    public void computeScroll() {
        super.computeScroll();

        if (mScroller.isFinished()){
            return;
        }

        if (mScroller.computeScrollOffset()){
            mRect.top = mScroller.getCurrY();
            mRect.bottom = (int) (mRect.top + mViewHeight/mScale);
            invalidate();
        }

        //到此，这个自定义View也就结束了。bye~
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

}
