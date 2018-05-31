package com.example.user.Timer.injection.App.user;

import com.example.user.Timer.dataLayer.repository.Repository;
import com.example.user.Timer.dataLayer.repository.RepositoryImpl;
import com.example.user.Timer.presentation.fragmentDescription.DescriptionPresenter;
import com.example.user.Timer.presentation.fragmentDescription.DescriptionPresenterImpl;
import com.example.user.Timer.presentation.fragmentClockView.ClockViewPresenter;
import com.example.user.Timer.presentation.fragmentClockView.ClockViewPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {
    @UserScope
    @Provides
    Repository provideRepository(RepositoryImpl repository) {
        return repository;
    }

    @Provides
    ClockViewPresenter provideFragmentClockView(ClockViewPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    DescriptionPresenter provideFragmentDescriptionPresenter(DescriptionPresenterImpl presenter) {
        return presenter;
    }
}
