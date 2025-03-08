package com.example.federatedlearning.service.impl;

import com.example.federatedlearning.mapper.DataSourceMapper;
import com.example.federatedlearning.model.DataSource;
import com.example.federatedlearning.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Override
    public List<DataSource> getAllDataSources() {
        return dataSourceMapper.getAllDataSources();
    }

    @Override
    public DataSource getDataSourceById(Long id) {
        return dataSourceMapper.getDataSourceById(id);
    }

    @Override
    public Map<String, Integer> getConnectionStatusStatistics(Long dataSourceId) {
        return fetchStatistics(dataSourceId, "connectionStatus");
    }

    @Override
    public Map<String, Integer> getServiceTypeStatistics(Long dataSourceId) {
        return fetchStatistics(dataSourceId, "serviceType");
    }

    @Override
    public Map<String, Integer> getProtocolTypeStatistics(Long dataSourceId) {
        return fetchStatistics(dataSourceId, "protocolType");
    }

    @Override
    public Map<String, Integer> getAttackTypeStatistics(Long dataSourceId) {
        return fetchStatistics(dataSourceId, "attackType");
    }

    private Map<String, Integer> fetchStatistics(Long dataSourceId, String type) {
        Map<String, Integer> statistics = new HashMap<>();
        try {
            String url = dataSourceMapper.getUrlById(dataSourceId);
            URL dataUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) dataUrl.openConnection();
            conn.setRequestMethod("GET");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    String[] row = line.split(","); // 假设以逗号分隔
                    // 根据不同类型进行统计
                    if ("connectionStatus".equals(type)) {
                        statistics.put(row[0], statistics.getOrDefault(row[0], 0) + 1);
                    } else if ("serviceType".equals(type)) {
                        statistics.put(row[1], statistics.getOrDefault(row[1], 0) + 1);
                    } else if ("protocolType".equals(type)) {
                        statistics.put(row[2], statistics.getOrDefault(row[2], 0) + 1);
                    }else if ("attackType".equals(type)) {
                        statistics.put(row[3], statistics.getOrDefault(row[3], 0) + 1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // 错误处理
        }
        return statistics;
    }
}