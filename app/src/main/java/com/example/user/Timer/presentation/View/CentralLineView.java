package com.example.user.Timer.presentation.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CentralLineView extends View {
    public CentralLineView(Context context) {
        super(context);
    }

    public CentralLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CentralLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        Path path = new Path();
        path.moveTo(getWidth() / 2, 0);
        path.lineTo(getWidth() / 2 - 50, 50);
        path.lineTo(getWidth() / 2 + 50, 50);
        canvas.drawPath(path, paint);
    }
}
