package com.example.liushan.dropgooddemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class NumberView extends View implements Animator.AnimatorListener{
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;
    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private float upY = 0;
    private float downY = 150;
    private int mTextUp =1;
    private int mTextCente = 1;
    private int mTextDown = 1;
    private int mTempNum = 1;
    private int mTextSize = 100;
    private int mAnimType;
    private String mSameTextUpStr;
    private String mDiffTextUpStr ;
    private String mSameTextCenStr;
    private String mDiffTextCenStr;
    private String mSameTextDownStr;
    private String mDiffTextDownStr;
    private Paint paint = new Paint();
    public NumberView(Context context) {
        super(context);
        init(null, 0);
    }

    public NumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public NumberView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.NumberView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.NumberView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.NumberView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.NumberView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.NumberView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.NumberView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO: consider storing these as member variables to reduce
        int height = getHeight();
        int weith = getWidth();
        paint.setTextSize(mTextSize);
        canvas.drawColor(Color.parseColor("#2196F3"));
        String textUp = String.valueOf(mTextUp);
        String textCente = String.valueOf(mTextCente);
        String textDown = String.valueOf(mTextDown);
        float textUpWidth = paint.measureText(textUp);
        float textCenteWidth = paint.measureText(textCente);
        float textDownWidth = paint.measureText(textDown);
        if(mDiffTextUpStr !=null && mDiffTextUpStr !="") {
            canvas.drawText(mSameTextUpStr,weith/2-textUpWidth/2,0,paint);
            canvas.drawText(mDiffTextUpStr,weith/2-textUpWidth/2+paint.measureText(mSameTextUpStr),0+upY,paint);
        }else{
            canvas.drawText(textUp,weith/2-textUpWidth/2,0+upY,paint);
        }
        if(mDiffTextCenStr !=null && mDiffTextCenStr !="") {
            canvas.drawText(mSameTextCenStr,weith/2-textCenteWidth/2,height/2+mTextSize/2,paint);
            canvas.drawText(mDiffTextCenStr,weith/2-textCenteWidth/2+paint.measureText(mSameTextCenStr),height/2+mTextSize/2+upY,paint);
        }else{
            canvas.drawText(textCente,weith/2-textCenteWidth/2,height/2+mTextSize/2+upY,paint);
            Log.v("ls","onDraw_textCente is "+textCente+"upY is "+upY);
        }
        if(mDiffTextDownStr !=null && mDiffTextDownStr !="") {
            canvas.drawText(mSameTextDownStr,weith/2-textDownWidth/2,height+mTextSize,paint);
            canvas.drawText(mDiffTextDownStr,weith/2-textDownWidth/2+paint.measureText(mSameTextDownStr),height+mTextSize+upY,paint);
        }else{
            canvas.drawText(textDown,weith/2-textDownWidth/2,height+mTextSize+upY,paint);
        }
    }


    private void compareNum(String numOne,String numTwo) {
        cleanCompareNum();
        int numOneLength = numOne.length();
        int numTwoLength = numTwo.length();
        if(numOneLength != numTwoLength || (numOneLength == 1 && numTwoLength == 1)) {
            mSameTextCenStr = numTwo;
            mDiffTextCenStr = "";
            if(mAnimType == 1){//plus
                mSameTextUpStr = numOne;
                mDiffTextUpStr = "";
            }else {
                mSameTextDownStr = numOne;
                mDiffTextDownStr = "";
            }
        }else {
            int i = 0;
            while (i<numOneLength) {
                if(numOne.charAt(i) != numTwo.charAt(i)) {
                    mDiffTextCenStr +=numTwo.charAt(i);
                    if(mAnimType == 1 ) {
                        mDiffTextUpStr += numOne.charAt(i);
                    }else {
                        mDiffTextDownStr += numOne.charAt(i);
                    }
                }else {
                    mSameTextCenStr += numTwo.charAt(i);
                    if(mAnimType == 1) {
                        mSameTextUpStr += numOne.charAt(i);
                    }else {
                        mSameTextDownStr  += numOne.charAt(i);
                    }
                }
                i++;
            }
        }
   }


    public void startAnim(int type) {
        int height = getHeight();
        mAnimType = type;

        ObjectAnimator animatorPlus = ObjectAnimator.ofFloat(this,"UpY",0,(height/2+mTextSize/2));
        if(type == 1)//plus
        {
            mTextCente = mTempNum;
            mTempNum++;
            mTextUp = mTextCente+1;
            mTextDown =mTextCente-1;
            String textUp = String.valueOf(mTextUp);
            String textCenter = String.valueOf(mTextCente);
            compareNum(textUp,textCenter);
            animatorPlus.start();
        }else {
            if(mTempNum<1){
                return;
            }
            ObjectAnimator animatorMinus =ObjectAnimator.ofFloat(this,"UpY",0,-(height/2+mTextSize/2));
            if(mTempNum>0) {
                mTextCente = mTempNum;
                mTempNum--;
                mTextUp =mTextCente+1 ;
                mTextDown = mTextCente-1;
            }
            String textCenter = String.valueOf(mTextCente);
            String textDown = String.valueOf(mTextDown);
            compareNum(textDown,textCenter);
            animatorMinus.start();
        }
        Log.v("ls","mTextUp is "+mTextUp+">>mTextCente is "+mTextCente+">>mTextDown is "+mTextDown);

    }

    public void setNum(int num){
        mTempNum = num;
        mTextCente = mTempNum;
        upY = 0;
        cleanCompareNum();
        invalidate();
    }

    private void cleanCompareNum() {
        mSameTextUpStr = "";
        mDiffTextUpStr = "";
        mSameTextCenStr = "";
        mDiffTextCenStr = "";
        mSameTextDownStr = "";
        mDiffTextDownStr = "";
    }

    public void setUpY(float y){
        this.upY = y;
       // Log.v("ls","y is "+y);
        invalidate();

    }

    public void setDownY(float y){
        this.downY = y;
        invalidate();

    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }


}
