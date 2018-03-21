package org.wong.createview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wong on 2018/3/21.
 * 自定义开关控件，初使用前请设置开关的背景以及滑块图片资源。
 */

public class MView extends View {

    private Bitmap slideButtonBitmap;
    private Bitmap switchBackgroundBitmap;
    private Paint paint;
    private boolean switchState = false;
    //当前触摸的位置
    private float currentX;
    //触摸状态flag
    private boolean isTouchModel = false;
    //开关界限
    private float openOffLimit;
    //监听事件
    OnSwitchChangeListener onSwitchChangeListener = null;

    public MView(Context context) {
        super(context);
        init();
    }

    public MView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 控件初始化
     */
    private void init() {
        paint = new Paint();
    }

    /**
     * 设置开关状态
     *
     * @param switchState true开；false关
     */
    public void setSwitchState(boolean switchState) {
        this.switchState = switchState;
    }

    /**
     * 设置开关滑块资源，使用前请设置
     *
     * @param slideButtonResource 开关滑块图片资源
     */
    public void setSlideButtonResource(int slideButtonResource) {
        slideButtonBitmap = BitmapFactory.decodeResource(getResources(), slideButtonResource);
    }

    /**
     * 设置开关背景图资源，使用前请设置
     *
     * @param switchBackgroundResource 开关背景图片资源
     */
    public void setSwitchBackgroundResource(int switchBackgroundResource) {
        switchBackgroundBitmap = BitmapFactory.decodeResource(getResources(), switchBackgroundResource);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(switchBackgroundBitmap.getWidth(), switchBackgroundBitmap.getHeight());
        //开关界限
        openOffLimit = switchBackgroundBitmap.getWidth() - slideButtonBitmap.getWidth();
    }

    //绘制界面
    @Override
    protected void onDraw(Canvas canvas) {
        //绘制开关背景
        canvas.drawBitmap(switchBackgroundBitmap, 0, 0, paint);

        //根据触摸情况开始绘制开关滑块
        if (isTouchModel) {
            //让滑块中心位置对准手指触摸的地方
            float newPosition = currentX - (slideButtonBitmap.getWidth() / 2.0f);

            //绘制滑块
            if (newPosition < 0) {
                newPosition = 0;
            } else if (newPosition > openOffLimit) {
                newPosition = openOffLimit;
            }
            canvas.drawBitmap(slideButtonBitmap, newPosition, 0, paint);
        } else {
            //非触摸状态
            if (switchState) {
                canvas.drawBitmap(slideButtonBitmap, openOffLimit, 0, paint);
            } else {
                canvas.drawBitmap(slideButtonBitmap, 0, 0, paint);
            }
        }

    }

    //触摸事件监听
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouchModel = true;
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isTouchModel = false;
                boolean newState = event.getX() > openOffLimit;
                if (newState != switchState && onSwitchChangeListener != null) {
                    onSwitchChangeListener.onStateChange(newState);
                }
                switchState = newState;
                break;
        }
        invalidate();
        return true;
    }

    public void setOnSwitchChangeListener(OnSwitchChangeListener onSwitchChangeListener) {
        this.onSwitchChangeListener = onSwitchChangeListener;
    }

    public interface OnSwitchChangeListener {
        //状态回调
        void onStateChange(boolean state);
    }
}