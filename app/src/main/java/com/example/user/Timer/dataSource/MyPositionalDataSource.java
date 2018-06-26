package com.example.user.Timer.dataSource;

import android.arch.paging.ItemKeyedDataSource;
import android.arch.paging.PositionalDataSource;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.user.Timer.dataLayer.repository.Repository;
import com.example.user.Timer.dataLayer.store.models.User;
import com.example.user.Timer.injection.App.user.UserScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;

public class MyPositionalDataSource extends ItemKeyedDataSource<Long, User> {

    public MyPositionalDataSource() {
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<User> callback) {
        Random rand = new Random();
        Log.d("delete", "loadInitial thread is " + String.valueOf(Thread.currentThread().getName()));
        Log.d("delete", String.valueOf(params.requestedLoadSize));
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < params.requestedLoadSize; i++) {
            User user = new User();
            user.setTime(rand.nextInt(300) + 1);
            userList.add(user);
        }
        callback.onResult(userList);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<User> callback) {
        Random rand = new Random();
        Log.d("delete", "loadAfter thread is " + String.valueOf(Thread.currentThread().getName()));
        Log.d("delete", String.valueOf(params.requestedLoadSize));
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < params.requestedLoadSize; i++) {
            User user = new User();
            user.setTime(rand.nextInt(300) + 1);
            userList.add(user);
        }
        callback.onResult(userList);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<User> callback) {

    }

    @NonNull
    @Override
    public Long getKey(@NonNull User item) {
        return null;
    }
}
