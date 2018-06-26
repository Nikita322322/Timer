package com.example.user.Timer.presentation.fragmentDescription;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.example.user.Timer.R;
import com.example.user.Timer.dataLayer.store.models.User;
import com.example.user.Timer.dataSource.MyPositionalDataSource;
import com.example.user.Timer.databinding.FragmentDescriptionBinding;
import com.example.user.Timer.presentation.App;
import com.example.user.Timer.presentation.Adapters.UserAdapter;
import com.example.user.Timer.presentation.ModelInPresentationLayer.ModelInPresentationLayer;
import com.example.user.Timer.presentation.UserViewModel.UserViewModel;
import com.example.user.Timer.presentation.View.CustomHorizontalRecyclerView;
import com.example.user.Timer.presentation.View.CustomScrollViewForGraphics;
import com.example.user.Timer.presentation.View.SmoothScrollLinearLayoutManager;
import com.example.user.Timer.presentation.mvp.BaseFragment;
import com.example.user.Timer.presentation.mvp.BaseView;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class DescriptionFragment extends BaseFragment<DescriptionPresenter> implements DescriptionView, CustomScrollViewForGraphics.HorizontalListener {

    @Inject
    DescriptionPresenter presenter;
    private FragmentDescriptionBinding binding;
    private GestureDetector mDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getUserComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false);
        // binding.loadingView.setVisibility(View.VISIBLE);
        //userUserAdapter = new UserAdapter(getContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.customScrollViewForGraphics.setListener(this);
//        SmoothScrollLinearLayoutManager smoothScrollLinearLayoutManager = new SmoothScrollLinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        binding.recyclerView.setLayoutManager(smoothScrollLinearLayoutManager);
//
//        binding.recyclerView.setAdapter(userUserAdapter);
//        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dx > 0) {
//                    int visibleItemCount = smoothScrollLinearLayoutManager.getChildCount();
//                    int totalItemCount = smoothScrollLinearLayoutManager.getItemCount();
//                    int pastVisiblesItems = smoothScrollLinearLayoutManager.findFirstVisibleItemPosition();
//
//                    if ((visibleItemCount + pastVisiblesItems) + 1 >= totalItemCount) {
//                        presenter.fetchNewData();
//                    }
//                }
//            }
//        });
//        binding.recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
//                binding.recyclerView.setPadding(Math.round(view.getWidth() / 2) - 40, 0, 0, 0);
//            }
//        });
        mDetector = new GestureDetector(getContext(), new MyGestureListener());
        binding.customScrollViewForGraphics.setOnTouchListener(touchListener);
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // pass the events to the gesture detector
            // a return value of true means the detector is handling it
            // a return value of false means the detector didn't
            // recognize the event
            return mDetector.onTouchEvent(event);

        }
    };

    @Override
    public void showAllUsers(List<ModelInPresentationLayer> userList) {
         binding.customScrollViewForGraphics.setModel(userList,400);
    }

    private void showDialog(UserAdapter userAdapter, int i) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle(getResources().getString(R.string.Delete_this_Item));
        alertDialog.setIcon(R.drawable.f5da366372b5ca66c0de5fd61b6d9bde);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.NO),
                (dialogInterface, i1) -> {
                    dialogInterface.dismiss();
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.YES),
                (dialog, which) -> {
                    // presenter.deleteUser(((ModelInPresentationLayer) userAdapter.getItem(i)).getId());
                    //    userAdapter.deleteUser(i);
                    //  binding.customGraphicsView.updateView(userAdapter.getUserList());
                });
        alertDialog.show();
    }

    @Override
    public boolean isShowButtonOnMainActivityToolbar() {
        return true;
    }

    @Override
    protected DescriptionPresenter getPresenter() {
        return presenter;
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    public void onFetchData() {
        presenter.fetchNewData();
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {

            // don't return false here or else none of the other
            // gestures will work
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            binding.customScrollViewForGraphics.scrollTo(distanceX);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            return true;
        }
    }
}


