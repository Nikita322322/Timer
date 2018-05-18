package com.example.user.Timer.injection.App;

import com.example.user.Timer.injection.App.user.UserComponent;
import com.example.user.Timer.injection.App.user.UserModule;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class})
public interface AppComponent {
    UserComponent plusUserComponent(UserModule userModule);
}
