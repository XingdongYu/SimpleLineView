package com.robog.library;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public class PixelShape {

    private int mHorizontal;

    private int mVertical;

    private int[] mPath;

    public PixelShape(int horizontal, int vertical, int[] path) {
        mHorizontal = horizontal;
        mVertical = vertical;
        mPath = path;
    }

    public int getHorizontal() {
        return mHorizontal;
    }

    public void setHorizontal(int horizontal) {
        this.mHorizontal = horizontal;
    }

    public int getVertical() {
        return mVertical;
    }

    public void setVertical(int vertical) {
        this.mVertical = vertical;
    }

    public int[] getPath() {
        return mPath;
    }

    public void setPath(int[] path) {
        this.mPath = path;
    }
}
