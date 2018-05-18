package com.example.user.Timer.presentation.mvp;


public interface BasePresenter<T extends BaseView> {

    void attachView(T view);// метод для передачи View презентеру. Т.е. View вызовет его и передаст туда себя.

    void onStart();

    void onStop();

    void detachView();


}
