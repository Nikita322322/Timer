package com.example.user.Timer.presentation.fragmentDescription;


import com.example.user.Timer.domainLayer.interactors.DeleteUserInteractor;
import com.example.user.Timer.domainLayer.interactors.GetAllUsersInteractor;
import com.example.user.Timer.presentation.mvp.BasePresenterImpl;


import javax.inject.Inject;

public class DescriptionPresenterImpl extends BasePresenterImpl<DescriptionView> implements DescriptionPresenter {
    private final GetAllUsersInteractor getAllUsersInteractor;
    private final DeleteUserInteractor deleteUserInteractor;

    @Inject
    public DescriptionPresenterImpl(GetAllUsersInteractor getAllUsersInteractor, DeleteUserInteractor deleteUserInteractor) {
        this.getAllUsersInteractor = getAllUsersInteractor;
        this.deleteUserInteractor = deleteUserInteractor;
    }

    @Override
    protected void onViewAttached() {
        getAllUsersInteractor.execute(null).subscribe(users -> {
            if (users != null) {
                view.showAllUsers(users);
            }
        }, Throwable::printStackTrace);
    }

    @Override
    public void deleteUser(Long id) {
        deleteUserInteractor.execute(id).subscribe(aBoolean -> {
                }, Throwable::printStackTrace
        );
    }
}
