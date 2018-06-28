package com.example.user.Timer.dataLayer.store.local;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.user.Timer.dataLayer.store.models.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

    public Observable<List<User>> fetchNewData() {
        return Observable.create(subscriber -> {
            try {
                Random rand = new Random();
                List<User> userList = new ArrayList<>();
                for (int i = 0; i < 19; i++) {
                    User user = new User();
                    user.setTime(rand.nextInt(380) + 10);
                    userList.add(user);
                }
                subscriber.onNext(userList);
            } catch (Exception e) {
                subscriber.onError(e);
            } finally {
                subscriber.onComplete();
            }
        });
    }

    public Observable<Boolean> saveUser(User user) {
        return Observable.create(subscriber -> {
            try {
                if (mDb != null && mDb.userDao() != null) {
                    long b = mDb.userDao().save(user);
                    if (b >= 0) {
                        subscriber.onNext(true);
                    } else {
                        subscriber.onNext(false);
                    }
                } else {
                    subscriber.onNext(false);
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    public Observable<List<User>> getAllUsers() {
//        return Single.create(subscriber -> {
//            try {
//                if (mDb != null && mDb.userDao() != null) {
//                    List<User> userList = mDb.userDao().getAll();
//                    Collections.sort(userList, (user, t1) -> {
//                        if (user.getDate().equals(t1.getDate())) return 0;
//                        else if (Long.parseLong(user.getDate()) < Long.parseLong(t1.getDate()))
//                            return 1;
//                        else return -1;
//                    });
//                    subscriber.onSuccess(userList);
//                } else {
//                    subscriber.onSuccess(new ArrayList<>());
//                }
//            } catch (Exception e) {
//                subscriber.onError(e);
//            }
//        });
        return Observable.defer(() -> Observable.just(mDb.userDao().getAll()));
    }


    public Observable<Boolean> deleteUser(long id) {
        return Observable.create(subscriber -> {
            try {
                if (mDb != null && mDb.userDao() != null) {
                    long response = mDb.userDao().deleteByUserId(id);//the value is the number of rows affected by this query.
                    if (response <= 0) {
                        subscriber.onNext(false);
                    } else {
                        subscriber.onNext(true);
                    }
                } else {
                    subscriber.onNext(false);
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}