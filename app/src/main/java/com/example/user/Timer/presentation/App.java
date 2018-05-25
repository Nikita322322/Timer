package com.example.user.Timer.presentation;

import android.app.Application;

import com.example.user.Timer.injection.App.DaggerAppComponent;
import com.example.user.Timer.injection.App.user.UserComponent;
import com.example.user.Timer.injection.App.user.UserModule;

public class App extends Application {

    private static App instance;
    private static com.example.user.Timer.injection.App.AppComponent component;
    private static UserComponent userComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static com.example.user.Timer.injection.App.AppComponent getAppComponent() {
        if (component == null) {
            component = DaggerAppComponent.builder().appModule(new com.example.user.Timer.injection.App.AppModule(instance)).build();
        }
        return component;
    }

    public static UserComponent getUserComponent() {
        if (userComponent == null) {
            userComponent = getAppComponent().plusUserComponent(new UserModule());
        }
        return userComponent;
    }

    public static App getContext() {
        return instance;
    }
}
