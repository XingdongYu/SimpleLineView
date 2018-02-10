package com.robog.library.painter;


import android.graphics.Canvas;
import android.support.annotation.NonNull;

import com.robog.library.Action;
import com.robog.library.Chain;
import com.robog.library.PixelShape;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public class TaskPainter extends DelayPainter {

    private static final ThreadFactory mThreadFactory = new ThreadFactory() {

        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "SimpleLineView thread pool->" + mCount.getAndIncrement());
        }
    };

    private static final ThreadPoolExecutor sExecutor =
            new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), mThreadFactory);

    public TaskPainter() {
        super(0);
    }

    @Override
    public void start(final Chain chain, Action action) {
        sExecutor.execute(new Runnable() {
            @Override
            public void run() {
                chain.proceed();
            }
        });
    }

}
