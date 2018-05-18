package com.example.user.Timer.presentation.fragmentDescription;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.user.Timer.R;
import com.example.user.Timer.dataLayer.store.models.User;
import com.example.user.Timer.databinding.FragmentDescriptionBinding;
import com.example.user.Timer.presentation.App;
import com.example.user.Timer.presentation.Adapters.UserAdapter;
import com.example.user.Timer.presentation.mvp.BaseFragment;
import com.example.user.Timer.presentation.mvp.BaseView;


import java.util.List;

import javax.inject.Inject;


public class DescriptionFragment extends BaseFragment<DescriptionPresenter> implements DescriptionView {

    @Inject
    DescriptionPresenter presenter;
    FragmentDescriptionBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getUserComponent().inject(this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
    public void goWebFragment() {
        mainRouter.showWebFragment();
    }

    @Override
    public void showAllUsers(List<User> userList) {
        UserAdapter userAdapter = new UserAdapter(getContext());
        userAdapter.setUserList(userList);
        binding.listView.setAdapter(userAdapter);
        binding.listView.setOnItemClickListener((adapterView, view, i, l) -> showDialog(userAdapter, i));
    }

    private void showDialog(UserAdapter userAdapter, int i) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setMessage(getResources().getString(R.string.Delete_this_Item));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.NO),
                (dialogInterface, i1) -> dialogInterface.dismiss());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.YES),
                (dialog, which) -> {
                    presenter.deleteUser(((User) userAdapter.getItem(i)).getId());
                    userAdapter.deleteUser(i);
                });
        alertDialog.show();
    }
}


