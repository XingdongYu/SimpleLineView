package com.robog.library.painter;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/11
 */

public interface CirclePainter extends Painter {

    float startAngle();

    float sweepAngle();

    float angle();

    boolean useCenter();

    void setPadding(float padding);

    float getPadding();
}
