package com.example.user.Timer.dataLayer.repository;

import com.example.user.Timer.dataLayer.store.models.User;


import java.util.List;

import io.reactivex.Observable;

/**
 * Created by User on 17.05.2018.
 */

public interface Repository {

    Observable<Boolean> saveUser(User user);

    Observable<List<User>> getAllUsers();

    Observable<Boolean> deleteUser(long id);
}
