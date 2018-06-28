package com.example.user.Timer.presentation.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.user.Timer.R;
import com.example.user.Timer.presentation.fragmentDescription.DescriptionFragment;
import com.example.user.Timer.presentation.fragmentClockView.ClockViewFragment;
import com.example.user.Timer.presentation.mvp.BaseFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MainRouter, BaseFragment.OnFragmentActivatedListener {

    private final static String CLOCK_VIEW_FRAGMENT = "ClockViewFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setNavigationOnClickListener(v -> onBackPressed());

        Intent intent = getIntent();
        String isShowDescriptionFragment = intent.getStringExtra(DescriptionFragment.class.getName());
        if (isShowDescriptionFragment != null && isShowDescriptionFragment.equals(DescriptionFragment.class.getName())) {
            showFragment(new DescriptionFragment(), true);
        } else {
            showFragment(new ClockViewFragment(), false);
        }
    }

    public void showFragment(BaseFragment activefragment, boolean addToBackStack) {
//        FragmentManager supportFragmentManager = getSupportFragmentManager();
//        Fragment fragmentByTag = supportFragmentManager.findFragmentByTag(activefragment.getClass().getSimpleName());
//        activefragment.setOnFragmentActivatedListener(this);
//        if (fragmentByTag == null) {
//            if (activefragment.getClass().getSimpleName().equals(CLOCK_VIEW_FRAGMENT)) {
//                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                supportFragmentManager.beginTransaction()
//                        .add(R.id.fragmentMainLay, activefragment)
//                        .commit();
//            } else {
//                if (addToBackStack) {
//                    supportFragmentManager.beginTransaction()
//                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left)
//                            .replace(R.id.fragmentMainLay, activefragment)
//                            .commit();
//                } else {
//                    supportFragmentManager.beginTransaction()
//                            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_left)
//                            .replace(R.id.fragmentMainLay, activefragment)
//                            .addToBackStack(null)
//                            .commit();
//                }
//            }
//        }
    }

    public void showNavigationButton(Boolean isShow) {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(isShow);
        getSupportActionBar().setDisplayShowHomeEnabled(isShow);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void showDescriptionFragment() {
        showFragment(new DescriptionFragment(), false);
    }

    @Override
    public void onFragmentActivated(BaseFragment tBaseFragment) {
        showNavigationButton(tBaseFragment.isShowButtonOnMainActivityToolbar());
    }
}
