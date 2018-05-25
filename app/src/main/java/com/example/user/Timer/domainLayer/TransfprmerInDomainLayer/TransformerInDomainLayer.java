package com.example.user.Timer.domainLayer.TransfprmerInDomainLayer;

import com.example.user.Timer.dataLayer.store.models.User;
import com.example.user.Timer.domainLayer.interactors.model.ModelInDomainLayer;
import com.example.user.Timer.presentation.ModelInPresentationLayer.ModelInPresentationLayer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by User on 22.05.2018.
 */

public class TransformerInDomainLayer {
    @Inject
    public TransformerInDomainLayer() {
    }

    public io.reactivex.functions.Function<List<User>, List<ModelInDomainLayer>> userToModelInDomainLayer = new Function<List<User>, List<ModelInDomainLayer>>() {
        @Override
        public List<ModelInDomainLayer> apply(List<User> modelInDomainLayers) throws Exception {
            List<ModelInDomainLayer> userModelList = new ArrayList<ModelInDomainLayer>();
            for (User model : modelInDomainLayers) {
                userModelList.add(
                        new ModelInDomainLayer(
                                model.getId(),
                                model.getTime(),
                                model.getDate(),
                                model.getGoal()
                        )
                );
            }
            return userModelList;
        }
    };

    public ModelInDomainLayer transformPresentationModelToDomain(ModelInPresentationLayer modelInPresentationLayer) {
        return new ModelInDomainLayer(modelInPresentationLayer.getTime(), modelInPresentationLayer.getDate(), modelInPresentationLayer.getGoal());
    }
}
