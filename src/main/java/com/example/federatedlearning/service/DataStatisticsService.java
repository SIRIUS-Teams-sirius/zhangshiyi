package com.example.federatedlearning.service;

import java.util.Map;

public interface DataStatisticsService {
    Map<String, Integer> getLabelStatistics();
    Map<String, Integer> getConnectionStatusStatistics();
    Map<String, Integer> getServiceTypeStatistics();
    Map<String, Integer> getProtocolTypeStatistics();
}
