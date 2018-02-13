package com.robog.library.painter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.robog.library.Action;
import com.robog.library.Chain;
import com.robog.library.PixelPoint;

import java.util.List;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public abstract class AbsPainter implements Painter {

    private static final String TAG = "AbsPainter";

    /**
     * 动画每一帧时长
     */
    protected static final int INTERVAL = 10;

    protected Chain chain;

    protected List<PixelPoint> pointList;

    /**
     * 绘制是否正在执行
     */
    private boolean mIsRunning = false;

    protected Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public AbsPainter() {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.MITER);
    }

    @Override
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    @Override
    public Paint getPaint() {
        return paint;
    }

    @Override
    public boolean isRunning() {
        return mIsRunning;
    }

    @Override
    public void start(Chain chain, Action action) {

        mIsRunning = true;

        this.chain = chain;

        pointList = action.fetchCoordinate(this);

        boolean isFinish = performDraw(action);

        mIsRunning = false;

        if (isFinish) {
            chain.proceed();
        }
    }

    @Override
    public void stop() {
        mIsRunning = false;
    }

    @Override
    public void onDraw(Canvas canvas) {

        // 1.完成之前Painter的绘制
        drawPreviouse(canvas);
        // 2.绘制当前
        realDraw(canvas);
    }

    protected abstract void realDraw(Canvas canvas);


    public boolean performDraw(Action action) {
        return false;
    }

    /**
     * 开始当前绘制前，先完成之前的绘制
     * @param canvas
     */
    private void drawPreviouse(Canvas canvas) {
        if (chain != null) {

            final int index = chain.index();
            final List<Painter> painters = chain.painters();

            for (int i = 0; i < index - 1; i ++) {
                final Painter painter = painters.get(i);
                painter.completeDraw(canvas);
            }


        }
    }

}
