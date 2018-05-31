package com.example.user.Timer.domainLayer.interactors;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public abstract class UseCase<Input, Output> {

    protected abstract Single<Output> buildUseCase(Input arg);

    public Single<Output> execute(Input arg) {
        return buildUseCase(arg)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }
}
