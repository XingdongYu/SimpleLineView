package com.robog.library.painter;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.robog.library.Action;
import com.robog.library.Chain;
import com.robog.library.PixelPath;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public interface Painter {

    PixelPath getPixelPath();

    int duration();

    /**
     * 路径是否闭合
     */
    boolean close();

    void setPaint(Paint paint);

    Paint getPaint();

    boolean isRunning();

    void start(Chain chain, Action action);

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
