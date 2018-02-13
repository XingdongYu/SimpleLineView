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
     * TaskPainter能让之后的操作在线程中执行
     */
    private final Painter mTaskPainter = new TaskPainter();

    private final Chain mChain;

    private int mProgress;

    /**
     * 是否继续绘制
     */
    private boolean mStick;

    public SimpleLineView(Context context) {
        this(context, null);
    }

    public SimpleLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPainters.add(mTaskPainter);
        mChain = new RealChain(mPainters, 0, this);

    }

    public void setOnFinishListener(Chain.OnFinishListener listener) {
        mChain.setOnFinishListener(listener);
    }

    public void start() {
        mStick = false;
        mChain.proceed();
    }

    public void stick() {
        for (Painter painter : mPainters) {
            painter.stick();
        }
        mStick = true;
        mChain.proceed();
    }

    public void stop() {
        for (Painter painter : mPainters) {
            painter.stop();
        }
        mStick = false;
    }

    public SimpleLineView addPainter(Painter painter) {
        mPainters.add(painter);
        return this;
    }

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
        mStick = true;
        mChain.proceed();
    }

    @Override
    public int getProgress() {
        return mProgress;
    }

    @Override
    public List<PixelPoint> fetchCoordinate(Painter painter) {

        List<PixelPoint> pixelPoints = mPointPool.get(painter);

        if (pixelPoints != null) {

            // 如果不是继续绘制，则重置point
            if (!mStick) {
                PixelUtil.resetPoint(pixelPoints);
            }

            return pixelPoints;

        } else {

            pixelPoints = new ArrayList<>();
            setPoint(painter, pixelPoints);
            mPointPool.put(painter, pixelPoints);

            return pixelPoints;
        }
    }

    private void setPoint(Painter painter, List<PixelPoint> pixelPoints) {

        final PixelShape shape = painter.getShape();
        final int[] path = shape.getPath();
        final int horizontal = shape.getHorizontal();
        final int vertical = shape.getVertical();

        for (int target : path) {

            if (target > horizontal * vertical) {
                throw new IllegalArgumentException("Current coordinate [" + target + "] is invalid!");
            }

            int quotient = target / horizontal;
            int remainder = target % horizontal;

            float x;
            float y;
            float coefficientX;
            float coefficientY;
            if (remainder != 0) {
                // 针对余数不为0时
                coefficientX = remainder - 0.5f;
                coefficientY = quotient + 0.5f;
            } else {
                // 余数为0时
                coefficientX = horizontal - 0.5f;
                coefficientY = quotient - 0.5f;
            }
            x = coefficientX *  mWidth / horizontal;
            y = coefficientY *  mHeight / vertical;

            PixelPoint pixelPoint = new PixelPoint(x, y);
            pixelPoints.add(pixelPoint);
        }
    }

}
