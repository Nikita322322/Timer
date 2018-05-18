package com.example.user.Timer.injection.App.user;

import com.example.user.Timer.presentation.fragmentDescription.DescriptionFragment;
import com.example.user.Timer.presentation.fragmentWebView.ClockViewFragment;

import dagger.Subcomponent;

@UserScope
@Subcomponent(modules = {UserModule.class})
public interface UserComponent {

    void injects(ClockViewFragment fragment);

    void inject(DescriptionFragment descriptionFragmentFragment);

}