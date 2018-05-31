package com.example.user.Timer.dataLayer.store.local;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.user.Timer.dataLayer.store.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;


/**
 * Created by User on 27.11.2017.
 */

public class LocalStore {
    private AppDatabase mDb;

    @Inject
    LocalStore(Context context) {
        mDb = Room.databaseBuilder(context, AppDatabase.class, User.TABLE_NAME).build(); // Get an Instance of AppDatabase class
    }

    public Single<Boolean> saveUser(User user) {
        return Single.create(subscriber -> {
            try {
                if (mDb != null && mDb.userDao() != null) {
                    long b = mDb.userDao().save(user);
                    if (b >= 0) {
                        subscriber.onSuccess(true);
                    } else {
                        subscriber.onSuccess(false);
                    }
                } else {
                    subscriber.onSuccess(false);
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    public Single<List<User>> getAllUsers() {
        return Single.create(subscriber -> {
            try {
                if (mDb != null && mDb.userDao() != null) {
                    List<User> userList = mDb.userDao().getAll();
                    Collections.sort(userList, (user, t1) -> {
                        if (user.getDate().equals(t1.getDate())) return 0;
                        else if (Long.parseLong(user.getDate()) < Long.parseLong(t1.getDate()))
                            return 1;
                        else return -1;
                    });
                    subscriber.onSuccess(userList);
                } else {
                    subscriber.onSuccess(new ArrayList<>());
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    public Single<Boolean> deleteUser(long id) {
        return Single.create(subscriber -> {
            try {
                if (mDb != null && mDb.userDao() != null) {
                    long response = mDb.userDao().deleteByUserId(id);//the value is the number of rows affected by this query.
                    if (response <= 0) {
                        subscriber.onSuccess(false);
                    } else {
                        subscriber.onSuccess(true);
                    }
                } else {
                    subscriber.onSuccess(false);
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}