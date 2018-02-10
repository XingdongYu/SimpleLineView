package com.robog.library.painter;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.robog.library.Action;
import com.robog.library.PixelPoint;
import com.robog.library.PixelShape;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public class CirclePainter extends AbsPainter {

    private static final String TAG = "CirclePainter";

    final PixelShape mPixelShape;

    final int mDuration;

    final float mStartAngle;

    final float mSweepAngle;

    final boolean mUseCenter;

    float mPadding = 10;

    final RectF mRectF = new RectF();

    public CirclePainter() {
        this(null, 0, 0, 360, false);
    }

    public CirclePainter(Painter painter) {
        this(painter, 0, 360, false);
    }

    public CirclePainter(Painter painter, float startAngle, float sweepAngle, boolean useCenter) {
        this(painter.getShape(), painter.duration(), startAngle, sweepAngle, useCenter);
    }

    public CirclePainter(PixelShape pixelShape, int duration,
                         float startAngle, float sweepAngle, boolean useCenter) {
        mPixelShape = pixelShape;
        mDuration = duration;
        mStartAngle = startAngle;
        mSweepAngle = sweepAngle;
        mUseCenter = useCenter;
    }

    public void setPadding(float padding) {
        mPadding = padding;
    }


    @Override
    public PixelShape getShape() {
        return mPixelShape;
    }

    @Override
    public int duration() {
        return mDuration;
    }

    @Override
    public boolean close() {
        return false;
    }

    @Override
    public void completeDraw(Canvas canvas) {
        canvas.drawArc(mRectF, mStartAngle, mSweepAngle, mUseCenter, paint);
    }

    @Override
    protected void realDraw(Canvas canvas) {
        canvas.drawArc(mRectF, mStartAngle, pointList.get(0).getAngle(), mUseCenter, paint);
    }

    @Override
    public void performDraw(Action action) {

        setRectF();

        PixelPoint point = pointList.get(0);
        float frac = duration() / INTERVAL;
        float div = 360 / frac;
        float angle = point.getAngle();


        if (mSweepAngle >= 0) {
            if (angle > mSweepAngle) {
                angle = mSweepAngle;
            }
            while (angle <= mSweepAngle && !isStop) {
                angle += div;
                update(action, point, angle);
            }

        } else {
            if (angle < mSweepAngle) {
                angle = mSweepAngle;
            }
            while (angle >= mSweepAngle && !isStop) {
                angle -= div;
                update(action, point, angle);
            }
        }

    }

    void setRectF() {
        // 以第一点为矩形左上角、最后一点为右下角
        mRectF.set(pointList.get(0).getStartX() + mPadding,
                pointList.get(0).getStartY() + mPadding,
                pointList.get(pointList.size() - 1).getStartX() - mPadding,
                pointList.get(pointList.size() - 1).getStartY() - mPadding);
    }

    private void update(Action action, PixelPoint point, float angle) {
        point.setAngle(angle);
        action.update(this);
        try {
            Thread.sleep(INTERVAL);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
