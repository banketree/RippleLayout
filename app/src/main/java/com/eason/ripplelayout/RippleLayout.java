package com.eason.ripplelayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Eason on 2/26/16.
 */
public class RippleLayout extends LinearLayout {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 被点击的控件的宽高
    private int mTargetWidth;
    private int mTargetHeight;
    // 在被选中的控件长宽中的最大值和最小值
    private int mMinBetweenWidthAndHeight;
    private int mMaxBetweenWidthAndHeight;

    // mMaxRadius为绘制的水波纹圆圈最大的半径
    private int mMaxRevealRadius;
    // mRevealRadiusGap为每次重新绘制半径增加的值
    private int mRevealRadiusGap;
    // mRevealRadius为初始的数值
    private int mRevealRadius = 0;
    // 用户点击处的坐标
    private float mCenterX;
    private float mCenterY;
    // 获取自定义控件MyRevealLayout 在屏幕上的位置
    private int[] mLocationInScreen = new int[2];
    // 是否执行动画
    private boolean mShouldDoAnimation = false;
    // 是否被按下
    private boolean mIsPressed = false;
    // 重新绘制的时间 单位毫秒
    private int INVALIDATE_DURATION = 50;
    // mTouchTarget指的是用户点击的那个view
    private View mTouchTarget;

    private boolean isClickedParent = false;

    public RippleLayout(Context context) {
        super(context);
        init();
    }

    public RippleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public RippleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mPaint.setColor(Color.BLACK);
//        RippleLayout.this.setClickable(true);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.getLocationOnScreen(mLocationInScreen);
    }

    /**
     * 绘制水波
     */
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!mShouldDoAnimation) {
            return;
        }

        if (mRevealRadius > mMinBetweenWidthAndHeight / 2) {
            mRevealRadius += mRevealRadiusGap * 4;
        } else {
            mRevealRadius += mRevealRadiusGap;
        }


        this.getLocationOnScreen(mLocationInScreen);
        int[] location = new int[2];
        mTouchTarget.getLocationOnScreen(location);
        // 获得要绘制View的left, top, right, bottom值
        int left = location[0] - mLocationInScreen[0];
        int top = location[1] - mLocationInScreen[1];
        int right = left + mTouchTarget.getMeasuredWidth();
        int bottom = top + mTouchTarget.getMeasuredHeight();

        canvas.save();
        if (!isClickedParent) {
            canvas.clipRect(left, top, right, bottom);
        }
        canvas.drawCircle(mCenterX, mCenterY, mRevealRadius, mPaint);
        canvas.restore();

        if (mRevealRadius <= mMaxRevealRadius) {
            if (isClickedParent) {
                postInvalidateDelayed(INVALIDATE_DURATION);
            } else {
                postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
            }

        } else if (!mIsPressed) {
            mShouldDoAnimation = false;
            if (isClickedParent) {
                postInvalidateDelayed(INVALIDATE_DURATION);
            } else {
                postInvalidateDelayed(INVALIDATE_DURATION, left, top, right, bottom);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 获得相对于屏幕的坐标
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        // 获得动作
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            View touchTarget = getTouchTarget(this, x, y);
            if (touchTarget != null && touchTarget.isClickable() && touchTarget.isEnabled()) {
                mTouchTarget = touchTarget;
                isClickedParent = false;
                initParametersForChild(event, touchTarget);
                // 刷新界面，延迟执行时间
                postInvalidateDelayed(INVALIDATE_DURATION);
            } else {
                mTouchTarget = this;
                isClickedParent = true;
                RippleLayout.this.setClickable(true);
                initParametersForParent(event);
                postInvalidateDelayed(INVALIDATE_DURATION);
            }

        } else if (action == MotionEvent.ACTION_UP) {
            mIsPressed = false;
            RippleLayout.this.setClickable(false);
            postInvalidateDelayed(INVALIDATE_DURATION);
            return true;
        } else if (action == MotionEvent.ACTION_CANCEL) {
            mIsPressed = false;
            postInvalidateDelayed(INVALIDATE_DURATION);
        }

        return super.dispatchTouchEvent(event);
    }


    private void initParametersForChild(MotionEvent event, View view) {
        mCenterX = event.getX();
        mCenterY = event.getY();
        mTargetWidth = view.getMeasuredWidth();
        mTargetHeight = view.getMeasuredHeight();
        mMaxRevealRadius = (int) Math.sqrt((double) (mTargetWidth * mTargetWidth + mTargetHeight * mTargetHeight));
        mRevealRadius = 0;
        mShouldDoAnimation = true;
        mIsPressed = true;
        mRevealRadiusGap = mMaxRevealRadius / 20;
    }

    private void initParametersForParent(MotionEvent event) {
        mCenterX = event.getX();
        mCenterY = event.getY();
        mTargetWidth = this.getMeasuredWidth();
        mTargetHeight = this.getMeasuredHeight();
        mMaxRevealRadius = (int) Math.sqrt((double) (mTargetWidth * mTargetWidth + mTargetHeight * mTargetHeight));
        mRevealRadius = 0;
        mShouldDoAnimation = true;
        mIsPressed = true;
        mRevealRadiusGap = mMaxRevealRadius / 20;
    }

    /**
     * 遍历view树找到用户所点击的那个view
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    private View getTouchTarget(View view, int x, int y) {
        View target = null;
        ArrayList<View> TouchableViews = view.getTouchables();
        for (View child : TouchableViews) {
            if (isTouchPointInView(child, x, y)) {
                target = child;
                break;
            }
        }

        return target;
    }

    /**
     * 判断事件的xy是否落在view的上下左右四个角之内
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    private boolean isTouchPointInView(View view, int x, int y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (view.isClickable() && y >= top && y <= bottom && x >= left && x <= right) {
            return true;
        }
        return false;
    }

}
