package com.robog.simplelineview.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.robog.simplelineview.R;

/**
 * Author: yuxingdong
 * Time: 2018/2/16
 */

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        findViewById(R.id.bt_various_sample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VariousSampleActivity.launch(LaunchActivity.this);
            }
        });

        findViewById(R.id.bt_add_sample).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSampleActivity.launch(LaunchActivity.this);
            }
        });
    }
}
