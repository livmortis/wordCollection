package com.example.xzy.wordidtyandcltn.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xzy.wordidtyandcltn.R;
import com.example.xzy.wordidtyandcltn.util.UIUtility;
import com.example.xzy.wordidtyandcltn.view.CustomHScrollView;
import com.example.xzy.wordidtyandcltn.view.CustomScrollView;
import com.example.xzy.wordidtyandcltn.view.AreaView;

/**
 * Created by xzy on 6/22/18.
 */

public class SelectAreaActivity extends Activity implements View.OnClickListener {
    //labels
    private ImageView iviv;
    private TextView tvConfirm;
    private TextView tvDelete;
    private RelativeLayout rl;

    //view
    private CustomScrollView mCustomScrollView;
    private CustomHScrollView mCustomHScrollView;
    private AreaView mAreaView;

    //variable
    private byte[][] areaByte;
    private int mMotionWidth;                                //区域控件宽
    private int mMotionHeight;                                //区域控件高



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_settings_area_settings);
        initview();
        initAreaView();
    }


    private void initview() {
//        rl = findViewById(R.id.window_layout);
        mCustomScrollView = findViewById(R.id.motion_vScroll);
        mCustomHScrollView = findViewById(R.id.motion_hScroll);
        mAreaView = findViewById(R.id.motion_areaView);
        iviv = findViewById(R.id.iv);
        tvConfirm = findViewById(R.id.area_confirm);
        tvDelete = findViewById(R.id.area_clear);
        tvDelete.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
    }


    private void initAreaView() {
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth;
        screenWidth = display.getWidth();
        mMotionWidth = screenWidth - UIUtility.dip2px(SelectAreaActivity.this, 30);
        mMotionHeight = mMotionWidth * 4 / 5;


        RelativeLayout winP = (RelativeLayout) findViewById(R.id.window_layout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mMotionWidth, mMotionHeight);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        winP.setLayoutParams(params);

        mCustomScrollView.setLayoutParams(new RelativeLayout.LayoutParams(mMotionWidth, mMotionHeight));
        mCustomHScrollView.setLayoutParams(new FrameLayout.LayoutParams(mMotionWidth, mMotionHeight));
        iviv.setLayoutParams(new FrameLayout.LayoutParams(mMotionWidth, mMotionHeight));
        mAreaView.setLayoutParams(new FrameLayout.LayoutParams(mMotionWidth, mMotionHeight));
        mAreaView.setScrollView(mCustomScrollView, mCustomHScrollView);
        mAreaView.setImageView(iviv);
        mAreaView.setMode(AreaView.EDIT);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.area_confirm:
                if (mAreaView != null) {
                    areaByte = mAreaView.getAreas();
                    for(int i = 0 ; i < areaByte.length ; i++) {
                        for(int j = 0 ; j < areaByte[0].length ; j++) {
                            Log.d("xzyAreaByte", areaByte[i][j]+"");
                        }
                    }
                }
                break;
            case R.id.area_clear:
                if (mAreaView != null) {
                    if (areaByte != null && areaByte.length > 0)
                        mAreaView.clearAerea(areaByte);
                    else
                        mAreaView.clearAerea();
                }
                break;

        }

    }
}
