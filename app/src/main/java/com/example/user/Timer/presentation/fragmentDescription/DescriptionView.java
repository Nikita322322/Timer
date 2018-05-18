package com.example.user.Timer.presentation.fragmentDescription;

import com.example.user.Timer.dataLayer.store.models.User;
import com.example.user.Timer.presentation.mvp.BaseView;

import java.util.List;


public interface DescriptionView extends BaseView {
    void goWebFragment();
    void showAllUsers(List<User> userList);
}
