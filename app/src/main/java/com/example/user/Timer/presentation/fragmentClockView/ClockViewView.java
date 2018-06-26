package com.example.user.Timer.presentation.fragmentClockView;

import com.example.user.Timer.presentation.mvp.BaseView;

public interface ClockViewView extends BaseView {
    void notifyThatUserSaved();

    void invalidateCircleSeekBarView(float time);
}
