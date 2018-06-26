package com.example.user.Timer.domainLayer;

import android.arch.paging.DataSource;

import com.example.user.Timer.dataLayer.repository.Repository;
import com.example.user.Timer.dataSource.MyPositionalDataSource;

import javax.inject.Inject;

public class UserDataSourceFactory implements DataSource.Factory {

    @Inject
    public UserDataSourceFactory() {
    }

    @Override
    public DataSource create() {
        return new MyPositionalDataSource();
    }
}
