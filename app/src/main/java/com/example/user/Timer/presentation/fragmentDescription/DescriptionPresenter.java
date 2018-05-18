package com.example.user.Timer.presentation.fragmentDescription;


import com.example.user.Timer.presentation.mvp.BasePresenter;

public interface DescriptionPresenter extends BasePresenter<DescriptionView> {
    void deleteUser(Long id);
}
