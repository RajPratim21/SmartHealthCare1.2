package com.example.rajpratim.completehealthcare.Calories_detect.ECG;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by RajPratim on 6/9/2016.
 */

public class GraphView extends View {

    private Paint paint;
    private Path path;
    private double availableWidth;
    private double availableHeight;
    int count;
    int counter;
    private double data;

    //float spacing = (float)0.5;
    //float spacing = (float)0.25;
    float spacing = (float)10.00;

    public GraphView(Context context)
    {
        this(context, null);
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context,attrs);
        setBackgroundColor(Color.BLACK);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        path = new Path();
        counter = 0;
    }
    public GraphView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        availableWidth = getMeasuredWidth();
        availableHeight = getMeasuredHeight();
    }

    public void plotProcedure(double data) {
        this.data = data;
        if (counter > availableWidth/spacing) {counter = 0; path.reset(); }
        if (counter == 0) { path.moveTo(0, (float) (availableHeight/2 - data*100));}
        else {

            path.lineTo( counter*spacing, (float) ( availableHeight/2- data*100));
        }
        counter++;
        // Force redraw
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        paint.setTextSize(48);
        canvas.drawText(String.valueOf(data),(float)availableWidth/10,(float)availableHeight/10, paint);
        //canvas.drawText(String.valueOf(data), availableWidth/10, availableHeight/10, paint);
        canvas.drawPath(path, paint);
    }
}
