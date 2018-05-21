package com.example.user.Timer.domainLayer.interactors;

import com.example.user.Timer.dataLayer.repository.Repository;
import com.example.user.Timer.domainLayer.interactors.model.ModelInDomainLayer;
import com.example.user.Timer.presentation.ModelInPresentationLayer.ModelInPresentationLayer;

import javax.inject.Inject;

import io.reactivex.Observable;


public class SaveValueInteractor extends UseCase<ModelInPresentationLayer, Boolean> {
    private final Repository repository;


    @Inject
    public SaveValueInteractor(Repository repository) {
        this.repository = repository;
    }

    @Override
    protected Observable<Boolean> buildUseCase(ModelInPresentationLayer arg) {
        ModelInDomainLayer modelInDomainLayer = new ModelInDomainLayer(arg.getTime(), arg.getDate());

        return repository.saveUser(modelInDomainLayer);
    }
}

