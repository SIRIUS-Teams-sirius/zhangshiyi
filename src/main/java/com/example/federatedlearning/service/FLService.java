//package com.example.federatedlearning.service;
//
//import java.util.Map;
//
//public interface FLResultService {
//    Map<String, Float> getAccuracy(String modelId);
//    Map<String, Float> getPrecision(String modelId);
//    Map<String, Float> getRecall(String modelId);
//    Map<String, Float> getF1Score(String modelId);
//}
// TrainingService.java
package com.example.federatedlearning.service;

import com.example.federatedlearning.dto.TrainLog;
import com.example.federatedlearning.dto.TrainResult;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface FLService {
    // TrainLog 操作
    TrainLog createTrainingLog(TrainLog log);
    List<TrainLog> getLogsByModelId(String modelId);
    List<TrainLog> getLogsByLevel(String logLevel);
    List<TrainLog> searchLogs(String modelId, String logLevel, LocalDateTime start, LocalDateTime end);
    void updateLogMetadata(String logId, Map<String, Object> metadata);

    // TrainResult 操作
    TrainResult saveTrainingResult(TrainResult result);
    TrainResult getLatestResult();
    List<TrainResult> getResultsByCreator(String createdBy);
    Map<String, Float> getModelMetrics(int resultId);
    void updateModelMetrics(int resultId, float accuracy, float precision, float recall, float f1Score);
}