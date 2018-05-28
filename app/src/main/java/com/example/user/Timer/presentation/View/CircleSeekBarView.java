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
    private final String SECOND = "c";
    private final int barWidth = 5;//dp
    private final int TEXT_SIZE = 17;//sp
    private final int RADIUS_SMALLER_CIRCLE = 20;
    private PaintHolder paintHolder;
    private String text = "0";
    private int angle = 0;
    private int maxProgress = 60;
    private int progress;
    private float innerRadius;
    private float outerRadius;
    private float cx;
    private float cy;

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
        setMaxProgress(60);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int size = (w > h) ? h : w;
        cx = w / 2;
        cy = h / 2;
        outerRadius = (size - 2 * RADIUS_SMALLER_CIRCLE) / 2;
        innerRadius = outerRadius - barWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(cx, cy, outerRadius, paintHolder.getCircleRing());
        int startAngle = 270;
        @SuppressLint("DrawAllocation")
        RectF rect = new RectF(cx - outerRadius, cy - outerRadius, cx + outerRadius, cy + outerRadius);
        canvas.drawArc(rect, startAngle, angle, true, paintHolder.getInnerCirclePaint(Color.parseColor("#ff33b5e5")));
        canvas.drawCircle(cx, cy, innerRadius, paintHolder.getInnerCirclePaint(Color.WHITE));
        float endX = (float) (Math.cos(Math.toRadians(270 + angle)) * outerRadius + cx);
        float endY = (float) (Math.sin(Math.toRadians(270 + angle)) * outerRadius + cy);
        canvas.drawCircle(endX, endY, RADIUS_SMALLER_CIRCLE, paintHolder.getSmallerCirclePaint());
        @SuppressLint("DrawAllocation")
        Rect bounds = new Rect();
        Paint textPaint = paintHolder.getTextPaint(getTextSizeInSp(TEXT_SIZE));
        textPaint.getTextBounds(String.valueOf(Integer.parseInt(text) - Math.round(progress)) + SECOND, 0, (String.valueOf(Integer.parseInt(text) - Math.round(progress)) + SECOND).length(), bounds);
        canvas.drawText(String.valueOf(Integer.parseInt(text) - Math.round(progress)) + SECOND, cx - bounds.width() / 2, cy - bounds.height() / 2, textPaint);

        super.onDraw(canvas);
    }

    public void setAngle(int angle) {
        this.angle = angle;
        float donePercent = (((float) this.angle) / 360) * 100;
        float progress = (donePercent / 100) * 60;

        if (progress - Math.round(progress) >= 0.45) {
            progress += 0.1;
        }
        setProgress(Math.round(progress));
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        text = String.valueOf(maxProgress);
    }

    public void setProgress(int progress) {
        if (this.progress != progress) {
            this.progress = progress;
            int newPercent = (this.progress * 100) / 60;
            int newAngle = (newPercent * 360) / 100;
            this.setAngle(newAngle);
        }
    }

    public void minuteHasPassed() {
        text = String.valueOf(Integer.parseInt(text) - 60);
    }

    public int getTime() {
        return Integer.parseInt(text);
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
        private Paint smallerCirclePaint;

        private PaintHolder() {
            initTextPaint();
            initCirclePaint();
            initInnerCircle();
            initSmallerCircle();
        }

        private void initSmallerCircle() {
            smallerCirclePaint = new Paint();
            smallerCirclePaint.setColor(Color.parseColor("#03a9f4"));
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

        Paint getSmallerCirclePaint() {
            return smallerCirclePaint;
        }

        Paint getCircleRing() {
            return circleRing;
        }

        Paint getTextPaint(int dp) {
            textPaint.setTextSize(getTextSizeInSp(dp));
            return textPaint;
        }

        Paint getInnerCirclePaint() {
            return innerCirclePaint;
        }

        Paint getInnerCirclePaint(int color) {
            innerCirclePaint.setColor(color);
            return innerCirclePaint;
        }

    }

    private int getTextSizeInSp(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dp, getResources().getDisplayMetrics());
    }
}
