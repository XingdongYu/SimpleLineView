package com.robog.simplelineview;

import android.graphics.Canvas;
import android.graphics.RectF;
import com.robog.library.PixelShape;
import com.robog.library.painter.RealCirclePainter;

/**
 * 自定义可同时画圆的Painter
 *
 * @Author: yuxingdong
 * @Time: 2018/2/13
 */

public class DoubleCirclePainter extends RealCirclePainter {

    private RectF mSmallRectF = new RectF();

    public DoubleCirclePainter(PixelShape pixelShape, int duration,
                             float startAngle, float sweepAngle, boolean useCenter) {
        super(pixelShape, duration, startAngle, sweepAngle, useCenter);
    }

    @Override
    public void completeDraw(Canvas canvas) {
        super.completeDraw(canvas);
        canvas.drawArc(mSmallRectF, startAngle(), sweepAngle(), useCenter(), paint);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mSmallRectF, startAngle(), -angle(), useCenter(), paint);
    }

    @Override
    public void setRectF() {
        super.setRectF();
        // 小圆以第二点为矩形左上角、倒数第二点为右下角
        mSmallRectF.set(pointList.get(1).getStartX(),
                pointList.get(1).getStartY(),
                pointList.get(pointList.size() - 2).getStartX(),
                pointList.get(pointList.size() - 2).getStartY());
    }

}
