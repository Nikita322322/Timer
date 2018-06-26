package com.example.user.Timer.domainLayer.interactors;

import com.example.user.Timer.dataLayer.repository.Repository;
import com.example.user.Timer.domainLayer.TransfprmerInDomainLayer.TransformerInDomainLayer;
import com.example.user.Timer.presentation.ModelInPresentationLayer.ModelInPresentationLayer;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class SaveValueInteractor extends UseCase<ModelInPresentationLayer, Boolean> {
    private Repository repository;
    private TransformerInDomainLayer transformerInDomainLayer;

    @Inject
    public SaveValueInteractor(Repository repository, TransformerInDomainLayer transformerInDomainLayer) {
        this.repository = repository;
        this.transformerInDomainLayer = transformerInDomainLayer;
    }

    @Override
    protected Observable<Boolean> buildUseCase(ModelInPresentationLayer arg) {
        return repository.saveUser(transformerInDomainLayer.transformPresentationModelToDomain(arg));
    }
}

