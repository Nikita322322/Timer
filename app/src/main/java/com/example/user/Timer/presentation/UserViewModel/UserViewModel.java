package com.example.user.Timer.presentation.UserViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;
import android.arch.paging.LivePagedListBuilder;

import com.example.user.Timer.dataLayer.store.models.User;
import com.example.user.Timer.domainLayer.UserDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class UserViewModel extends ViewModel {
    public LiveData<PagedList<User>> userList;
    Executor executor;
    UserViewModel() {
        executor = Executors.newFixedThreadPool(5);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                        .setPrefetchDistance(15)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        userList = (new LivePagedListBuilder(new UserDataSourceFactory(), pagedListConfig))
                .setBackgroundThreadExecutor(executor)
                .build();
    }
}
