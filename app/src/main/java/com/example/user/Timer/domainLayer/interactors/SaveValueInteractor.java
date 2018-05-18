package com.example.user.Timer.domainLayer.interactors;

import com.example.user.Timer.dataLayer.repository.Repository;
import com.example.user.Timer.dataLayer.store.models.User;

import javax.inject.Inject;

import io.reactivex.Observable;



public class SaveValueInteractor extends UseCase<User, Boolean> {
    private final Repository repository;

    @Inject
    public SaveValueInteractor(Repository repository) {

        this.repository = repository;
    }

    @Override
    protected Observable<Boolean> buildUseCase(User arg) {
        return repository.saveUser(arg);
    }
}

