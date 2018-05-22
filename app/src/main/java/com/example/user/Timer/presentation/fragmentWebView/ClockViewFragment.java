package com.example.user.Timer.presentation.fragmentWebView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.user.Timer.R;
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
    private Disposable subscription = null;
    int time = 0;

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
                startTimer();
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
        binding.resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });
        return binding.getRoot();
    }

    private void startTimer() {
        if (!binding.limitEditText.getText().toString().trim().equals("")) {
            if (subscription == null) {
                final int[] maxProgress = {Integer.parseInt(binding.limitEditText.getText().toString())};
                time = maxProgress[0];
            }
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
            subscription = Observable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        if (time >= binding.circleSeekBar.getProgress() + 1) {
                            if (binding.circleSeekBar.getProgress() + 1 == 60) {
                                time -= 60;
                                binding.textView.setText("Прошла одна минута, оставшееся время- " + String.valueOf(time) + "секунд");
                                binding.circleSeekBar.setProgress(60);
                                binding.circleSeekBar.invalidate();
                                binding.circleSeekBar.setProgress(0);
                            } else {
                                binding.circleSeekBar.setProgress(binding.circleSeekBar.getProgress() + 1);
                                binding.circleSeekBar.invalidate();
                            }
                        } else {
                            if (binding.circleSeekBar.getProgress() == time) {
                                binding.textView.setText("");
                                Toast toast = Toast.makeText(getContext(), R.string.finish, Toast.LENGTH_LONG);
                                toast.show();
                                subscription.dispose();
                            } else {
                                binding.circleSeekBar.setProgress(1);
                                binding.circleSeekBar.invalidate();
                            }
                        }
                    });
        } else {
            Toast toast = Toast.makeText(getContext(), R.string.limit, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void resetTimer() {
        binding.limitEditText.setText("");
        binding.circleSeekBar.setProgress(0);
        binding.circleSeekBar.invalidate();
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
        subscription = null;
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
    }

    @Override
    public void onStop() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
        super.onStop();
    }
}
