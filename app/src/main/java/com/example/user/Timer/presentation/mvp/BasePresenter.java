package com.example.user.Timer.presentation.mvp;


public interface BasePresenter<T extends BaseView> {

    void attachView(T view);

    void onStart();

    void onStop();

    void detachView();


}
