package com.robog.library.painter;

import android.graphics.Canvas;

import com.robog.library.Action;
import com.robog.library.PixelPoint;
import com.robog.library.PixelPath;
import com.robog.library.Utils;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public class SegmentPainter extends AbstractPainter {

    private static final String TAG = "SegmentPainter";

    private PixelPath mPixelPath;

    private int mDuration;

    private boolean mClose;

    public SegmentPainter() {
        this(null, 1000, true);
    }

    public SegmentPainter(PixelPath pixelPath) {
        this(pixelPath, 1000, true);
    }

    public SegmentPainter(Painter painter) {
        this(painter.getPixelPath(), painter.duration(), painter.close());
    }

    public SegmentPainter(PixelPath pixelPath, int duration, boolean close) {
        mPixelPath = pixelPath;
        mDuration = duration;
        mClose = close;
    }

    @Override
    public PixelPath getPixelPath() {
        return mPixelPath;
    }

    @Override
    public int duration() {
        return mDuration;
    }

    @Override
    public boolean close() {
        return mClose;
    }

    @Override
    public void completeDraw(Canvas canvas) {
        draw(canvas, true);
    }

    @Override
    protected void realDraw(Canvas canvas) {
        draw(canvas, false);
    }

    private void draw(Canvas canvas, boolean complete) {

        for (int i = 0; i < pointList.size(); i++) {
            PixelPoint current = pointList.get(i);
            PixelPoint next;
            if (i < pointList.size() - 1) {
                next = pointList.get(i + 1);
            } else {
                if (!close()) {
                    return;
                }
                next = pointList.get(0);
            }
            // 如果是完整画完当前笔，或者当前点已完整绘制，则当前点起始点与下一点起始点相连
            if (complete || current.isPathFinish()) {

                canvas.drawLine(current.getStartX(), current.getStartY(),
                        next.getStartX(), next.getStartY(), paint);
            } else {
                // 否则，当前点起始点与当前点所到点相连
                canvas.drawLine(current.getStartX(), current.getStartY(),
                        current.getEndX(), current.getEndY(), paint);
            }

        }
    }

    @Override
    public boolean performDraw(Action action) {

        // 总路程
        float distance = Utils.calDistance(pointList, close());

        for (int i = 0; i < pointList.size(); i++) {

            PixelPoint current = pointList.get(i);
            PixelPoint next;

            if (i < pointList.size() - 1) {
                next = pointList.get(i + 1);
            } else {
                next = pointList.get(0);
            }
            // 当前线段占总路径百分比
            float fraction = Utils.calFraction(distance, current, next);
            float segment = duration() * fraction / INTERVAL;

            float startX = current.getStartX();
            float startY = current.getStartY();

            float endX = next.getStartX();
            float endY = next.getStartY();

            float deltaX = endX - startX;
            float deltaY = endY - startY;

            float dis = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            // 单位时间移动长度
            float div = dis / segment;
            float degree = (float) Math.atan2(deltaY, deltaX);

            while (!current.isPathFinish()) {

                if (!isRunning()) {
                    return false;
                }

                float moveX = (float) (current.getEndX() + div * Math.cos(degree));
                float moveY = (float) (current.getEndY() + div * Math.sin(degree));

                current.setEndX(moveX);
                current.setEndY(moveY);

                float moveDis = Utils.getDis(current.getEndX(), current.getEndY(),
                        current.getStartX(), current.getStartY());

                // 如果当前点离目标点小于2倍单位时间长度，则认为当前线段已完成
                if (Math.abs(dis - moveDis) < 2 * div) {
                    current.setEndX(endX);
                    current.setEndY(endY);
                    current.setPathFinish(true);
                }
                // 更新界面
                action.update(this);
                try {
                    Thread.sleep(INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            // 保证图像都绘制
            action.update(this);

        }
        return true;
    }
}
