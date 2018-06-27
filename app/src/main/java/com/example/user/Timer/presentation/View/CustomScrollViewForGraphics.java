package com.example.user.Timer.presentation.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.user.Timer.presentation.ModelInPresentationLayer.ModelInPresentationLayer;

import java.util.ArrayList;
import java.util.List;

public class CustomScrollViewForGraphics extends View {
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

    public CustomScrollViewForGraphics(Context context) {
        super(context);
    }

    public CustomScrollViewForGraphics(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollViewForGraphics(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
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

    public void scrollTo(float x) {
        if (mWidth + x >= getWidth()) {
            distanceOfScroll += Math.round(x);
            mWidth += 2 * Math.round(x);
            scrollBy(Math.round(x), 0);
        } else {
            mWidth = getWidth();
            scrollTo(0, 0);
        }
    }

    public void scrollIsFinished() {
        listener.onFetchData();
        int pos;
        if (distanceOfScroll % cellWidth > cellWidth / 2) {
            pos = (distanceOfScroll / cellWidth + 1) * cellWidth;
        } else {
            pos = (distanceOfScroll / cellWidth) * cellWidth;
        }
        valueAnimator = ValueAnimator.ofFloat(99, 100);
        valueAnimator.setDuration(100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float animatedValue = (Float) valueAnimator.getAnimatedValue();
                scrollTo(Math.round(pos * animatedValue / 100), 0);
            }
        });
        valueAnimator.start();
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
                    rect.right = rect.left + cellWidth - 5;
                    rect.bottom = getHeight();
                    canvas.drawRoundRect(rect, 20, 20, paint);
                    drawLine(canvas);
                    if (i == lastDisplayedPosition) {
                        break;
                    }
                }
            }
            for (int i = lastDisplayedPosition + 1; i < viewModelList.size(); i++) {
                rect.left = i * cellWidth + Math.round(getWidth() / 2 - cellWidth / 2);
                rect.top = getHeight() - viewModelList.get(i).getTime();
                rect.right = rect.left + cellWidth - 5;
                rect.bottom = getHeight();
                canvas.drawRoundRect(rect, 20, 20, paint);
                drawLine(canvas);
            }

        }
        if (valueAnimator != null && !valueAnimator.isRunning()) {
            for (int i = 0; i < modelInPresentationLayers.size(); i++) {
                rect.left = i * cellWidth + Math.round(getWidth() / 2 - cellWidth / 2);
                rect.top = getHeight() - modelInPresentationLayers.get(i).getTime();
                rect.right = rect.left + cellWidth - 5;
                rect.bottom = getHeight();
                canvas.drawRoundRect(rect, 20, 20, paint);
                drawLine(canvas);
            }
        }
        viewModelList.clear();

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
}
