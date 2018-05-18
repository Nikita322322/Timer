package com.example.user.Timer.dataLayer.store.local;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.user.Timer.dataLayer.store.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


/**
 * Created by User on 27.11.2017.
 */

public class LocalStore {
    AppDatabase mDb;
    List<User> users;

    @Inject
    public LocalStore(Context context) {
        mDb = Room.databaseBuilder(context, AppDatabase.class, "user-database").build(); // Get an Instance of AppDatabase class
    }

    public Observable<Boolean> saveToken(HashMap<String, String> listTokens) {
        return Observable.create(subscriber -> {
            try {
                boolean b = true;
                subscriber.onNext(b);
            } catch (Exception e) {
                subscriber.onError(e);
            } finally {
                subscriber.onComplete();
            }
        });
    }

    public String getToken() {
        return "";
    }

    public Observable<Boolean> saveUserId(String userId) {
        return Observable.create(subscriber -> {

            subscriber.onNext(false);

            subscriber.onComplete();

        });
    }

    public String getUserId() {
        return "";
    }

    public Observable<Boolean> isTokenEmpty() {
        return Observable.create(subscriber -> {
            try {
                //if (!sharedPreferences.getString(PREF_TOKEN, "").equals("")) {
                subscriber.onNext(true);
                // } else {
                //   subscriber.onNext(false);
                //}
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
            } finally {
                subscriber.onComplete();
            }
        });
    }

    public Observable<List<User>> getAllUsers() {
        return Observable.create(subscriber -> {
            try {
                if (mDb != null && mDb.userDao() != null) {
                    subscriber.onNext(mDb.userDao().getAll());
                } else {
                    subscriber.onNext(new ArrayList<>());
                }
            } catch (Exception e) {
                subscriber.onError(e);
            } finally {
                subscriber.onComplete();
            }
        });
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
            } finally {
                subscriber.onComplete();
            }
        });
    }
}
