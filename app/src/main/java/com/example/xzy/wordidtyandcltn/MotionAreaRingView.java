package com.example.xzy.wordidtyandcltn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

//import com.mm.android.common.utility.UIUtility;

/**
 * 动检区域（环形设置）
 * Created by zhengcong on 2017/09/11.
 */
public class MotionAreaRingView {
//        extends View {
//    private int mSelectAreaColor = Color.argb(150,241,141,0);
//    private int mSmallCircleColor = Color.rgb(25,157,255);
//    private int mBigCircleColor = Color.argb(100,241,141,0);
//    private int mStrokeColor = Color.argb(80,255,255, 255);
//    private Paint mFillPaint;
//    private Paint mStrokePaint;
//    private Paint mBigCirclePaint;
//    private Paint mSmallCirclePaint;
//    private Paint mAreaTextPaint;
//    private Paint mFTTextPaint;
//    private PointF mCircle;
//    private float mMotionAreaRadius;//检测区域半径
//    private float mBigCircleRadius;//外圆半径
//    private float mSmallCircleRadius;//内圆半径
//    private SparseArray<Boolean> mAreaStates;
//    private MotionAreaRingListener mListener;
//    private float mMargin;
//    private Context mContext;
//
//    public interface MotionAreaRingListener{
//        void onMotionAreaRingChangeResult(SparseArray<Boolean> state);
//    }
//
//    public MotionAreaRingView(Context context) {
//        super(context);
//    }
//
//    public MotionAreaRingView(Context context, AttributeSet attrs) {
//        this(context, attrs, -1);
//
//    }
//
//    public MotionAreaRingView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context);
//    }
//
//    private void init(Context context) {
//        mContext = context;
//        mAreaStates = new SparseArray<>();
//        mFillPaint = new Paint();
//        mFillPaint.setAntiAlias(true);
//        mFillPaint.setStyle(Paint.Style.FILL);
//        mStrokePaint = new Paint();
//        mStrokePaint.setAntiAlias(true);
//        mStrokePaint.setStrokeWidth(1f);
//        mStrokePaint.setColor(mStrokeColor);
//        mStrokePaint.setStyle(Paint.Style.STROKE);
//        mSmallCirclePaint = new Paint();
//        mSmallCirclePaint.setAntiAlias(true);
//        mSmallCirclePaint.setColor(mSmallCircleColor);
//        mBigCirclePaint = new Paint();
//        mBigCirclePaint.setAntiAlias(true);
//        mBigCirclePaint.setColor(mBigCircleColor);
//        mAreaTextPaint = new Paint();
//        mAreaTextPaint.setAntiAlias(true);
//        mAreaTextPaint.setTextAlign(Paint.Align.CENTER);
//        mAreaTextPaint.setColor(Color.WHITE);
//        mFTTextPaint = new Paint();
//        mFTTextPaint.setColor(Color.WHITE);
//        mFTTextPaint.setTextSize(35.0f);
//        mFTTextPaint.setTextAlign(Paint.Align.CENTER);
//
//        mMargin = UIUtility.dip2px(context,2f);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        setMeasuredDimension(width, height);
//        mMotionAreaRadius = 0;
//        if (height > width) {//竖屏
//            mBigCircleRadius = width / 2;
//        } else {
//            if (height * 2 > width)
//                mBigCircleRadius = width / 2;
//            else
//                mBigCircleRadius = height;
//        }
//        mBigCircleRadius = mBigCircleRadius - mMargin * 2;
//        mSmallCircleRadius = mBigCircleRadius / 5;
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        if (mAreaStates == null || mAreaStates.size() == 0)
//            return;
//
//        //确定中心点
//        mCircle = new PointF(getWidth() / 2, getHeight() - mMargin);
//        //外圆的外接矩形
//        RectF bigRect = new RectF(mCircle.x - mBigCircleRadius,
//                mCircle.y - mBigCircleRadius, mCircle.x + mBigCircleRadius,
//                mCircle.y + mBigCircleRadius);
//        //外圆的外接矩形
//        RectF motionAreaRect = new RectF(mCircle.x - mMotionAreaRadius,
//                mCircle.y - mMotionAreaRadius, mCircle.x + mMotionAreaRadius,
//                mCircle.y + mMotionAreaRadius);
//        //圆的外接矩形
//        RectF smallRect = new RectF(mCircle.x - mSmallCircleRadius,
//                mCircle.y - mSmallCircleRadius, mCircle.x + mSmallCircleRadius,
//                mCircle.y + mSmallCircleRadius);
//        float textAngle = 180f;
//        for (int i = 0; i < mAreaStates.size(); i++) {
//            if (mAreaStates.get(i)) {
//                mFillPaint.setColor(mSelectAreaColor);
//            } else {
//                mFillPaint.setColor(Color.TRANSPARENT);
//            }
//            //画动检区域
////                RadialGradient mRadialGradientRepeat = new RadialGradient(mCircle.x,
////                        mCircle.y, mBigCircleRadius, new int[]{mSmallCircleColor, Color.WHITE},
////                        null, Shader.TileMode.CLAMP);
////                mFillPaint.setShader(mRadialGradientRepeat);
//            canvas.drawArc(motionAreaRect, 180f + 180f / (float) mAreaStates.size() *  (float) i,
//                    180f / (float) mAreaStates.size(), true, mFillPaint);
//
//            //画外圆
//            canvas.drawArc(bigRect, 180f + 180f / (float) mAreaStates.size() * (float) i,
//                    180f / (float) mAreaStates.size(), true, mBigCirclePaint);
//            //画外圆描边
//            canvas.drawArc(bigRect, 180f + 180f / (float) mAreaStates.size() * (float) i,
//                    180f / (float) mAreaStates.size(), true, mStrokePaint);
//
//            //画文字
//            String text = "Zone" + (i + 1) + (mAreaStates.get(i) ? " On" : " Off");
//            if (i == 0)
//                textAngle = textAngle + 180f / (float) mAreaStates.size() / 2f;
//            else
//                textAngle = textAngle + 180f / (float) mAreaStates.size();
//            drawAreaText(canvas, textAngle, text);
//        }
//
//        //2.23版本去掉内圆
////        canvas.drawArc(smallRect, 180, 180, true, mSmallCirclePaint);
////        canvas.drawArc(smallRect, 180, 180, true, mStrokePaint);
//
//        //2.23版本去掉FT提示
////        drawFTText(canvas);
//    }
//
//    /**
//     * 计算触摸点的角度
//     * @param radiusX 圆心
//     * @param radiusY 圆心
//     * @param x1      触摸点
//     * @param y1      触摸点
//     * @return 触摸点的角度
//     */
//    private float getTouchedPointAngle(float radiusX, float radiusY, float x1,
//                                       float y1) {
//        float differentX = x1 - radiusX;
//        float differentY = y1 - radiusY;
//        double a;
//        double t = differentY
//                / Math.sqrt(differentX * differentX + differentY * differentY);
//
//        if (differentX > 0.0F) {
//            // 0~90
//            if (differentY > 0.0F)
//                a = 6.283185307179586D - Math.asin(t);
//            else
//                // 270~360
//                a = -Math.asin(t);
//        } else if (differentY > 0.0F)
//            // 90~180
//            a = 3.141592653589793D + Math.asin(t);
//        else {
//            // 180~270
//            a = 3.141592653589793D + Math.asin(t);
//        }
//        return (float) (360.0D - a * 180.0D / 3.141592653589793D % 360.0D);
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if (mAreaStates == null || mAreaStates.size() == 0)
//                    break;
//                //点击位置x坐标与圆心的x坐标的距离
//                int distanceX = Math.abs((int) mCircle.x - (int) event.getX());
//                //点击位置y坐标与圆心的y坐标的距离
//                int distanceY = Math.abs((int) mCircle.y - (int) event.getY());
//                //点击位置与圆心的直线距离
//                int distanceZ = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
//
//                if (distanceZ <= mBigCircleRadius) {
//                    float angle = getTouchedPointAngle(mCircle.x, mCircle.y,
//                            event.getX(), event.getY());
//                    if (180.0 <= angle && angle <= 360.0) {
//                        if (mListener != null) {
//                            if (mAreaStates.indexOfValue(true) < 0)
//                                return super.onTouchEvent(event);
//                            int position = getSelectPosition(angle);
//                            mAreaStates.put(position, !mAreaStates.get(position));
//                            invalidate();
//                            mListener.onMotionAreaRingChangeResult(mAreaStates);
//                        }
//                    }
//                }
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
//
//    /**
//     * 根据触摸点的角度计算出在哪个扇形上
//     * @param touchedPointAngle 触摸角度
//     * @return 扇形位置
//     */
//    private int getSelectPosition(float touchedPointAngle) {
//        if (mAreaStates == null || mAreaStates.size() == 0)
//            return 0;
//        for (int i = 0; i < mAreaStates.size(); i++) {
//            if (touchedPointAngle > (180f +  (float) i * 180f / (float) mAreaStates.size()) &&
//                    touchedPointAngle <= (180f +  (float) (i + 1) * 180f
//                            / (float) mAreaStates.size())) {
//                return i;
//            }
//        }
//        return 1;
//    }
//
//    public void setListener(MotionAreaRingListener listener){
//        mListener = listener;
//    }
//
//    public void setStates(SparseArray<Boolean> states) {
//        if (states == null || states.size() == 0)
//            return;
//        this.mAreaStates = states;
//        postInvalidate();
//    }
//
//    public SparseArray<Boolean> getStates() {
//        return this.mAreaStates;
//    }
//
//    /**
//     * 设置动检区域半径
//     * @param percent 百分比
//     */
//    public boolean setMotionAreaRadius(int percent) {
//        if (mAreaStates == null || mAreaStates.size() == 0)
//            return false;
//        if (percent > 0 && percent <= 100) {
//            if (mMotionAreaRadius == 0){
//                for(int i = 0; i < mAreaStates.size(); i++){
//                    mAreaStates.put(i, true);
//                }
//            }
//            mMotionAreaRadius = mBigCircleRadius * (percent / 100f);
//            postInvalidate();
//            return true;
//        } else if(percent == 0){
//            mMotionAreaRadius = 0;
//            for(int i = 0; i < mAreaStates.size(); i++){
//                mAreaStates.put(i, false);
//            }
//            postInvalidate();
//            return true;
//        }
//        return false;
//    }
//
//    //画文字
//    private void drawAreaText(Canvas canvas, float textAngle, String message) {
//        if (mAreaStates == null || mAreaStates.size() == 0 || textAngle < 180 || textAngle > 360)
//            return;
//        Rect rect = new Rect();
//        float textSize;
//        if (mAreaStates.size() <= 3)
//            textSize = UIUtility.dip2px(mContext, 17f);
//        else if (mAreaStates.size() <= 6)
//            textSize = UIUtility.dip2px(mContext, 14f);
//        else
//            textSize = UIUtility.dip2px(mContext, 10f);
//
//        mAreaTextPaint.setTextSize(textSize);
//        mAreaTextPaint.getTextBounds(message, 0, message.length(), rect);
//        if (textAngle > 180 && textAngle <= 270) { //画布坐标系第三象限(数学坐标系第二象限)
//            canvas.drawText(message, (float) (-mBigCircleRadius * 0.65
//                            * Math.cos(Math.toRadians(textAngle - 180))) + mCircle.x,
//                    (float) (-mBigCircleRadius * 0.65 * Math.sin(Math.toRadians(textAngle - 180)))
//                            + rect.height() / 2 + mCircle.y, mAreaTextPaint);
//        } else { //画布坐标系第四象限(数学坐标系第一象限)
//            canvas.drawText(message, (float) (mBigCircleRadius * 0.65
//                            * Math.cos(Math.toRadians(360 - textAngle))) + mCircle.x,
//                    (float) (-mBigCircleRadius * 0.65 * Math.sin(Math.toRadians(360 - textAngle)))
//                            + rect.height() / 2 + mCircle.y, mAreaTextPaint);
//        }
//    }
//
//    private void drawFTText(Canvas canvas) {
//        if (mAreaStates == null || mAreaStates.size() == 0)
//            return;
//        float textSize;
//        Rect rect = new Rect();
//        if (mAreaStates.size() <= 3)
//            textSize = UIUtility.dip2px(mContext, 17f);
//        else if (mAreaStates.size() <= 6)
//            textSize = UIUtility.dip2px(mContext, 14f);
//        else
//            textSize = UIUtility.dip2px(mContext, 10f);
//        mFTTextPaint.setTextSize(textSize);
//        //20ft
//        mFTTextPaint.getTextBounds("20FT", 0, "20FT".length(), rect);
//        canvas.drawText("20FT", mCircle.x,mCircle.y - mBigCircleRadius + rect.height() + 20f, mAreaTextPaint);
//        //5ft
//        mFTTextPaint.getTextBounds("5FT", 0, "5FT".length(), rect);
//        canvas.drawText("5FT", mCircle.x,mCircle.y - mSmallCircleRadius - 20f, mAreaTextPaint);
//    }
//
//
//    public void setRadius(int radius) {
//        mMotionAreaRadius = mBigCircleRadius * (radius / 100f);
//    }
}
