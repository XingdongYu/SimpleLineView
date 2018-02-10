package com.robog.library.painter;

import android.util.Log;

import com.robog.library.Action;
import com.robog.library.PixelPoint;
import com.robog.library.PixelShape;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/10
 */

public class CircleProgressPainter extends CirclePainter {

    private float[] mPercent;

    public CircleProgressPainter() {
        this(new float[]{0, 1.0f}, null, 0, 0, 360, false);
    }

    public CircleProgressPainter(Painter painter) {
        this(painter, new float[]{0, 1.0f}, 0, 360, false);
    }

    public CircleProgressPainter(Painter painter, float[] percent,
                                 float startAngle, float sweepAngle, boolean useCenter) {
        this(percent, painter.getShape(), painter.duration(), startAngle, sweepAngle, useCenter);
    }

    public CircleProgressPainter(float[] percent, PixelShape pixelShape, int duration,
                                 float startAngle, float sweepAngle, boolean useCenter) {

        super(pixelShape, duration, startAngle, sweepAngle, useCenter);

        if (percent == null || percent.length != 2) {
            throw new IllegalArgumentException("the length of percent must be 2!");
        }
        mPercent = percent;
    }

    public Painter setPercent(float[] mPercent) {
        this.mPercent = mPercent;
        return this;
    }

    @Override
    public void performDraw(Action action) {

        setRectF();

        PixelPoint point = pointList.get(0);

        float progress = (float) action.getProgress() / 100;
        float startPercent = mPercent[0];
        float endPercent = mPercent[1];
        float offsetPercent = endPercent - startPercent;

        if (offsetPercent < 0) {
            throw new IllegalArgumentException("percent[1] must be greater!");
        }

        if (progress < startPercent) {
            return;
        }
        if (progress > endPercent) {
            progress = endPercent;
        }

        float realProgress = (progress - startPercent) / offsetPercent;
        float angle = mSweepAngle * realProgress;

        point.setAngle(angle);
        action.update(this);
    }
}
