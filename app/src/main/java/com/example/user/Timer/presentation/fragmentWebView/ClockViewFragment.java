package com.example.user.Timer.presentation.fragmentWebView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.user.Timer.databinding.FragmentClockviewBinding;
import com.example.user.Timer.presentation.App;
import com.example.user.Timer.presentation.View.CircleSeekBarView;
import com.example.user.Timer.presentation.mvp.BaseFragment;
import com.example.user.Timer.presentation.mvp.BaseView;

import javax.inject.Inject;


public class ClockViewFragment extends BaseFragment<ClockViewPresenter> implements ClockViewView {

    FragmentClockviewBinding binding;
    @Inject
    public ClockViewPresenter presenter;

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
        binding.change.setOnClickListener(view -> {
            mainRouter.showEditFragment(null);
        });
        binding.saveValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.save(binding.circleSeekBar.getProgress());
            }
        });
        binding.circleSeekBar.setMaxProgress(60);
        binding.circleSeekBar.setProgress(0);
        binding.circleSeekBar.invalidate();
        binding.circleSeekBar.setSeekBarChangeListener(new CircleSeekBarView.OnSeekChangeListener() {

            @Override
            public void onProgressChange(CircleSeekBarView view, int newProgress) {
             }
        });


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
}
