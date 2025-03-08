package com.example.federatedlearning.service;

import com.example.federatedlearning.model.DataSource;

import java.util.List;
import java.util.Map;

public interface DataService {

    List<DataSource> getAllDataSources(); // 获取所有数据源

    DataSource getDataSourceById(Long id); // 根据 ID 获取数据源

    /**
     * 统计连接状态的值
     * @param dataSourceId 数据源 ID
     * @return 连接状态统计结果
     */
    Map<String, Integer> getConnectionStatusStatistics(Long dataSourceId);

    /**
     * 统计服务类型的值
     * @param dataSourceId 数据源 ID
     * @return 服务类型统计结果
     */
    Map<String, Integer> getServiceTypeStatistics(Long dataSourceId);

    /**
     * 统计协议类型的值
     * @param dataSourceId 数据源 ID
     * @return 协议类型统计结果
     */
    Map<String, Integer> getProtocolTypeStatistics(Long dataSourceId);

    /**
     * 统计攻击类型的值
     * @param dataSourceId 数据源 ID
     * @return 协议类型统计结果
     */
    Map<String, Integer> getAttackTypeStatistics(Long dataSourceId);
}