package com.example.federatedlearning.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "model_results")
public class TrainResult {
    @Id

    private int id;
    private float accuracy;
    private float precision;
    private float recall;
    private float f1Score;
    private LocalDateTime trainTime;
    private String createdBy;  // Python服务标识

}