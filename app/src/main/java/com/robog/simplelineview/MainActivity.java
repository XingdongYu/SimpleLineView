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
import com.robog.library.painter.SegProgressPainter;
import com.robog.library.painter.SegmentPainter;

public class MainActivity extends AppCompatActivity {


    private Painter mHook, mCicle, mCube, mLeftCha, mRightCha,
            mHookProgress, mCicleProgress, mCubeProgress, mLeftChaProgress, mRightChaProgress;

    private SimpleLineView mView1, mView2, mView3, mView4;

    {
        PixelShape hookShape = new PixelShape(10, 10, new int[]{43, 65, 38});
        PixelShape circleShape = new PixelShape(10, 10, new int[]{1, 100});
        PixelShape cubeShape = new PixelShape(2, 2, new int[]{1, 2, 4, 3});
        PixelShape leftChaShape = new PixelShape(10, 10, new int[]{34, 67});
        PixelShape rightChaShape = new PixelShape(10, 10, new int[]{37, 64});

        mHook = new SegmentPainter(hookShape, 1000, false);
        mHookProgress = new SegProgressPainter(mHook, new float[]{0.6f, 1});

        mCube = new SegmentPainter(cubeShape, 1000, true);
        mCubeProgress = new SegProgressPainter(mCube, new float[]{0.6f, 1});

        mCicle = new CirclePainter(circleShape, 1000, -120, 360, false);
        mCicleProgress = new CircleProgressPainter(mCicle, new float[]{0, 0.4f}, -120,
                360, false);

        mLeftCha = new SegmentPainter(leftChaShape, 500, false);
        mRightCha = new SegmentPainter(rightChaShape, 500, false);

        mLeftChaProgress = new SegProgressPainter(mLeftCha, new float[]{0f, 0.3f});
        mRightChaProgress = new SegProgressPainter(mRightCha, new float[]{0.3f, 0.6f});

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

        mView1.addPainter(mCicle).addPainter(mHook);
        mView2.addPainter(mCicle).addPainter(mLeftCha).addPainter(mRightCha);

        mView3.addPainter(mCicleProgress).addPainter(mHookProgress).onMain();
        mView4.addPainter(mLeftChaProgress).addPainter(mRightChaProgress)
                .addPainter(mCubeProgress).onMain();

        mSeekBar = findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mView3.setProgress(progress);
                mView4.setProgress(progress);
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
                break;
            case R.id.bt_continue:
                mView1.stick();
                mView2.stick();
                break;
            case R.id.bt_stop:
                mView1.stop();
                mView2.stop();
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mView1.stop();
        mView2.stop();
    }
}
