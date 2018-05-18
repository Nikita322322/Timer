package com.example.user.Timer.presentation.fragmentDescription;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import com.example.user.Timer.dataLayer.store.models.User;
import com.example.user.Timer.databinding.FragmentDescriptionBinding;
import com.example.user.Timer.presentation.App;
import com.example.user.Timer.presentation.UserAdapter;
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
        binding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.deleteUser(Long.parseLong(binding.textId.getText().toString()));
            }
        });
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
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showDialog(userAdapter, i);
            }
        });
    }

    private void showDialog(UserAdapter userAdapter, int i) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Dialog");
        alertDialog.setMessage("Delete this Item");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteUser(((User) userAdapter.getItem(i)).getId());
                        userAdapter.deleteUser(i);
                    }
                });
        alertDialog.show();
    }
}


