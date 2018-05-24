package com.example.user.Timer.presentation.fragmentWebView;

import com.example.user.Timer.presentation.mvp.BasePresenter;


public interface ClockViewPresenter extends BasePresenter<ClockViewView> {
    void save(int value,long maxValue);
}
