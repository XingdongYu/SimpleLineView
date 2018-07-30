package com.robog.library;

import android.util.SparseArray;
import com.robog.library.painter.Painter;

import java.util.List;

/**
 * Author: yuxingdong
 * Time: 2018/2/9
 */

public class RealChain implements Chain {

    private final int mIndex;

    private final List<Painter> mPainters;

    private final SparseArray<Chain> mChainPool = new SparseArray<>();

    private final Action mAction;

    private OnFinishListener mOnFinishListener;

    public RealChain(List<Painter> painterList, int index, Action action) {
        mPainters = painterList;
        mIndex = index;
        mAction = action;
    }

    @Override
    public void proceed() {

        if (mOnFinishListener != null && mIndex > 0) {
            mOnFinishListener.onFinish(mIndex - 1);
        }

        if (mIndex == mPainters.size()) {
            return;
        }

        Chain next = mChainPool.get(mIndex);

        if (next == null) {
            next = new RealChain(mPainters, mIndex + 1, mAction);
            next.setOnFinishListener(mOnFinishListener);
            mChainPool.put(mIndex, next);
        }

        final Painter painter = mPainters.get(mIndex);
        painter.start(next, mAction);

    }

    @Override
    public int index() {
        return mIndex;
    }

    @Override
    public void setOnFinishListener(OnFinishListener listener) {
        mOnFinishListener = listener;
    }

    @Override
    public List<Painter> painters() {
        return mPainters;
    }

}
