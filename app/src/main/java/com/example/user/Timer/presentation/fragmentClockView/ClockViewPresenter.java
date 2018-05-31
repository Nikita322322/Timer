package com.example.user.Timer.presentation.fragmentClockView;

import com.example.user.Timer.presentation.mvp.BasePresenter;


public interface ClockViewPresenter extends BasePresenter<ClockViewView> {
    void saveResult(int value, long maxValue);

    void startTimer();

    void disposeFromSubscription(boolean isSetSubscriptionNull);

    boolean checkSubscriptionOnNull();
}
