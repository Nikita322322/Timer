package com.example.user.Timer.presentation.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.user.Timer.R;
import com.example.user.Timer.presentation.fragmentDescription.DescriptionFragment;
import com.example.user.Timer.presentation.fragmentWebView.ClockViewFragment;
import com.example.user.Timer.presentation.mvp.BaseFragment;

public class MainActivity extends AppCompatActivity implements MainRouter {

    private final static String FRAGMENT_WEB_VIEW = "ClockViewFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Intent intent = getIntent();
        if (savedInstanceState == null) {
            showFragment(new ClockViewFragment(), null);
        }

        if (intent != null) {
            String extra = intent.getStringExtra("profile");
            if (extra != null) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        extra, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }


    public void showFragment(BaseFragment activefragment, Bundle bundle) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragmentByTag = supportFragmentManager.findFragmentByTag(activefragment.getClass().getSimpleName());
        if (fragmentByTag == null || bundle != null) {
            if (activefragment.getClass().getSimpleName().equals(FRAGMENT_WEB_VIEW)) {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                supportFragmentManager.beginTransaction()
                        .add(R.id.fragmentMainLay, activefragment)
                        .commit();
            } else {
                supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentMainLay, activefragment)
                        .commit();
            }
            activefragment.setArguments(bundle);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void showEditFragment(Bundle bundle) {
        showFragment(new DescriptionFragment(), bundle);
    }

    @Override
    public void showWebFragment() {
        showFragment(new ClockViewFragment(), null);
    }
}
