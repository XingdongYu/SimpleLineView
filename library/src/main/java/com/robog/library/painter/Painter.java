package com.robog.library.painter;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.robog.library.Action;
import com.robog.library.Chain;
import com.robog.library.PixelShape;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public interface Painter {

    PixelShape getShape();

    int duration();

    /**
     * 路径是否闭合
     */
    boolean close();

    void setPaint(Paint paint);

    Paint getPaint();

    void start(Chain chain, Action action);

    void stick();

    void stop();

    /**
     * 绘制
     */
    void onDraw(Canvas canvas);

    /**
     * 进行下一笔绘画时，完整画完当前笔
     */
    void completeDraw(Canvas canvas);

}
