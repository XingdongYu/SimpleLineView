package com.robog.library;

import com.robog.library.painter.Painter;

import java.util.List;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public interface Action {

    /**
     * 更新view，实际调用的是{@link SimpleLineView#postInvalidate()}方法
     * @param painter 该painter实现view的onDraw
     */
    void update(Painter painter);

    /**
     * 对外接口，设置progress后更新view
     * @param progress
     */
    void setProgress(int progress);

    /**
     * painter中通过调用该接口进行相应的绘制工作
     * @return
     */
    int getProgress();

    /**
     * 通过当前view执行的状态作出相应处理，可参考{@link com.robog.library.painter.TaskPainter#start(Chain, Action)}方法
     * @return
     */
    int getStatus();

    /**
     * 获得当前painter下所有点的实际坐标
     * @param painter
     * @return
     */
    List<PixelPoint> fetchCoordinate(Painter painter);
}
