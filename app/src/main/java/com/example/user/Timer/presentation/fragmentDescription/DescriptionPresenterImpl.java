package com.example.user.Timer.presentation.fragmentDescription;

import com.example.user.Timer.domainLayer.interactors.DeleteUserInteractor;
import com.example.user.Timer.domainLayer.interactors.FetchNewDataInteractor;
import com.example.user.Timer.domainLayer.interactors.GetAllUsersInteractor;
import com.example.user.Timer.presentation.mvp.BasePresenterImpl;


import javax.inject.Inject;

public class DescriptionPresenterImpl extends BasePresenterImpl<DescriptionView> implements DescriptionPresenter {
    private final GetAllUsersInteractor getAllUsersInteractor;
    private final DeleteUserInteractor deleteUserInteractor;
    private final FetchNewDataInteractor fetchNewDataInteractor;

    @Inject
    public DescriptionPresenterImpl(GetAllUsersInteractor getAllUsersInteractor, DeleteUserInteractor deleteUserInteractor, FetchNewDataInteractor fetchNewDataInteractor) {
        this.getAllUsersInteractor = getAllUsersInteractor;
        this.deleteUserInteractor = deleteUserInteractor;
        this.fetchNewDataInteractor = fetchNewDataInteractor;
    }


    @Override
    protected void onViewAttached() {
        fetchNewData();
    }

    @Override
    public void deleteUser(Long id) {
        addDisposable(deleteUserInteractor.execute(id).subscribe(aBoolean -> {
        }, Throwable::printStackTrace));
    }

    @Override
    public void fetchNewData() {
        addDisposable(fetchNewDataInteractor.execute(null).subscribe(modelInPresentationLayers -> {
            if (isViewAttached()) {
                view.showAllUsers(modelInPresentationLayers);
            }
        }));
    }

}
