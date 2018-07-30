package com.robog.library.painter;


import android.support.annotation.NonNull;
import android.util.Log;

import com.robog.library.Action;
import com.robog.library.Chain;
import com.robog.library.PixelPoint;
import com.robog.library.SimpleLineView;
import com.robog.library.Utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.robog.library.SimpleLineView.STATUS_START;

/**
 * Author: yuxingdong
 * Time: 2018/2/9
 */

public class TaskPainter extends DelayPainter {

    private static final ThreadFactory FACTORY = new ThreadFactory() {

        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r, "SimpleLineView thread pool->" + mCount.getAndIncrement());
        }
    };

    private static final ThreadPoolExecutor CACHE_EXECUTOR =
            new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), FACTORY);

    private static Executor sExecutor = CACHE_EXECUTOR;

    private final Map<Painter, List<PixelPoint>> mPainterPool;

    private boolean mIsRunning;

    public TaskPainter(Map<Painter, List<PixelPoint>> painters) {
        super(0);
        mPainterPool = painters;
    }

    @Override
    public void start(final Chain chain, final Action action) {
        mIsRunning = true;
        sExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // 当status为start时重置point
                if (action.getStatus() == STATUS_START) {
                    Utils.resetPointStatus(mPainterPool);
                }
                chain.proceed();
                mIsRunning = false;
            }
        });
    }

    @Override
    public boolean isRunning() {
        return mIsRunning;
    }

    public void setExecutor(Executor executor) {
        sExecutor = executor;
    }
}
