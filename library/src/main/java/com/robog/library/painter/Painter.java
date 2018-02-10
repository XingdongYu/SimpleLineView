package com.robog.library.painter;

import android.graphics.Canvas;

import com.robog.library.Action;
import com.robog.library.Chain;
import com.robog.library.PixelShape;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public interface Painter {

    PixelShape getShape();

    /**
     * 完成当前笔画所需时间
     */
    int duration();

    /**
     * 路径是否闭合
     */
    boolean close();

    /**
     * 插入一笔
     */
    void start(Chain chain, Action action);

    /**
     * 继续
     */
    void stick();

    /**
     * 停止
     */
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
