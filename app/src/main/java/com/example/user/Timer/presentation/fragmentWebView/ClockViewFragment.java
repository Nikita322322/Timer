package com.example.user.Timer.presentation.fragmentWebView;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


import com.example.user.Timer.R;
import com.example.user.Timer.databinding.FragmentClockviewBinding;
import com.example.user.Timer.presentation.App;
import com.example.user.Timer.presentation.View.CircleSeekBarView;
import com.example.user.Timer.presentation.activity.MainActivity;
import com.example.user.Timer.presentation.fragmentDescription.DescriptionFragment;
import com.example.user.Timer.presentation.mvp.BaseFragment;
import com.example.user.Timer.presentation.mvp.BaseView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


public class ClockViewFragment extends BaseFragment<ClockViewPresenter> implements ClockViewView {
    private FragmentClockviewBinding binding;
    @Inject
    public ClockViewPresenter presenter;
    private Disposable subscription = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getUserComponent().injects(this);
    }

    public ClockViewFragment() {
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentClockviewBinding.inflate(inflater, container, false);
        binding.circleSeekBar.setSeekBarChangeListener(new CircleSeekBarView.OnSeekChangeListener() {
            @Override
            public void onProgressChange(float progress) {
                setProgress(Math.round(progress));
            }
        });
        binding.changeButton.setOnClickListener(view -> {
            mainRouter.showDescriptionFragment(null);
        });

        binding.saveValueButton.setOnClickListener(view ->
                presenter.saveResult(Math.round(binding.circleSeekBar.getProgress()), (int) binding.circleSeekBar.getMaxProgress()));
        binding.startButton.setOnClickListener(view -> startTimer());
        binding.stopButton.setOnClickListener(view -> {
            binding.circleSeekBar.setTouchView(true);
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        });
        binding.resetButton.setOnClickListener(view -> resetTimer());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.navigation_button_animation);
        binding.changeButton.startAnimation(animation);
        animation.cancel();
    }

    private void startTimer() {
        if (!binding.limitTextView.getText().toString().trim().equals("") || Math.round(binding.circleSeekBar.getProgress()) != 0) {
            if (subscription == null) {
                try {
                    if (binding.limitTextView.getText().toString().trim().equals("")) {
                        binding.circleSeekBar.setMaxProgress(Math.round(binding.circleSeekBar.getProgress()));
                    } else {
                        binding.circleSeekBar.setMaxProgress(Integer.parseInt(binding.limitTextView.getText().toString()) + Math.round(binding.circleSeekBar.getProgress()));
                    }
                    binding.limitTextView.setText(String.valueOf(binding.circleSeekBar.getMaxProgress()));
                } catch (Exception e) {
                    Toast toast = Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                    binding.circleSeekBar.setMaxProgress(Integer.MAX_VALUE);
                }
                binding.circleSeekBar.setProgress(0);
            }
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
            binding.circleSeekBar.setTouchView(false);
            startTime();
        } else {
            Toast toast = Toast.makeText(getContext(), R.string.limit, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void startTime() {
        subscription = Observable.interval(50, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    if ((int) binding.circleSeekBar.getMaxProgress() == (int) binding.circleSeekBar.getProgress()) {
                        binding.textView.setText("");
                        Toast toast = Toast.makeText(getContext(), R.string.finish, Toast.LENGTH_LONG);
                        toast.show();
                        binding.circleSeekBar.setTouchView(true);
                        subscription.dispose();
                    } else {
                        binding.circleSeekBar.setProgress((float) (binding.circleSeekBar.getProgress() + 0.05));
                        binding.circleSeekBar.invalidate();
                    }
                });
    }

    private void setProgress(int progress) {
        int limit = 0;
        if (!binding.limitTextView.getText().toString().equals("")) {
            try {
                limit = Math.round(Float.parseFloat(binding.limitTextView.getText().toString()));
                limit += progress;
            } catch (Exception e) {
                Toast toast = Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG);
                toast.show();
                limit = Integer.MAX_VALUE;
            }
            binding.limitTextView.setText(String.valueOf(limit));
        } else {
            binding.limitTextView.setText(String.valueOf(progress));
        }
    }

    private void resetTimer() {
        binding.circleSeekBar.setTouchView(true);
        binding.limitTextView.setText("");
        binding.circleSeekBar.setMaxProgress(60);
        if (binding.circleSeekBar.getProgress() != 0.0) {
            binding.circleSeekBar.setProgress(0);
        }
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
    public void onStop() {
        resetTimer();
        super.onStop();
    }

    @Override
    public void notifyThatUserSaved(String message) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra(DescriptionFragment.class.getName(), DescriptionFragment.class.getName());
        int requestID = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), requestID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder b = new NotificationCompat.Builder(getActivity());
        b.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(getResources().getString(R.string.Congratulations))
                .setContentText(message)
                .setContentIntent(pendingIntent);

        NotificationManager nm = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (nm != null) {
            nm.notify(1, b.build());
        }
    }
}
