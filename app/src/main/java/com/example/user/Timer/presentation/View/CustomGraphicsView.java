package com.example.user.Timer.presentation.View;

/**
 * Created by User on 19.05.2018.
 */

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.user.Timer.dataLayer.store.models.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by User on 06.11.2017.
 */

public class CustomGraphicsView extends View {

    private final int indent = 5;
    private int mWidth;
    private int mHeight;
    private Paint paint;
    public RectF drawableRect;
    Palette palette;
    private int amountTypeOfNote;
    private List<ViewModel> columns = new ArrayList<>();
    private List<Integer> setScaleLength = new ArrayList<>();

    public CustomGraphicsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        drawableRect = new RectF();
        palette = new Palette();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onDraw(Canvas canvas) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        for (ViewModel viewModell : columns) {
            paint.setColor(palette.blueColor);

            drawableRect.left = viewModell.startX;
            drawableRect.top = viewModell.startY;
            if (amountTypeOfNote == 1) {
                drawableRect.right = (drawableRect.left + getWidth() / amountTypeOfNote);

            } else {
                drawableRect.right = (drawableRect.left + getWidth() / amountTypeOfNote - indent * metrics.density);
            }
            drawableRect.bottom = (viewModell.endY);
            canvas.drawRect(drawableRect, paint);

            paint.setColor(palette.redColor);
            paint.setTextLocale(Locale.ENGLISH);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
            setTextSizeForWidth(paint, (mWidth / amountTypeOfNote - indent * getResources().getDisplayMetrics().density), viewModell.height, viewModell.name);
            canvas.drawText(viewModell.name, viewModell.startX - indent * getResources().getDisplayMetrics().density / 2, mHeight, paint);

        }
    }

    private void setTextSizeForWidth(Paint paint, float desiredWidth, float desiredHeight,
                                     String text) {
        final float testTextSize = 35f;

        // Get the bounds of the text, using  testTextSize.
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // Calculate the desired size as a proportion of our testTextSize
        float desiredTextSizeWidth = testTextSize * ((desiredWidth) / (bounds.width()));

        float desiredTextSizeHeight = testTextSize * ((desiredHeight) / bounds.height());

        if (desiredTextSizeHeight < desiredTextSizeWidth) {
            paint.setTextSize(desiredTextSizeHeight);
        } else {
            paint.setTextSize(desiredTextSizeWidth);
        }

    }

    public void updateView(List<User> graphicsModelList) {
        if (columns != null && columns.size() != 0) {
            columns.clear();
        }
        int maxValue = -1;
        for (int i = 0; i < graphicsModelList.size(); i++) {
            if (graphicsModelList.get(i).getTime() > maxValue) {
                maxValue = (int) graphicsModelList.get(i).getTime();
            }
        }
        if (maxValue != 0) {
            amountTypeOfNote = graphicsModelList.size();

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 100);
            valueAnimator.setDuration(1000);
            int finalMaxValue = maxValue;
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    Float animatedValue = (Float) animation.getAnimatedValue();

                    for (int i = 0; i < graphicsModelList.size(); i++) {
                        setScaleLength.add(Math.round((graphicsModelList.get(i).getTime() * mHeight / finalMaxValue) * animatedValue) / 100);
                    }

                    for (int i = 0; i < setScaleLength.size(); i++) {
                        ViewModel viewModel = new ViewModel(getPaddingLeft() + i * mWidth / amountTypeOfNote, mHeight - setScaleLength.get(i), mHeight, getDate(graphicsModelList.get(i).getDate()), graphicsModelList.get(i).getId(), Math.round((graphicsModelList.get(i).getTime() * mHeight / finalMaxValue)));
                        columns.add(viewModel);
                        invalidate();
                    }
                    setScaleLength.clear();
                }
            });
            valueAnimator.start();
        }
    }

//    public void invalidate(long id) {
//        if (columns != null && columns.size() != 0) {
//            for (int i = 0; i < columns.size(); i++) {
//                if (columns.get(i).id == id) {
//                    columns.remove(i);
//                }
//            }
//        }
//        invalidate();
//    }

    private String getDate(String milliseconds) {
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(Long.parseLong(milliseconds));  //here your time in miliseconds
        //String date = "" + cl.get(Calendar.DAY_OF_MONTH) + ":" + cl.get(Calendar.MONTH) + ":" + cl.get(Calendar.YEAR);
        String time = "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND);
        return time;
    }

    class Palette {
        int redColor = Color.RED;
        int blueColor = Color.BLUE;
    }

    class ViewModel {

        int startX;
        int startY;
        int endY;
        String name;
        long id;
        int height;

        public ViewModel(int startX, int startY, int endY, String name, long id, int height) {
            this.startX = startX;
            this.startY = startY;
            this.endY = endY;
            this.name = name;
            this.id = id;
            this.height = height;
        }

    }
}


