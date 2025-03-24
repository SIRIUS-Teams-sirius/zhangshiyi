package com.example.federatedlearning.mapper;

import com.example.federatedlearning.dto.FLResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FLResultMapper {
    @Select("SELECT accuracy, 'precision', recall, f1_score FROM model_evaluation WHERE model_id = #{modelId} LIMIT 1")
    FLResult selectEvaluationByModelId(String modelId);
}