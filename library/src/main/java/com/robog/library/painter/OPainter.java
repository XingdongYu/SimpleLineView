package com.robog.library.painter;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/11
 */

public interface OPainter extends Painter {

    float getStartAngle();

    float getSweepAngle();

    boolean useCenter();

    void setPadding(float padding);
}
