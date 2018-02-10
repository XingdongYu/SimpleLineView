package com.robog.library;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public class PixelPoint {

    private volatile float mStartX;

    private volatile float mStartY;

    private volatile float mEndX;

    private volatile float mEndY;

    private volatile float mAngle;

    private final float mInitialX;

    private final float mInitialY;

    private volatile boolean mPathFinish;

    public PixelPoint() {
        mInitialX = 0;
        mInitialY = 0;
    }

    public PixelPoint(float x, float y) {
        set(x, y);

        mInitialX = x;
        mInitialY = y;
    }

    private void set(float x, float y) {
        mStartX = x;
        mStartY = y;
        mEndX = x;
        mEndY = y;
    }

    public float getStartX() {
        return mStartX;
    }

    public void setStartX(float mStartX) {
        this.mStartX = mStartX;
    }

    public float getStartY() {
        return mStartY;
    }

    public void setStartY(float mStartY) {
        this.mStartY = mStartY;
    }

    public float getEndX() {
        return mEndX;
    }

    public void setEndX(float mEndX) {
        this.mEndX = mEndX;
    }

    public float getEndY() {
        return mEndY;
    }

    public void setEndY(float mEndY) {
        this.mEndY = mEndY;
    }

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float mAngle) {
        this.mAngle = mAngle;
    }

    public boolean isPathFinish() {
        return mPathFinish;
    }

    public void setPathFinish(boolean mPathFinish) {
        this.mPathFinish = mPathFinish;
    }

    public void reset() {
        set(mInitialX, mInitialY);
        mAngle = 0;
        mPathFinish = false;
    }

    @Override
    public String toString() {
        return "Start:(" + mStartX + ", " + mStartY + ") "
                + "End:(" + mEndX + "," + mEndY + ") ";
    }
}
