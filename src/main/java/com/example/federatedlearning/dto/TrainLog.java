package com.example.federatedlearning.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document(collection = "training_logs")

public class TrainLog {
@Id
private String id;

private String modelId;  // 关联的模型ID
private String logLevel;
private String message;
private LocalDateTime timestamp;
private Map<String, Object> metadata;

}
