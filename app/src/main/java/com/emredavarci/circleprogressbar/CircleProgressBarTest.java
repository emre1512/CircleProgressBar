package com.emredavarci.circleprogressbar;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ProgressBar;

/**
 * Created by user on 02.08.2017.
 */

public class CircleProgressBarTest extends ProgressBar {

//    public CircleProgressBar(Context context) {
//        super(context);
//    }

    public CircleProgressBarTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttributes(context, attrs);
    }

    public CircleProgressBarTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs);
    }

    private float radius;

    private void getAttributes(Context context, AttributeSet attrs){

//        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, 0, 0);
//        try {
//            radius = ta.getDimension(R.styleable.CircleProgressBar_radius, 2.3f);
//        } finally {
//            ta.recycle();
//        }

//        GradientDrawable gd = new GradientDrawable();
//        gd.setUseLevel(true);
//        gd.setShape(GradientDrawable.RING);
//        gd.setColor(Color.BLUE);

        CircleProgressBar c = new CircleProgressBar(context);
        c.setLayoutParams(new ViewGroup.LayoutParams(150,150));

        c.setDrawingCacheEnabled(true);

      //  c.buildDrawingCache(true);
        Drawable drawable = new BitmapDrawable(getResources(), c.getDrawingCache());
       // Bitmap b = Bitmap.createBitmap(c.getDrawingCache());
       // c.setDrawingCacheEnabled(false); // clear drawing cache

        setMax(500);
        setProgress(0);
        setProgressDrawable(drawable);
        //    getProgressDrawable().setBounds(bounds);

    }

}
