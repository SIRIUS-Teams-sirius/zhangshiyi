package com.example.federatedlearning.service;

import java.util.Map;

public interface FLResultService {
    Map<String, Float> getAccuracy(String modelId);
    Map<String, Float> getPrecision(String modelId);
    Map<String, Float> getRecall(String modelId);
    Map<String, Float> getF1Score(String modelId);
}
