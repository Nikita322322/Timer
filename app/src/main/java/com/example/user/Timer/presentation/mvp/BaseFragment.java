package com.example.user.Timer.presentation.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.user.Timer.presentation.activity.MainRouter;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    protected T presenter;
    protected MainRouter mainRouter;
    private OnFragmentActivatedListener onFragmentActivatedListener;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = getPresenter();
        if (presenter != null) {
            presenter.attachView(getBaseView());
        }
        mainRouter = (MainRouter) getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (onFragmentActivatedListener != null) {
            onFragmentActivatedListener.onFragmentActivated(this);
        }
        presenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    public boolean isShowButtonOnMainActivityToolbar() {
        return false;
    }

    protected abstract T getPresenter();

    protected abstract BaseView getBaseView();

    public void setOnFragmentActivatedListener(OnFragmentActivatedListener onFragmentActivatedListener) {
        this.onFragmentActivatedListener = onFragmentActivatedListener;
    }

    public interface OnFragmentActivatedListener {
        void onFragmentActivated(BaseFragment tBaseFragment);
    }
}