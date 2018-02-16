package com.robog.library;

import com.robog.library.painter.Painter;

import java.util.List;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public interface Action {

    void update(Painter painter);

    void setProgress(int progress);

    int getProgress();

    int getStatus();

    List<PixelPoint> fetchCoordinate(Painter painter);
}
