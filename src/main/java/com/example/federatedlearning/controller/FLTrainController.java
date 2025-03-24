package com.example.federatedlearning.controller;

import com.example.federatedlearning.service.FLResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/FL_result")
public class FLTrainController {

    @Autowired
    private FLResultService flResultService;

    // 获取 accuracy
    @GetMapping("/accuracy/{modelId}")
    public Map<String, Float> getAccuracy(@PathVariable String modelId) {
        return flResultService.getAccuracy(modelId);
    }

    // 获取 precision
    @GetMapping("/precision/{modelId}")
    public Map<String, Float> getPrecision(@PathVariable String modelId) {
        return flResultService.getPrecision(modelId);
    }

    // 获取 recall
    @GetMapping("/recall/{modelId}")
    public Map<String, Float> getRecall(@PathVariable String modelId) {
        return flResultService.getRecall(modelId);
    }

    // 获取 f1_score
    @GetMapping("/f1-score/{modelId}")
    public Map<String, Float> getF1Score(@PathVariable String modelId) {
        return flResultService.getF1Score(modelId);
    }
}