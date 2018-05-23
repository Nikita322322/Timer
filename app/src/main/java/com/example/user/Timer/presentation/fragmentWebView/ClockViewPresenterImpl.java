package com.example.user.Timer.presentation.fragmentWebView;

import com.example.user.Timer.domainLayer.interactors.SaveValueInteractor;
import com.example.user.Timer.presentation.ModelInPresentationLayer.ModelInPresentationLayer;
import com.example.user.Timer.presentation.mvp.BasePresenterImpl;


import java.util.Calendar;

import javax.inject.Inject;

public class ClockViewPresenterImpl extends BasePresenterImpl<ClockViewView> implements ClockViewPresenter {

    private final SaveValueInteractor saveValueInteractor;

    @Inject
    public ClockViewPresenterImpl(SaveValueInteractor saveValueInteractor) {
        this.saveValueInteractor = saveValueInteractor;
    }

    @Override
    protected void onViewAttached() {
        if (isViewAttached()) {

        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void save(int value) {
        ModelInPresentationLayer user = new ModelInPresentationLayer();
        user.setTime(value);
        Calendar cal = Calendar.getInstance();
        user.setDate(String.valueOf(cal.getTimeInMillis()));

        addDisposable(saveValueInteractor.execute(user).subscribe(aBoolean -> {
        }, Throwable::printStackTrace));
    }
}
