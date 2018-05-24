package com.example.user.Timer.presentation.activity;

import android.os.Bundle;


public interface MainRouter {
    void showDescriptionFragment(Bundle bundle);
    void showNavigationButton(Boolean isShow);
    void showWebFragment();
}
