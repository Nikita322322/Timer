package com.example.user.Timer.presentation.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenterImpl<T extends BaseView> implements BasePresenter<T> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected T view;

    @Override
    public void attachView(T view) {
        this.view = view;
        if (isViewAttached()) {
            onViewAttached();
        }
    }



    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void detachView() {
        view=null;
        compositeDisposable.clear();
    }

    public void addDisposable(Disposable disposable){
        compositeDisposable.add(disposable);
    }

    protected boolean isViewAttached() {
        return view != null;
    }

    public T getView() {
        return view;
    }

    protected abstract void onViewAttached();

}
