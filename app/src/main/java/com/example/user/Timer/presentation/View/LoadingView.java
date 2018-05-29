package com.example.user.Timer.presentation.View;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.user.Timer.R;

/**
 * Created by User on 29.05.2018.
 */

public class LoadingView extends FrameLayout {

    public LoadingView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.loading_view, this);
    }
}
