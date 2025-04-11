package com.example.federatedlearning.Repository;

import com.example.federatedlearning.dto.TrainResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainResultRepository extends MongoRepository<TrainResult, Integer> {

    // 插入训练结果
    TrainResult save(TrainResult result);

    // 查询最新的训练结果
    @Query(value = "{}", sort = "{ 'trainTime': -1 }")
    Optional<TrainResult> findFirstByOrderByTrainTimeDesc();

    // 根据创建者查询训练结果
    List<TrainResult> findByCreatedBy(String createdBy);

    // 根据 resultId 查询训练结果
    Optional<TrainResult> findById(int resultId);

    // 更新训练结果的指标
    @Query("{ '_id': ?0 }")
    @Update("{$set: { 'accuracy': ?1, 'precision': ?2, 'recall': ?3, 'f1Score': ?4, 'trainTime': ?5 }}")
    void updateMetrics(int resultId, float accuracy, float precision, float recall, float f1Score, LocalDateTime trainTime);
}