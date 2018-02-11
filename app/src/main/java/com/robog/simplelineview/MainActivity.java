package com.robog.simplelineview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.robog.library.PixelShape;
import com.robog.library.SimpleLineView;
import com.robog.library.painter.CirclePainter;
import com.robog.library.painter.CircleProgressPainter;
import com.robog.library.painter.Painter;
import com.robog.library.painter.RoundPainter;
import com.robog.library.painter.SegProgressPainter;
import com.robog.library.painter.SegmentPainter;

public class MainActivity extends AppCompatActivity {


    private Painter mHookPainter, mCubePainter, mLeftChaPainter, mRightChaPainter,
            mCloseStarPainter, mOpenStarPainter,
            mHookProgressPainter, mCubeProgressPainter,
            mLeftChaProgressPainter, mRightChaProgressPainter;

    private RoundPainter mCiclePainter, mCicleProgressPainter;

    /**
     * 这里的路径点通过获取PS中五角星图片的像素点的x和y值计算得到
     * 例如: 图片像素为200 * 200, x = 100, y = 16, 则当前点为
     * 200*(y-1)+x = 200 * 15 + 100 = 3100
     * 这里的点大约取了一下，并非绝对精确的值
     */
    private static final int[] CLOSE_FIVE_STAR = {3100, 36046, 15987, 15813, 36154};
    private static final int[] OPEN_FIVE_STAR = {3100, 15878, 15813, 23667, 36046, 28100, 36154, 23731, 15987, 15920};

    private SimpleLineView mView1, mView2, mView3, mView4, mView5, mView6;

    {
        PixelShape hookShape = new PixelShape(10, 10, new int[]{43, 65, 38});
        PixelShape circleShape = new PixelShape(10, 10, new int[]{1, 100});
        PixelShape cubeShape = new PixelShape(2, 2, new int[]{1, 2, 4, 3});
        PixelShape leftChaShape = new PixelShape(10, 10, new int[]{34, 67});
        PixelShape rightChaShape = new PixelShape(10, 10, new int[]{37, 64});
        PixelShape closeStarShape = new PixelShape(200, 200, CLOSE_FIVE_STAR);
        PixelShape openStarShape = new PixelShape(200, 200, OPEN_FIVE_STAR);

        // 钩
        mHookPainter = new SegmentPainter(hookShape, 1000, false);
        mHookProgressPainter = new SegProgressPainter(mHookPainter, new float[]{0.6f, 1});

        // 矩形
        mCubePainter = new SegmentPainter(cubeShape, 1000, true);
        mCubeProgressPainter = new SegProgressPainter(mCubePainter, new float[]{0.6f, 1});

        // 圆形
        mCiclePainter = new CirclePainter(circleShape, 1000,
                -120, 360, false);
        mCicleProgressPainter = new CircleProgressPainter(mCiclePainter, new float[]{0, 0.4f});

        // 从左至右一笔
        mLeftChaPainter = new SegmentPainter(leftChaShape, 500, false);
        // 从右至左一笔
        mRightChaPainter = new SegmentPainter(rightChaShape, 500, false);

        mLeftChaProgressPainter = new SegProgressPainter(mLeftChaPainter, new float[]{0f, 0.3f});
        mRightChaProgressPainter = new SegProgressPainter(mRightChaPainter, new float[]{0.3f, 0.6f});

        mCloseStarPainter = new SegmentPainter(closeStarShape, 2000, true);
        mOpenStarPainter = new SegmentPainter(openStarShape, 2000, true);
    }

    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mView1 = findViewById(R.id.view1);
        mView2 = findViewById(R.id.view2);
        mView3 = findViewById(R.id.view3);
        mView4 = findViewById(R.id.view4);
        mView5 = findViewById(R.id.view5);
        mView6 = findViewById(R.id.view6);

        mView1.addPainter(mCiclePainter).addPainter(mHookPainter);
        mView2.addPainter(mCiclePainter).addPainter(mLeftChaPainter).addPainter(mRightChaPainter);
        mView3.addPainter(mCloseStarPainter);
        mView4.addPainter(mOpenStarPainter);

        mView5.addPainter(mCicleProgressPainter).addPainter(mHookProgressPainter).onMain();
        mView6.addPainter(mLeftChaProgressPainter).addPainter(mRightChaProgressPainter)
                .addPainter(mCubeProgressPainter).onMain();

        mSeekBar = findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mView5.setProgress(progress);
                mView6.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_start:
                mView1.start();
                mView2.start();
                mView3.start();
                mView4.start();
                break;
            case R.id.bt_continue:
                mView1.stick();
                mView2.stick();
                mView3.stick();
                mView4.stick();
                break;
            case R.id.bt_stop:
                mView1.stop();
                mView2.stop();
                mView3.stop();
                mView4.stop();
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mView1.stop();
        mView2.stop();
        mView3.stop();
        mView4.stop();
    }
}
