package com.example.xzy.wordidtyandcltn.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by xzy on 6/24/18.
 */

public class BaseActivity extends Activity {
    private Toast mToast;
    protected Context mContext;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        mContext = this;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCanceledOnTouchOutside(false);



    }

    protected void toast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }


    protected void showProgressDialog(int layoutId) {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
            mProgressDialog.setContentView(layoutId);
        }
    }

    protected void dissmissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
