package com.robog.library;

import com.robog.library.painter.Painter;

import java.util.List;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/10
 */

public interface Chain {

    void proceed();

    int index();

    void setOnFinishListener(OnFinishListener listener);

    List<Painter> painters();

    interface OnFinishListener {

        void onFinish(int index);
    }
}
