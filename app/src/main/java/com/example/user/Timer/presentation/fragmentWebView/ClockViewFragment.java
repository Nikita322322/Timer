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
        binding.changeButton.setOnClickListener(view -> {
            mainRouter.showDescriptionFragment(null);
        });

        binding.saveValueButton.setOnClickListener(view ->
                presenter.saveResult(Math.round(binding.circleSeekBar.getProgress()), (int) binding.circleSeekBar.getMaxProgress()));
        binding.startButton.setOnClickListener(view -> startTimer());
        binding.stopButton.setOnClickListener(view -> {
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
        mainRouter.showNavigationButton(false);
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
        if (!binding.limitEditText.getText().toString().trim().equals("")) {
            if (subscription == null) {
                binding.circleSeekBar.setMaxProgress(Integer.parseInt(binding.limitEditText.getText().toString()));
            }
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
            subscription = Observable.interval(500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        if ((int)binding.circleSeekBar.getMaxProgress() == (int)binding.circleSeekBar.getProgress()) {
                            binding.textView.setText("");
                            Toast toast = Toast.makeText(getContext(), R.string.finish, Toast.LENGTH_LONG);
                            toast.show();
                            subscription.dispose();
                        } else {
                            binding.circleSeekBar.setProgress((float) (binding.circleSeekBar.getProgress() + 0.5));
                            binding.circleSeekBar.invalidate();
                        }
                    });
        } else {
            Toast toast = Toast.makeText(getContext(), R.string.limit, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void resetTimer() {
        binding.limitEditText.setText("");
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
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
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
