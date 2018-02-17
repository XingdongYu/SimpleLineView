package com.robog.library.painter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Looper;

import com.robog.library.Action;
import com.robog.library.Chain;
import com.robog.library.PixelPath;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/10
 */

public class DelayPainter implements Painter {

    private final int mTime;

    public DelayPainter(int time) {
        mTime = time;
    }

    @Override
    public PixelPath getPixelPath() {
        return null;
    }

    @Override
    public int duration() {
        return 0;
    }

    @Override
    public boolean close() {
        return false;
    }

    @Override
    public void setPaint(Paint paint) {

    }

    @Override
    public Paint getPaint() {
        return null;
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void start(Chain chain, Action action) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw new RuntimeException("Can't delay in the main thread!");
            }
            Thread.sleep(mTime);
            chain.proceed();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void onDraw(Canvas canvas) {

    }

    @Override
    public void completeDraw(Canvas canvas) {

    }
}
