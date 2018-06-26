package com.example.user.Timer.presentation.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.user.Timer.presentation.ModelInPresentationLayer.ModelInPresentationLayer;

import java.util.ArrayList;
import java.util.List;

public class CustomViewForGraphic extends View {
    private int mWidth;
    private int mHeight;
    private DisplayMetrics metrics;
    private ModelInPresentationLayer user = new ModelInPresentationLayer();
    private ViewModel viewModel;
    public static int INDENT_FOR_TEXT = 30;//dp

    public CustomViewForGraphic(Context context) {
        super(context);
        init();
    }

    public CustomViewForGraphic(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomViewForGraphic(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        metrics = new DisplayMetrics();
        setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (viewModel != null) {
            RectF rect = new RectF(mWidth / 4, mHeight - viewModel.getTime(), mWidth - mWidth / 4, mHeight - INDENT_FOR_TEXT);
            Paint paint = new Paint();
            canvas.drawRoundRect(rect, 20, 20, paint);
            String text;
            if (user.getTime() < 50) {
                text = "авг";
            } else if (user.getTime() < 80) {
                text = "сен";
            } else if (user.getTime() < 110) {
                text = "окт";
            } else if (user.getTime() < 150) {
                text = "ноя";
            } else {
                text = "май";
            }
            paint.setAntiAlias(true);
            paint.setTypeface(Typeface.DEFAULT);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            setTextSizeForWidth(paint, mWidth - INDENT_FOR_TEXT, INDENT_FOR_TEXT, text);
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            canvas.drawText(text, (mWidth - bounds.width()) / 2.0f, mHeight, paint);
        }
    }

    private void setTextSizeForWidth(Paint paint, float desiredWidth, float desiredHeight,
                                     String text) {
        final float testTextSize = 35f;

        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        float desiredTextSizeWidth = testTextSize * ((desiredWidth) / (bounds.width()));

        float desiredTextSizeHeight = testTextSize * ((desiredHeight) / bounds.height());

        if (desiredTextSizeHeight < desiredTextSizeWidth) {
            paint.setTextSize(desiredTextSizeHeight);
        } else {
            paint.setTextSize(desiredTextSizeWidth);
        }
    }

    public void setModel(ModelInPresentationLayer user,int time) {
        this.user = user;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100);
        valueAnimator.setDuration(time);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float animatedValue = (Float) valueAnimator.getAnimatedValue();
                viewModel = new ViewModel((long) (user.getTime() * animatedValue / 100));
                invalidate();
            }
        });
        valueAnimator.start();
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

}
