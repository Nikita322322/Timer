package com.example.user.Timer.presentation.fragmentWebView;

import com.example.user.Timer.domainLayer.interactors.SaveValueInteractor;
import com.example.user.Timer.presentation.mvp.BasePresenterImpl;
import com.example.user.Timer.presentation.transformerInPresentationLayer.TransformerInPresentationLayer;


import javax.inject.Inject;

public class ClockViewPresenterImpl extends BasePresenterImpl<ClockViewView> implements ClockViewPresenter {

    private final SaveValueInteractor saveValueInteractor;
    private final TransformerInPresentationLayer transformerInPresentationLayer;
    private final String NOTIFY_MESSAGE = "результат был сохранен";

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
}
