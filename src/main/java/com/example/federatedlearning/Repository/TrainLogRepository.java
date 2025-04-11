package com.example.federatedlearning.Repository;

import com.example.federatedlearning.dto.TrainLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface TrainLogRepository extends MongoRepository<TrainLog, String> {

    // 插入日志
    TrainLog save(TrainLog log);

    // 根据 modelId 查询日志，按时间倒序排序
    List<TrainLog> findByModelIdOrderByTimestampDesc(String modelId);

    // 根据 logLevel 查询日志，按时间倒序排序
    List<TrainLog> findByLogLevelOrderByTimestampDesc(String logLevel);

    // 根据条件搜索日志
    @Query("{ 'modelId': ?0, 'logLevel': ?1, 'timestamp': { $gte: ?2, $lte: ?3 } }")
    List<TrainLog> searchLogs(String modelId, String logLevel, LocalDateTime start, LocalDateTime end);

    // 更新日志的元数据
    @Query("{ '_id': ?0 }")
    @Update("{$set: { 'metadata': ?1 }}")
    void updateMetadata(String logId, Map<String, Object> metadata);
}