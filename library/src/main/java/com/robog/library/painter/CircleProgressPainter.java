package com.robog.library.painter;

import com.robog.library.Action;
import com.robog.library.PixelPoint;
import com.robog.library.PixelPath;

/**
 * Author: yuxingdong
 * Time: 2018/2/10
 */

public class CircleProgressPainter extends RealCirclePainter {

    /**
     * mPercent为执行当前绘制占整体绘制的百分比区间
     * 例如: 0.5-0.7表示当动画执行到50%时开始执行当前绘制，至70%结束
     */
    private float[] mPercent;

    public CircleProgressPainter() {
        this(new float[]{0, 1.0f}, null,
                0, 0, 360, false);
    }

    public CircleProgressPainter(Painter painter) {
        this(painter, new float[]{0, 1.0f},
                0, 360, false);
    }

    public CircleProgressPainter(CirclePainter painter) {
        this(painter, new float[]{0, 1.0f},
                painter.startAngle(), painter.sweepAngle(), painter.useCenter());
    }

    public CircleProgressPainter(CirclePainter painter, float[] percent) {
        this(painter, percent, painter.startAngle(),
                painter.sweepAngle(), painter.useCenter());
    }

    public CircleProgressPainter(Painter painter, float[] percent,
                                 float startAngle, float sweepAngle, boolean useCenter) {
        this(percent, painter.getPixelPath(), painter.duration(), startAngle, sweepAngle, useCenter);
    }

    public CircleProgressPainter(float[] percent, PixelPath pixelPath, int duration,
                                 float startAngle, float sweepAngle, boolean useCenter) {

        super(pixelPath, duration, startAngle, sweepAngle, useCenter);

        if (percent == null || percent.length != 2) {
            throw new IllegalArgumentException("The length of percent must be 2!");
        }
        mPercent = percent;
    }

    public Painter setPercent(float[] percent) {
        this.mPercent = percent;
        return this;
    }

    @Override
    public boolean performDraw(Action action) {

        setRectF();

        PixelPoint point = pointList.get(0);

        float progress = (float) action.getProgress() / 100;
        float startPercent = mPercent[0];
        float endPercent = mPercent[1];
        float offsetPercent = endPercent - startPercent;

        if (offsetPercent < 0) {
            throw new IllegalArgumentException("percent[1] must be greater than percent[0]!");
        }

        if (progress < startPercent) {
            return true;
        }
        if (progress > endPercent) {
            progress = endPercent;
        }

        float realProgress = (progress - startPercent) / offsetPercent;
        float angle = sweepAngle() * realProgress;

        point.setAngle(angle);
        action.update(this);
        return true;
    }
}
