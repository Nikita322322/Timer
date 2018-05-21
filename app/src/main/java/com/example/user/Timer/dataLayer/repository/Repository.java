package com.example.user.Timer.dataLayer.repository;

import com.example.user.Timer.dataLayer.store.models.User;
import com.example.user.Timer.domainLayer.interactors.model.ModelInDomainLayer;


import java.util.List;

import io.reactivex.Observable;

/**
 * Created by User on 17.05.2018.
 */

public interface Repository {

    Observable<Boolean> saveUser(ModelInDomainLayer user);

    Observable<List<User>> getAllUsers();

    Observable<Boolean> deleteUser(long id);
}
