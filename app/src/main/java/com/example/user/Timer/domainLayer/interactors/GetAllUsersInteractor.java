package com.example.user.Timer.domainLayer.interactors;

import com.example.user.Timer.dataLayer.repository.Repository;
import com.example.user.Timer.dataLayer.store.models.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


public class GetAllUsersInteractor extends UseCase<Void, List<User>> {
    private final Repository repository;

    @Inject
    public GetAllUsersInteractor(Repository repository) {
        this.repository = repository;
    }

    @Override
    protected Observable<List<User>> buildUseCase(Void arg) {
        return repository.getAllUsers();
    }
}
