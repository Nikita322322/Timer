package com.example.user.Timer.domainLayer.interactors;

import com.example.user.Timer.dataLayer.repository.Repository;
import com.example.user.Timer.presentation.ModelInPresentationLayer.ModelInPresentationLayer;
import com.example.user.Timer.presentation.transformerInPresentationLayer.TransformerInPresentationLayer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class FetchNewDataInteractor extends UseCase<Void, List<ModelInPresentationLayer>> {
    private final Repository repository;
    private final TransformerInPresentationLayer transformerInPresentationLayer;

    @Inject
    public FetchNewDataInteractor(Repository repository, TransformerInPresentationLayer transformerInPresentationLayer) {
        this.repository = repository;
        this.transformerInPresentationLayer = transformerInPresentationLayer;
    }

    @Override
    protected Observable<List<ModelInPresentationLayer>> buildUseCase(Void arg) {
        return repository.fetchNewDate().map(transformerInPresentationLayer.domainModelToPresentationModel);
    }
}
