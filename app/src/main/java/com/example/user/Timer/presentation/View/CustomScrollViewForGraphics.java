package com.example.user.Timer.presentation.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.example.user.Timer.presentation.ModelInPresentationLayer.ModelInPresentationLayer;

import java.util.ArrayList;
import java.util.List;

public class CustomScrollViewForGraphics extends View {
    private final int INDENT = 5;//dp
    private final int ROUNDING = 20;//dp
    private RectF rect;
    private Paint paint;
    private int mWidth;
    private int cellWidth;
    private int distanceOfScroll = 0;
    private int lastDisplayedPosition = 0;
    private HorizontalListener listener;
    private List<ModelInPresentationLayer> modelInPresentationLayers = new ArrayList<>();
    private List<ViewModel> viewModelList = new ArrayList<>();
    private ValueAnimator valueAnimator;
    private GestureDetector mDetector;
    private Scroller scroller;
    private float x = 0;

    public CustomScrollViewForGraphics(Context context) {
        super(context);
        init(context);
    }

    public CustomScrollViewForGraphics(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomScrollViewForGraphics(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    private void init(Context context) {
        scroller = new Scroller(context);
        mDetector = new GestureDetector(context, new MyGestureListener());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        cellWidth = getWidth() / 5;
        rect = new RectF();
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            scrollIsFinished();
            x = 0;
        }

        if (!mDetector.onTouchEvent(event) && event.getAction() == MotionEvent.ACTION_MOVE) {
            if (x == 0) {
                x = event.getX();
            }
            scrollBy(Math.round(x - event.getX()), 0);
            distanceOfScroll+=x - event.getX();
            Log.v("delete", String.valueOf(x) + "event- " + String.valueOf(event.getX()));
            x = event.getX();
        }
        return mDetector.onTouchEvent(event);
    }

    public void scrollTo(float x) {
        if (mWidth + x >= getWidth()) {
            distanceOfScroll += 2 * Math.round(x);
            mWidth += 2 * Math.round(x);
            scrollBy(2 * Math.round(x), 0);
        } else {
            mWidth = getWidth();
            distanceOfScroll = 0;
            scrollTo(0, 0);
        }
    }

    public void scrollIsFinished() {
        listener.onFetchData();
        scrollTo(getPositionOfScroll(distanceOfScroll), 0);
    }

    private int getPositionOfScroll(int distanceOfScroll) {
        int pos;
        if (distanceOfScroll % cellWidth > (cellWidth - INDENT) / 2) {
            pos = (distanceOfScroll / cellWidth + 1) * cellWidth;
        } else {
            pos = (distanceOfScroll / cellWidth) * cellWidth;
        }
        return Math.round(pos);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        if (valueAnimator != null && valueAnimator.isRunning()) {
            if (lastDisplayedPosition != -1) {
                for (int i = 0; i < modelInPresentationLayers.size(); i++) {
                    rect.left = i * cellWidth + Math.round(getWidth() / 2 - cellWidth / 2);
                    rect.top = getHeight() - modelInPresentationLayers.get(i).getTime();
                    rect.right = rect.left + cellWidth - INDENT;
                    rect.bottom = getHeight();
                    canvas.drawRoundRect(rect, ROUNDING, ROUNDING, paint);
                    drawLine(canvas);
                    if (i == lastDisplayedPosition) {
                        break;
                    }
                }
            }
            for (int i = lastDisplayedPosition + 1; i < viewModelList.size(); i++) {
                rect.left = i * cellWidth + Math.round(getWidth() / 2 - cellWidth / 2);
                rect.top = getHeight() - viewModelList.get(i).getTime();
                rect.right = rect.left + cellWidth - INDENT;
                rect.bottom = getHeight();
                canvas.drawRoundRect(rect, ROUNDING, ROUNDING, paint);
                drawLine(canvas);
            }

        }
        if (valueAnimator != null && !valueAnimator.isRunning()) {
            for (int i = 0; i < modelInPresentationLayers.size(); i++) {
                rect.left = i * cellWidth + Math.round(getWidth() / 2 - cellWidth / 2);
                rect.top = getHeight() - modelInPresentationLayers.get(i).getTime();
                rect.right = rect.left + cellWidth - INDENT;
                rect.bottom = getHeight();
                canvas.drawRoundRect(rect, ROUNDING, ROUNDING, paint);
                drawLine(canvas);
            }
        }
        viewModelList.clear();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
        }
    }

    public void drawLine(Canvas canvas) {
        canvas.drawLine(Math.round(rect.left + rect.right) / 2, rect.bottom, Math.round(rect.left + rect.right) / 2, rect.top, getRedPaint());
    }

    public void setModel(List<ModelInPresentationLayer> user, int time) {
        lastDisplayedPosition = modelInPresentationLayers.size() - 1;
        modelInPresentationLayers.addAll(user);
        valueAnimator = ValueAnimator.ofFloat(0, 100);
        valueAnimator.setDuration(time);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float animatedValue = (Float) valueAnimator.getAnimatedValue();
                for (int i = 0; i < modelInPresentationLayers.size(); i++) {
                    viewModelList.add(new ViewModel((long) (modelInPresentationLayers.get(i).getTime() * animatedValue / 100)));
                }
                invalidate();
            }
        });
        valueAnimator.start();
    }


    public void setListener(HorizontalListener listener) {
        this.listener = listener;
    }

    public interface HorizontalListener {
        void onFetchData();
    }

    class ViewModel {
        long time;

        public ViewModel(long time) {
            this.time = time;
        }

        public long getTime() {
            return time;
        }
    }

    private Paint getRedPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        return paint;
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {

            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            scrollTo(distanceX);
            x = 0;
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            float duration = 0.20f;
            float path = -1 * velocityX * duration;
            distanceOfScroll += Math.round(path);
            if (distanceOfScroll <= 0) {
                scrollTo(0, 0);
                distanceOfScroll = 0;
            } else {
                scroller.startScroll(getScrollX(), getScrollY(), getPositionOfScroll(Math.round(path)), 0, 700);
            }
            invalidate();
            return true;
        }
    }
}
