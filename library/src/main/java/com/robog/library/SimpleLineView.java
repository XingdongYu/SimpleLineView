package com.robog.library;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.robog.library.painter.Painter;
import com.robog.library.painter.TaskPainter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yuxingdong
 * @Time: 2018/2/9
 */

public class SimpleLineView extends View implements Action {

    private static final String TAG = "LitePathView";

    private int mWidth;

    private int mHeight;

    /**
     * 保存painter对应当point对象，避免进度条拖动时频繁创建对象
     */
    private final Map<Painter, List<PixelPoint>> mPointPool = new HashMap<>();

    private final List<Painter> mPainters = new ArrayList<>();

    private Painter mCurrentPainter;

    /**
     * 让之后的操作在线程中执行
     */
    private final Painter mTaskPainter;

    private final Chain mChain;

    private int mProgress;

    public static final int STATUS_START = 0;

    public static final int STATUS_STICK = 1;

    public static final int STATUS_STOP = 2;

    /**
     * View启动状态
     * <p/>
     * 如果是STATUS_START，则会在{@link TaskPainter#start(Chain, Action)}中重置PixelPoint
     */
    private int mStatus;

    public SimpleLineView(Context context) {
        this(context, null);
    }

    public SimpleLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 默认在线程中
        mTaskPainter = new TaskPainter(mPointPool);
        mPainters.add(mTaskPainter);
        mChain = new RealChain(mPainters, 0, this);

    }

    public void setOnFinishListener(Chain.OnFinishListener listener) {
        mChain.setOnFinishListener(listener);
    }

    public void start() {

        mStatus = STATUS_START;
        if (!isRunning()) {
            mChain.proceed();
        }
    }

    public void stick() {

        mStatus = STATUS_STICK;
        if (!isRunning()) {
            mChain.proceed();
        }
    }

    public void stop() {

        mStatus = STATUS_STOP;
        if (mCurrentPainter != null) {
            mCurrentPainter.stop();
        }
    }

    public boolean isRunning() {
        return mTaskPainter.isRunning();
    }

    public SimpleLineView addPainter(Painter painter) {
        if (!mPainters.contains(painter)) {
            mPainters.add(painter);
        }
        return this;
    }

    public SimpleLineView addPainter(List<Painter> painters) {
        mPainters.addAll(painters);
        return this;
    }

    public SimpleLineView remove(Painter painter) {
        mPainters.remove(painter);
        return this;
    }

    public SimpleLineView clear() {
        mPainters.clear();
        mPainters.add(mTaskPainter);
        return this;
    }

    // TODO: 2018/2/16 若setProgress过快，上一步onDraw可能未完成，这种情况下画面会闪。这里先放主线程处理了。
    public SimpleLineView onMain() {
        mPainters.remove(mTaskPainter);
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int w = resolveSize(getMeasuredWidth(), widthMeasureSpec);
        final int h = resolveSize(getMeasuredHeight(), heightMeasureSpec);

        mWidth = w;
        mHeight = h;

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mCurrentPainter != null) {
            mCurrentPainter.onDraw(canvas);
        }
    }

    @Override
    public void update(Painter painter) {

        mCurrentPainter = painter;

        postInvalidate();

    }

    @Override
    public void setProgress(int progress) {
        mProgress = progress;
        mStatus = STATUS_STICK;
        mChain.proceed();
    }

    @Override
    public int getProgress() {
        return mProgress;
    }

    @Override
    public int getStatus() {
        return mStatus;
    }

    @Override
    public List<PixelPoint> fetchCoordinate(Painter painter) {

        List<PixelPoint> pixelPoints = mPointPool.get(painter);

        if (pixelPoints != null) {

            return pixelPoints;

        }

        pixelPoints = new ArrayList<>();
        Utils.setPoint(painter, pixelPoints, mWidth, mHeight);
        mPointPool.put(painter, pixelPoints);

        return pixelPoints;

    }

}
