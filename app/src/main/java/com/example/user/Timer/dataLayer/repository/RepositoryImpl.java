package com.example.user.Timer.dataLayer.repository;


import com.example.user.Timer.dataLayer.store.local.LocalStore;

import com.example.user.Timer.dataLayer.store.models.User;
import com.example.user.Timer.dataSource.MyPositionalDataSource;
import com.example.user.Timer.domainLayer.TransfprmerInDomainLayer.TransformerInDomainLayer;
import com.example.user.Timer.domainLayer.interactors.model.ModelInDomainLayer;
import com.example.user.Timer.injection.App.user.UserScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by User on 17.05.2018.
 */

@UserScope
public class RepositoryImpl implements Repository {

    private final LocalStore localStore;
    private final TransformerInDomainLayer transformerInDomainLayer;
    private BehaviorSubject<List<User>> onLoadSubject;

    @Inject
    RepositoryImpl(LocalStore localStore, TransformerInDomainLayer transformerInDomainLayer) {
        this.localStore = localStore;
        this.transformerInDomainLayer = transformerInDomainLayer;
        onLoadSubject = BehaviorSubject.create();
    }

    @Override
    public Observable<Boolean> saveUser(ModelInDomainLayer modelInDomainLayer) {
        return localStore.saveUser(transformerInDomainLayer.transformModelInDomainLayerToUserModel(modelInDomainLayer));
    }

    @Override
    public Observable<List<ModelInDomainLayer>> getAllUsers() {
        return localStore.getAllUsers().map(transformerInDomainLayer.userToModelInDomainLayer);
    }

    @Override
    public Observable<Boolean> deleteUser(long id) {
        return localStore.deleteUser(id);
    }


    @Override
    public Observable<List<ModelInDomainLayer>> fetchNewDate() {
        return localStore.fetchNewData().map(transformerInDomainLayer.userToModelInDomainLayer);
    }
}
