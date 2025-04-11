package com.example.federatedlearning.controller;

import com.example.federatedlearning.dto.TrainLog;
import com.example.federatedlearning.dto.TrainResult;
import com.example.federatedlearning.service.FLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/FL")
public class FLTrainController {

    @Autowired
    private FLService flService;

    // ========== TrainLog 相关操作 ==========

    // 根据 modelId 查询日志
    @GetMapping("/logs/model/{modelId}")
    public List<TrainLog> getLogsByModelId(@PathVariable String modelId) {
        return flService.getLogsByModelId(modelId);
    }

//    // 根据 logLevel 查询日志
//    @GetMapping("/logs/level/{logLevel}")
//    public List<TrainLog> getLogsByLevel(@PathVariable String logLevel) {
//        return flService.getLogsByLevel(logLevel);
//    }
//
//    // 根据条件搜索日志
//    @GetMapping("/logs/search")
//    public List<TrainLog> searchLogs(
//            @RequestParam String modelId,
//            @RequestParam String logLevel,
//            @RequestParam LocalDateTime start,
//            @RequestParam LocalDateTime end) {
//        return flService.searchLogs(modelId, logLevel, start, end);
//    }

    // ========== TrainResult 相关操作 ==========

    // 获取最新的训练结果
    @GetMapping("/results/latest")
    public TrainResult getLatestResult() {
        return flService.getLatestResult();
    }

    // 根据创建者查询训练结果
    @GetMapping("/results/creator/{createdBy}")
    public List<TrainResult> getResultsByCreator(@PathVariable String createdBy) {
        return flService.getResultsByCreator(createdBy);
    }

    // 获取模型的评估指标
    @GetMapping("/results/{resultId}/metrics")
    public Map<String, Float> getModelMetrics(@PathVariable int resultId) {
        return flService.getModelMetrics(resultId);
    }
}
