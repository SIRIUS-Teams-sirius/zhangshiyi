package com.example.federatedlearning.service.impl;

import com.example.federatedlearning.dto.NetworkData;
import com.example.federatedlearning.mapper.DataStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataStatisticsServiceImpl implements com.example.federatedlearning.service.DataStatisticsService {

    @Autowired
    private DataStatisticsMapper DataStatisticsMapper;

    @Override
    public Map<String, Integer> getLabelStatistics() {
        List<NetworkData> dataList = DataStatisticsMapper.findAll();
        return countByField(dataList, NetworkData::getLabel);
    }

    @Override
    public Map<String, Integer> getConnectionStatusStatistics() {
        List<NetworkData> dataList = DataStatisticsMapper.findAll();
        return countByField(dataList, NetworkData::getConnectionStatus);
    }

    @Override
    public Map<String, Integer> getServiceTypeStatistics() {
        List<NetworkData> dataList = DataStatisticsMapper.findAll();
        return countByField(dataList, NetworkData::getServiceType);
    }

    @Override
    public Map<String, Integer> getProtocolTypeStatistics() {
        List<NetworkData> dataList = DataStatisticsMapper.findAll();
        return countByField(dataList, NetworkData::getProtocolType);
    }

    private Map<String, Integer> countByField(List<NetworkData> dataList, java.util.function.Function<NetworkData, String> fieldExtractor) {
        Map<String, Integer> countMap = new HashMap<>();
        for (NetworkData data : dataList) {
            String fieldValue = fieldExtractor.apply(data);
            countMap.put(fieldValue, countMap.getOrDefault(fieldValue, 0) + 1);
        }
        return countMap;
    }
}
