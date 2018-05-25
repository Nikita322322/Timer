package com.example.user.Timer.presentation.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.user.Timer.R;
import com.example.user.Timer.presentation.fragmentDescription.DescriptionFragment;
import com.example.user.Timer.presentation.fragmentWebView.ClockViewFragment;
import com.example.user.Timer.presentation.mvp.BaseFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MainRouter {

    private final static String CLOCK_VIEW_FRAGMENT = "ClockViewFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String isShowDescriptionFragment = intent.getStringExtra(DescriptionFragment.class.getName());
        if (isShowDescriptionFragment == null) {
            showFragment(new ClockViewFragment(), null);
        } else {
            showFragment(new DescriptionFragment(), new Bundle());
        }
    }

    public void showFragment(BaseFragment activefragment, Bundle bundle) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragmentByTag = supportFragmentManager.findFragmentByTag(activefragment.getClass().getSimpleName());
        if (fragmentByTag == null || bundle != null) {
            if (activefragment.getClass().getSimpleName().equals(CLOCK_VIEW_FRAGMENT)) {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                supportFragmentManager.beginTransaction()
                        .add(R.id.fragmentMainLay, activefragment)
                        .commit();
            } else {
                if (bundle != null) {
                    supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left)
                            .replace(R.id.fragmentMainLay, activefragment)
                            .commit();
                } else {
                    supportFragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left)
                            .replace(R.id.fragmentMainLay, activefragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
            activefragment.setArguments(bundle);
        }
    }

    @Override
    public void showNavigationButton(Boolean isShow) {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(isShow);
        getSupportActionBar().setDisplayShowHomeEnabled(isShow);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void showDescriptionFragment(Bundle bundle) {
        showFragment(new DescriptionFragment(), bundle);
    }
}
