package com.example.user.Timer.dataLayer.repository;

import com.example.user.Timer.domainLayer.interactors.model.ModelInDomainLayer;


import java.util.List;

import io.reactivex.Single;

/**
 * Created by User on 17.05.2018.
 */

public interface Repository {

    Single<Boolean> saveUser(ModelInDomainLayer user);

    Single<List<ModelInDomainLayer>> getAllUsers();

    Single<Boolean> deleteUser(long id);
}
