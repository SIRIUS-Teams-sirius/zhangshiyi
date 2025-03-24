package com.example.federatedlearning.service.impl;

import com.example.federatedlearning.dto.FLResult;
import com.example.federatedlearning.mapper.FLResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FLResultServiceImpl implements com.example.federatedlearning.service.FLResultService {


    @Autowired
    private FLResultMapper flResultMapper;

    @Override
    public Map<String, Float> getAccuracy(String modelId) {
        FLResult result = flResultMapper.selectEvaluationByModelId(modelId);
        Map<String, Float> response = new HashMap<>();
        response.put("accuracy", result != null ? result.getAccuracy() : 0.0f);
        return response;
    }

    @Override
    public Map<String, Float> getPrecision(String modelId) {
        FLResult result = flResultMapper.selectEvaluationByModelId(modelId);
        Map<String, Float> response = new HashMap<>();
        response.put("precision", result != null ? result.getPrecision() : 0.0f);
        return response;
    }

    @Override
    public Map<String, Float> getRecall(String modelId) {
        FLResult result = flResultMapper.selectEvaluationByModelId(modelId);
        Map<String, Float> response = new HashMap<>();
        response.put("recall", result != null ? result.getRecall() : 0.0f);
        return response;
    }

    @Override
    public Map<String, Float> getF1Score(String modelId) {
        FLResult result = flResultMapper.selectEvaluationByModelId(modelId);
        Map<String, Float> response = new HashMap<>();
        response.put("f1_score", result != null ? result.getF1Score() : 0.0f);
        return response;
    }
}