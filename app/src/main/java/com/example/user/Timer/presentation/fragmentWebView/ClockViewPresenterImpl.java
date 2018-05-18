package com.example.user.Timer.presentation.fragmentWebView;

import com.example.user.Timer.dataLayer.store.models.User;
import com.example.user.Timer.domainLayer.interactors.SaveValueInteractor;
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
    public void save(String value) {
        User user = new User();
        user.setTime(Integer.parseInt(value));
        Calendar cal = Calendar.getInstance();
        user.setDate(String.valueOf(cal.getTimeInMillis()));

        saveValueInteractor.execute(user).subscribe(aBoolean -> {

        }, Throwable::printStackTrace);
    }
}
