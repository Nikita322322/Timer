package com.example.user.Timer.domainLayer.interactors;

import com.example.user.Timer.dataLayer.repository.Repository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by User on 18.05.2018.
 */

public class DeleteUserInteractor extends UseCase<Long, Boolean> {
    private final Repository repository;

    @Inject
    public DeleteUserInteractor(Repository repository) {
        this.repository = repository;
    }

    @Override
    protected Observable<Boolean> buildUseCase(Long arg) {
        return repository.deleteUser(arg);
    }
}
