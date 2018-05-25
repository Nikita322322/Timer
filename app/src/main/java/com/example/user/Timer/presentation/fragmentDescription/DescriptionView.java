package com.example.user.Timer.presentation.fragmentDescription;

import com.example.user.Timer.presentation.ModelInPresentationLayer.ModelInPresentationLayer;
import com.example.user.Timer.presentation.mvp.BaseView;

import java.util.List;

public interface DescriptionView extends BaseView {
    void showAllUsers(List<ModelInPresentationLayer> userList);
}
