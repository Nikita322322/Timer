package com.example.user.Timer.dataLayer.repository;

import android.content.Context;

import com.example.user.Timer.dataLayer.store.models.User;
import com.example.user.Timer.dataLayer.store.local.LocalStore;

import com.example.user.Timer.domainLayer.interactors.model.ModelInDomainLayer;
import com.example.user.Timer.injection.App.user.UserScope;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by User on 17.05.2018.
 */

@UserScope
public class RepositoryImpl implements Repository {

    private final LocalStore localStore;
    private final Context context;


    @Inject
    public RepositoryImpl(LocalStore localStore, Context context) {
        this.localStore = localStore;
        this.context = context;
    }


    @Override
    public Observable<Boolean> saveUser(ModelInDomainLayer modelInDomainLayer) {
        User user = new User();
        user.setDate(modelInDomainLayer.getDate());
        user.setTime(modelInDomainLayer.getTime());
        return localStore.saveUser(user);
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return localStore.getAllUsers();
    }

    @Override
    public Observable<Boolean> deleteUser(long id) {
        return localStore.deleteUser(id);
    }
}