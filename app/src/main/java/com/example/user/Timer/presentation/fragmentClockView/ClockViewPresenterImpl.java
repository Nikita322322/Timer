package com.example.user.Timer.presentation.fragmentClockView;

import com.example.user.Timer.domainLayer.interactors.SaveValueInteractor;
import com.example.user.Timer.presentation.mvp.BasePresenterImpl;
import com.example.user.Timer.presentation.transformerInPresentationLayer.TransformerInPresentationLayer;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ClockViewPresenterImpl extends BasePresenterImpl<ClockViewView> implements ClockViewPresenter {

    private final SaveValueInteractor saveValueInteractor;
    private final TransformerInPresentationLayer transformerInPresentationLayer;
    private final String NOTIFY_MESSAGE = "результат был сохранен";
    private Disposable subscription = null;

    @Inject
    public ClockViewPresenterImpl(SaveValueInteractor saveValueInteractor, TransformerInPresentationLayer transformerInPresentationLayer) {
        this.saveValueInteractor = saveValueInteractor;
        this.transformerInPresentationLayer = transformerInPresentationLayer;
    }

    @Override
    protected void onViewAttached() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void saveResult(int value, long maxValue) {
        addDisposable(saveValueInteractor.execute(transformerInPresentationLayer.getModelInPresentationLayer(value, maxValue))
                .subscribe(aBoolean -> {
                    if (aBoolean && isViewAttached()) {
                        view.notifyThatUserSaved(NOTIFY_MESSAGE);
                    }
                }, Throwable::printStackTrace));
    }

    @Override
    public void startTimer() {
        subscription = Observable.interval(50, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if (isViewAttached()) {
                        view.invalidateCircleSeekBarView((float) 0.05);
                    }
                });
    }

    @Override
    public void disposeFromSubscription(boolean isSetSubscriptionNull) {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
        if (isSetSubscriptionNull) {
            subscription = null;
        }
    }

    @Override
    public boolean checkSubscriptionOnNull() {
        return subscription == null;
    }

}
