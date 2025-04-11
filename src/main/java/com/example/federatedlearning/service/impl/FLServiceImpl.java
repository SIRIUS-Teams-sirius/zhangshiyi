package com.example.federatedlearning.service.impl;

import com.example.federatedlearning.dto.TrainLog;
import com.example.federatedlearning.dto.TrainResult;
import com.example.federatedlearning.Repository.TrainLogRepository;
import com.example.federatedlearning.Repository.TrainResultRepository;
import com.example.federatedlearning.service.FLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FLServiceImpl implements FLService {

    private final TrainLogRepository trainLogRepository;
    private final TrainResultRepository trainResultRepository;

    @Autowired
    public FLServiceImpl(TrainLogRepository trainLogRepository, TrainResultRepository trainResultRepository) {
        this.trainLogRepository = trainLogRepository;
        this.trainResultRepository = trainResultRepository;
    }

    // ========== TrainLog 相关操作 ==========

    @Override
    public TrainLog createTrainingLog(TrainLog log) {
        log.setTimestamp(LocalDateTime.now());
        return trainLogRepository.save(log);
    }

    @Override
    public List<TrainLog> getLogsByModelId(String modelId) {
        return trainLogRepository.findByModelIdOrderByTimestampDesc(modelId);
    }

    @Override
    public List<TrainLog> getLogsByLevel(String logLevel) {
        return trainLogRepository.findByLogLevelOrderByTimestampDesc(logLevel);
    }

    @Override
    public List<TrainLog> searchLogs(String modelId, String logLevel, LocalDateTime start, LocalDateTime end) {
        return trainLogRepository.searchLogs(modelId, logLevel, start, end);
    }

    @Override
    public void updateLogMetadata(String logId, Map<String, Object> metadata) {
        trainLogRepository.updateMetadata(logId, metadata);
    }

    // ========== TrainResult 相关操作 ==========

    @Override
    public TrainResult saveTrainingResult(TrainResult result) {
        result.setTrainTime(LocalDateTime.now());
        return trainResultRepository.save(result);
    }

    @Override
    public TrainResult getLatestResult() {
        return trainResultRepository.findFirstByOrderByTrainTimeDesc()
                .orElseThrow(() -> new RuntimeException("No result found"));
    }

    @Override
    public List<TrainResult> getResultsByCreator(String createdBy) {
        return trainResultRepository.findByCreatedBy(createdBy);
    }

    @Override
    public Map<String, Float> getModelMetrics(int resultId) {
        TrainResult result = trainResultRepository.findById(resultId)
                .orElseThrow(() -> new RuntimeException("Result not found with id: " + resultId));

        return Map.of(
                "accuracy", result.getAccuracy(),
                "precision", result.getPrecision(),
                "recall", result.getRecall(),
                "f1Score", result.getF1Score()
        );
    }

    @Override
    public void updateModelMetrics(int resultId, float accuracy, float precision,
                                   float recall, float f1Score) {
        trainResultRepository.updateMetrics(resultId, accuracy, precision, recall, f1Score, LocalDateTime.now());
    }
}