package com.example.federatedlearning.controller;

import com.example.federatedlearning.service.DataStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/data_statistics")
public class DataStatisticsController {
    @Autowired
    private DataStatisticsService DataStatisticsService;

    @GetMapping("/label")
    public Map<String, Integer> getLabelStatistics() {
        return DataStatisticsService.getLabelStatistics();
    }

    @GetMapping("/connection-status")
    public Map<String, Integer> getConnectionStatusStatistics() {
        return DataStatisticsService.getConnectionStatusStatistics();
    }

    @GetMapping("/service-type")
    public Map<String, Integer> getServiceTypeStatistics() {
        return DataStatisticsService.getServiceTypeStatistics();
    }

    @GetMapping("/protocol-type")
    public Map<String, Integer> getProtocolTypeStatistics() {
        return DataStatisticsService.getProtocolTypeStatistics();
    }
}
