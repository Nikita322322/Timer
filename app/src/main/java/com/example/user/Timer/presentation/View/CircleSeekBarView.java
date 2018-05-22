package com.example.user.Timer.presentation.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by User on 15.05.2018.
 */

public class CircleSeekBarView extends View {
    private int angle = 0;
    private int barWidth = 5;
    private int width;
    private int height;
    private int maxProgress = 60;
    private int progress;
    private float innerRadius;
    private float outerRadius;
    private float cx;
    private float cy;
    private PaintHolder paintHolder;
    private String text = "0";

    public CircleSeekBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CircleSeekBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleSeekBarView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paintHolder = new PaintHolder();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w; // Get View Width
        height = h;// Get View Height
        int size = (width > height) ? height : width; // Choose the smaller
        cx = width / 2; // Center X for circle
        cy = height / 2; // Center Y for circle
        outerRadius = size / 2; // Radius of the outer circle
        innerRadius = outerRadius - barWidth; // Radius of the inner circle
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(cx, cy, outerRadius, paintHolder.getCircleRing());
        int startAngle = 270;
        @SuppressLint("DrawAllocation")
        RectF rect = new RectF(cx - outerRadius, cy - outerRadius, cx + outerRadius, cy + outerRadius);
        canvas.drawArc(rect, startAngle, angle, true, paintHolder.getInnerCirclePaint());
        @SuppressLint("DrawAllocation")
        Rect bounds = new Rect();
        Paint textPaint = paintHolder.getTextPaint(getTextSizeInPixels(14));
        textPaint.getTextBounds(String.valueOf(text), 0, String.valueOf(text).length(), bounds);
        canvas.drawText(String.valueOf(text), cx - bounds.width() / 2, cy - bounds.height() / 2, textPaint);
        super.onDraw(canvas);
    }

    public void setAngle(int angle) {
        this.angle = angle;
        float donePercent = (((float) this.angle) / 360) * 100;
        float progress = (donePercent / 100) * maxProgress;

        if (progress - Math.round(progress) >= 0.45) {
            progress += 0.1;
        }
        text = String.valueOf(Math.round(progress));
        setProgress(Math.round(progress));
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if (this.progress != progress) {
            this.progress = progress;
            int newPercent = (this.progress * 100) / this.maxProgress;
            int newAngle = (newPercent * 360) / 100;
            this.setAngle(newAngle);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        boolean up = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moved(x, y, up);
                break;
            case MotionEvent.ACTION_MOVE:
                moved(x, y, up);
                break;
            case MotionEvent.ACTION_UP:
                up = true;
                moved(x, y, up);
                break;
        }
        return true;
    }

    private void moved(float x, float y, boolean up) {
        float distance = (float) Math.sqrt(Math.pow((x - cx), 2) + Math.pow((y - cy), 2));
        float adjustmentFactor = 100;
        if (distance < outerRadius + adjustmentFactor && distance > innerRadius - adjustmentFactor && !up) {

            float degrees = (float) ((float) ((Math.toDegrees(Math.atan2(x - cx, cy - y)) + 360.0)) % 360.0);
            // and to make it count 0-360
            if (degrees < 0) {
                degrees += 2 * Math.PI;
            }

            setAngle(Math.round(degrees));
            invalidate();

        } else {
            invalidate();
        }
    }

    private class PaintHolder {
        private Paint textPaint;
        private Paint circleRing;
        private Paint innerCirclePaint;

        private PaintHolder() {
            initTextPaint();
            initCirclePaint();
            initInnerCircle();
        }

        private void initCirclePaint() {
            circleRing = new Paint();
            circleRing.setColor(Color.LTGRAY);
            circleRing.setAntiAlias(true);
            circleRing.setStrokeWidth(5);
        }

        private void initInnerCircle() {
            innerCirclePaint = new Paint();
            innerCirclePaint.setColor(Color.parseColor("#ff33b5e5"));
            innerCirclePaint.setAntiAlias(true);
            innerCirclePaint.setStrokeWidth(5);
            innerCirclePaint.setStyle(Paint.Style.FILL);
        }

        private void initTextPaint() {
            textPaint = new Paint();
            textPaint.setColor(Color.BLACK);
            textPaint.setAntiAlias(true);
            textPaint.setTypeface(Typeface.DEFAULT);
            textPaint.setStyle(Paint.Style.FILL);
        }

        Paint getCircleRing() {
            return circleRing;
        }

        Paint getTextPaint(int dp) {
            textPaint.setTextSize(getTextSizeInPixels(dp));
            return textPaint;
        }

        Paint getInnerCirclePaint() {
            return innerCirclePaint;
        }

    }

    private int getTextSizeInPixels(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, getResources().getDisplayMetrics());
    }
}
