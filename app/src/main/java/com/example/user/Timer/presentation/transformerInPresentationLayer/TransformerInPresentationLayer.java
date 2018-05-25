package com.example.user.Timer.presentation.transformerInPresentationLayer;


import com.example.user.Timer.domainLayer.interactors.model.ModelInDomainLayer;
import com.example.user.Timer.presentation.ModelInPresentationLayer.ModelInPresentationLayer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;

public class TransformerInPresentationLayer {

    @Inject
    public TransformerInPresentationLayer() {
    }

    public io.reactivex.functions.Function<List<ModelInDomainLayer>, List<ModelInPresentationLayer>> domainModelToPresentationModel = new Function<List<ModelInDomainLayer>, List<ModelInPresentationLayer>>() {
        @Override
        public List<ModelInPresentationLayer> apply(List<ModelInDomainLayer> modelInDomainLayers) throws Exception {
            List<ModelInPresentationLayer> noteModelList = new ArrayList<ModelInPresentationLayer>();
            for (ModelInDomainLayer model : modelInDomainLayers) {
                noteModelList.add(
                        new ModelInPresentationLayer(
                                model.getId(),
                                model.getTime(),
                                model.getDate(),
                                model.getGoal()
                        )
                );
            }
            return noteModelList;
        }
    };

    public ModelInPresentationLayer getModelInPresentationLayer(int value, long maxValue) {
        ModelInPresentationLayer user = new ModelInPresentationLayer();
        user.setTime(value);
        user.setGoal(maxValue);
        Calendar cal = Calendar.getInstance();
        user.setDate(String.valueOf(cal.getTimeInMillis()));
        return user;
    }
}
