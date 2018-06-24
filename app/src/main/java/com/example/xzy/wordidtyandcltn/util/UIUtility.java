/**
 * 
 */
package com.example.xzy.wordidtyandcltn.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 13098
 * 
 */
public class UIUtility
{
	private static long 		    mLastClickTime;  		//快速点击记录的最后时间
    private static long             mLastIntervalClick;
	
	public static int dip2px(Context context, float dpValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        Log.d("xzyPixel-displayMetrics", scale+"");
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
	    
	/**
     * Activity中获取根视图
     * <p></p>
     * @author jianghongen 2015-3-18 下午3:26:19
     * @param activity
     * @return
     */
    public static View getRootView(Activity activity)
    {
    	return ((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);
    }
    
    /**
     * 判断是否是快速点击
     * <p></p>
     * @author jianghongen 2014-9-26 上午10:27:32
     * @return
     */
    public static boolean isFastDoubleClick() 
    {  
	    long time = System.currentTimeMillis();
	    
	    if (time - mLastClickTime < 500)
	    {
    		mLastClickTime = time;
	    	return true;
	    }
	    mLastClickTime = time;
	    return false;     
	}

    /**
     * 判断是否是在间隔时间内点击 32752
     * <p></p>
     * @return
     */
    public static boolean isFastClick(long intervalTime)
    {
        long time = System.currentTimeMillis();

        boolean isFastClick = (time - mLastIntervalClick < intervalTime);
        mLastIntervalClick = time;
        Log.i("32752fast", "interval Time:" + (time - mLastIntervalClick)  +
                (isFastClick ? "快速点击" : "正常点击")
        );
        return isFastClick;
    }

    /**
     * 获取状态栏高度
     * <p></p>
     * @author jianghongen 2014-7-22 上午09:50:07
     * @return
     */
    public  static int  getStatusBarHeight(Context context)
    {
        Class<?> c = null;
        Object object = null;
        Field field = null;
        int x = 0;
        int statusBar = 0;
        try
        {
            c =  Class.forName("com.android.internal.R$dimen");
            object = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(object).toString());
            statusBar = context.getResources().getDimensionPixelSize(x);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return statusBar;
    }

    /**
     * 设置按钮选中与否
     * <p></p>
     * @author feidan 2015年6月19日 下午4:46:46
     * @param selected
     * @param views
     */
    public static void setSelected(boolean selected, View... views)
    {
        for (View view : views)
        {
            view.setSelected(selected);
        }
    }

    /**
     * 设置空间是否可用（不置灰）
     * <p></p>
     * @author feidan 2015年9月18日 上午10:01:12
     * @param enabled
     * @param views
     */
    public static void setEnabled(boolean enabled, View...views)
    {
        for (View view : views)
        {
            view.setEnabled(enabled);
        }
    }

    /**
     * 启用/禁用控件，包括所有子控件
     * <p></p>
     * @author jianghongen 2015-7-25 上午10:27:39
     * @param viewGroups
     * @param enabled
     */
    public static void setEnabledSub(boolean enabled, ViewGroup... viewGroups)
    {
        for (ViewGroup viewGroup : viewGroups)
        {
            viewGroup.setEnabled(enabled);
            for(int i = 0; i < viewGroup.getChildCount(); i++)
            {
                View v = viewGroup.getChildAt(i);
                if(v instanceof ViewGroup)
                {
                    setEnabledSub(enabled, (ViewGroup)v);
                }
                else
                {
                    setEnabled(enabled, v);
                }
            }
        }
    }

    /**
     * 设置控件可用与否 (置灰)
     * @param views      被设置的控件
     * @param enabled   是否可用
     */
    public static void setEnabledEX(boolean enabled, View...views)
    {
        for (View view : views)
        {
            if (enabled)
            {
                view.setEnabled(enabled);
                view.clearAnimation();
            }
            else
            {
                Animation aniAlp = new AlphaAnimation(1f, 0.5f);
                aniAlp.setDuration(0);
                aniAlp.setInterpolator(new AccelerateDecelerateInterpolator());
                aniAlp.setFillEnabled(true);
                aniAlp.setFillAfter(true);
                view.startAnimation(aniAlp);
                view.setEnabled(enabled);
            }
        }
    }

    /**
     * 遍历布局，禁用/启用所有子控件
     * <p></p>
     * @author jianghongen 2015-7-25 上午10:27:39
     * @param viewGroup
     * @param enabled
     */
    public static void setEnabledSubControls(ViewGroup viewGroup, boolean enabled)
    {
        setEnabledEX(enabled, viewGroup);
        for(int i = 0; i < viewGroup.getChildCount(); i++)
        {
            View v = viewGroup.getChildAt(i);
            if(v instanceof ViewGroup)
            {
                setEnabledSubControls((ViewGroup)v, enabled);
            }
            else
            {
                setEnabledEX(enabled, v);
            }
        }
    }

    /**
     * 隐藏/显示
     * <p></p>
     * @author feidan 2015年10月9日 上午10:25:03
     * @param visibility
     * @param views
     */
    public static void setVisibility(int visibility, View...views)
    {
        for (View view : views)
        {
            view.setVisibility(visibility);
        }
    }

    /**
     * 判断是否有特殊字符串
     *
     * @param str
     * @return 有特殊字符串返回false
     */
    public static boolean stringFilter(String str)
    {
        String regEx = "[`~!@#$%^&*+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        //String regEx = "[!$&*()+=\'\"<>,&*]";
//        String regEx = "[!$&*()+=\\/'\"<>,~\\[\\]#@%^:?]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if (m.find())
        {
            return false;
        }
//        else if(str.contains("\\"))
//        {
//        	return false;
//        }
        return true;
    }

    /**
     * 将byte数组转换成string
     * <p></p>
     * @author liujianmei 2016年1月19日14:10:13
     * @param byteArray
     * @return
     */
    public static String byteArray2String(byte[] byteArray)
    {
        String strContent = "";
        try
        {
            strContent  = new String(byteArray, "utf-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        int splitIndex = strContent.indexOf('\u0000');
        if(splitIndex == -1)
        {
            return strContent;
        }
        else
        {
            return strContent.substring(0, splitIndex);
        }
    }


    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }



}
