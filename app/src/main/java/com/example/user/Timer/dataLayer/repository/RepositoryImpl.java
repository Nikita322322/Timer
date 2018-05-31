package com.example.user.Timer.dataLayer.repository;


import com.example.user.Timer.dataLayer.store.local.LocalStore;

import com.example.user.Timer.domainLayer.TransfprmerInDomainLayer.TransformerInDomainLayer;
import com.example.user.Timer.domainLayer.interactors.model.ModelInDomainLayer;
import com.example.user.Timer.injection.App.user.UserScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by User on 17.05.2018.
 */

@UserScope
public class RepositoryImpl implements Repository {

    private final LocalStore localStore;
    private final TransformerInDomainLayer transformerInDomainLayer;

    @Inject
    RepositoryImpl(LocalStore localStore, TransformerInDomainLayer transformerInDomainLayer) {
        this.localStore = localStore;
        this.transformerInDomainLayer = transformerInDomainLayer;
    }

    @Override
    public Single<Boolean> saveUser(ModelInDomainLayer modelInDomainLayer) {
        return localStore.saveUser(transformerInDomainLayer.transformModelInDomainLayerToUserModel(modelInDomainLayer));
    }

    @Override
    public Single<List<ModelInDomainLayer>> getAllUsers() {
        return localStore.getAllUsers().map(transformerInDomainLayer.userToModelInDomainLayer);
    }

    @Override
    public Single<Boolean> deleteUser(long id) {
        return localStore.deleteUser(id);
    }
}
