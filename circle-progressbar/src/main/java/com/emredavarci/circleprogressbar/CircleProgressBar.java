package com.emredavarci.circleprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by M. Emre Davarci on 02.08.2017.
 */

public class CircleProgressBar extends View {

    private Paint progressBarPaint;
    private Paint bacgroundPaint;
    private Paint textPaint;

    private float mRadius;
    private RectF mArcBounds = new RectF();

    float drawUpto = 0;

    public CircleProgressBar(Context context) {
        super(context);

        // create the Paint and set its color

    }

    private int progressColor;
    private int backgroundColor;
    private float strokeWidthDimension;
    private float backgroundWidth;
    private boolean roundedCorners;
    private float maxValue;

    private int progressTextColor = Color.BLACK;
    private float textSize = 18;
    private String text = "";
    private String suffix = "";
    private String prefix = "";

    int defStyleAttr;

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.defStyleAttr = defStyleAttr;
        initPaints(context, attrs);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initPaints(context, attrs);
    }

    private void initPaints(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, defStyleAttr, 0);

        progressColor = ta.getColor(R.styleable.CircleProgressBar_progressColor, Color.BLUE);
        backgroundColor = ta.getColor(R.styleable.CircleProgressBar_backgroundColor, Color.GRAY);
        strokeWidthDimension = ta.getFloat(R.styleable.CircleProgressBar_strokeWidthDimension, 10);
        backgroundWidth = ta.getFloat(R.styleable.CircleProgressBar_backgroundWidth, 10);
        roundedCorners = ta.getBoolean(R.styleable.CircleProgressBar_roundedCorners, false);
        maxValue = ta.getFloat(R.styleable.CircleProgressBar_maxValue, 100);
        progressTextColor = ta.getColor(R.styleable.CircleProgressBar_progressTextColor, Color.BLACK);
        textSize = ta.getDimension(R.styleable.CircleProgressBar_textSize, 18);
        suffix = ta.getString(R.styleable.CircleProgressBar_suffix);
        prefix = ta.getString(R.styleable.CircleProgressBar_prefix);
        text = ta.getString(R.styleable.CircleProgressBar_progressText);

        progressBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressBarPaint.setStyle(Paint.Style.FILL);
        progressBarPaint.setColor(progressColor);
        progressBarPaint.setStyle(Paint.Style.STROKE);
        progressBarPaint.setStrokeWidth(strokeWidthDimension * getResources().getDisplayMetrics().density);
        if(roundedCorners){
            progressBarPaint.setStrokeCap(Paint.Cap.ROUND);
        }else{
            progressBarPaint.setStrokeCap(Paint.Cap.BUTT);
        }
        String pc = String.format("#%06X", (0xFFFFFF & progressColor));
        progressBarPaint.setColor(Color.parseColor(pc));

        bacgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bacgroundPaint.setStyle(Paint.Style.FILL);
        bacgroundPaint.setColor(backgroundColor);
        bacgroundPaint.setStyle(Paint.Style.STROKE);
        bacgroundPaint.setStrokeWidth(backgroundWidth * getResources().getDisplayMetrics().density);
        bacgroundPaint.setStrokeCap(Paint.Cap.SQUARE);
        String bc = String.format("#%06X", (0xFFFFFF & backgroundColor));
        bacgroundPaint.setColor(Color.parseColor(bc));

        ta.recycle();

        textPaint = new TextPaint();
        textPaint.setColor(progressTextColor);
        String c = String.format("#%06X", (0xFFFFFF & progressTextColor));
        textPaint.setColor(Color.parseColor(c));
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

        //paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = Math.min(w, h) / 2f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        int size = Math.min(w, h);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float mouthInset = mRadius / 3;
        mArcBounds.set(mouthInset, mouthInset, mRadius * 2 - mouthInset, mRadius * 2 - mouthInset);
        canvas.drawArc(mArcBounds, 0f, 360f, false, bacgroundPaint);
        canvas.drawArc(mArcBounds, 270f, drawUpto / getMaxValue() * 360, false, progressBarPaint);

        if(TextUtils.isEmpty(suffix)){
            suffix = "";
        }

        if(TextUtils.isEmpty(prefix)){
            prefix = "";
        }

        String drawnText = prefix + text + suffix;

        if (!TextUtils.isEmpty(text)) {
            float textHeight = textPaint.descent() + textPaint.ascent();
            canvas.drawText(drawnText, (getWidth() - textPaint.measureText(drawnText)) / 2.0f, (getWidth() - textHeight) / 2.0f, textPaint);
        }

    }


    public void setProgress(float f){
        drawUpto = f;
        invalidate();
    }

    public float getProgress(){
        return drawUpto;
    }

    public float getProgressPercentage(){
        return drawUpto/getMaxValue() * 100;
    }

    public void setProgressColor(int color){
        progressColor = color;
        progressBarPaint.setColor(color);
        invalidate();
    }

    public void setProgressColor(String color){
        progressBarPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public void setBackgroundColor(int color){
        backgroundColor = color;
        bacgroundPaint.setColor(color);
        invalidate();
    }

    public void setBackgroundColor(String color){
        bacgroundPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public float getMaxValue(){
        return maxValue;
    }

    public void setMaxValue(float max){
        maxValue = max;
        invalidate();
    }

    public void setStrokeWidthDimension(float width){
        strokeWidthDimension = width;
        invalidate();
    }

    public float getStrokeWidthDimension(){
        return strokeWidthDimension;
    }

    public void setBackgroundWidth(float width){
        backgroundWidth = width;
        invalidate();
    }

    public float getBackgroundWidth(){
        return backgroundWidth;
    }

    public void setText(String progressText){
        text = progressText;
        invalidate();
    }

    public String getText(){
        return text;
    }

    public void setTextColor(int color){
        progressTextColor = color;
        textPaint.setColor(color);
        invalidate();
    }

    public void setTextColor(String color){
        textPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public int getTextColor(){
        return progressTextColor;
    }

    public void setSuffix(String suffix){
        this.suffix = suffix;
        invalidate();
    }

    public String getSuffix(){
        return suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
        invalidate();
    }
}


