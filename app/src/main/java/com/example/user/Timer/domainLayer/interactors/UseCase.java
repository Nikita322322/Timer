package com.example.user.Timer.domainLayer.interactors;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public abstract class UseCase<Input, Output> {

    protected abstract Observable<Output> buildUseCase(Input arg);

    public Observable<Output> execute(Input arg) {
        return buildUseCase(arg)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }
}
