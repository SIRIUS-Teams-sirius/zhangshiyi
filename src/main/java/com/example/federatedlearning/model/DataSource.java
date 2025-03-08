package com.example.federatedlearning.model;

import lombok.Data;

@Data
public class DataSource {
    private Long id; // 唯一标识符
    private String url; // 数据集的 URL
    private String description; // 可选描述
}