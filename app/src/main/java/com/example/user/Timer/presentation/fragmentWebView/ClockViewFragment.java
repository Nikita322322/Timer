package com.example.user.Timer.presentation.fragmentWebView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.user.Timer.databinding.FragmentClockviewBinding;
import com.example.user.Timer.presentation.App;
import com.example.user.Timer.presentation.mvp.BaseFragment;
import com.example.user.Timer.presentation.mvp.BaseView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class ClockViewFragment extends BaseFragment<ClockViewPresenter> implements ClockViewView {

    FragmentClockviewBinding binding;
    @Inject
    public ClockViewPresenter presenter;
    private Disposable subscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getUserComponent().injects(this);
    }


    public ClockViewFragment() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentClockviewBinding.inflate(inflater, container, false);

        binding.changeButton.setOnClickListener(view -> {
            mainRouter.showEditFragment(null);
        });
        binding.saveValueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.save(binding.circleSeekBar.getProgress());
            }
        });
        binding.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.limitEditText.getText().toString().equals("")) {
                    if (subscription != null && !subscription.isDisposed()) {
                        subscription.dispose();
                    }
                    int maxProgress = Integer.parseInt(binding.limitEditText.getText().toString());
                    binding.circleSeekBar.setMaxProgress(maxProgress);
                    Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread());
                    subscription = observable
                            .subscribe(aLong -> {
                                if (maxProgress >= aLong) {
                                    //  binding.value.setText(String.valueOf(aLong));
                                    binding.circleSeekBar.setProgress(aLong);
                                    binding.circleSeekBar.invalidate();
                                } else {
                                    Toast toast = Toast.makeText(getContext(), "Вы достигли цели!!!", Toast.LENGTH_LONG);
                                    toast.show();
                                    subscription.dispose();
                                }
                            });
                } else {
                    Toast toast = Toast.makeText(getContext(), "Введите лимит", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        binding.stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subscription != null && !subscription.isDisposed()) {
                    subscription.dispose();
                }
            }
        });

//        binding.circleSeekBar.setMaxProgress(60);
//        binding.circleSeekBar.setProgress(0);
//        binding.circleSeekBar.invalidate();
//        binding.circleSeekBar.setSeekBarChangeListener(new CircleSeekBarView.OnSeekChangeListener() {
//
//            @Override
//            public void onProgressChange(CircleSeekBarView view, int newProgress) {
//             }
//        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected ClockViewPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    public void show(boolean b) {
        binding.value.setText(String.valueOf(b));
    }

    @Override
    public void onStop() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
        super.onStop();
    }
}
