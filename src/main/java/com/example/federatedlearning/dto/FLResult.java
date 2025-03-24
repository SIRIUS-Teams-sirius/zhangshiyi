package com.example.federatedlearning.dto;

import lombok.Data;

@Data
public class FLResult {
    private int id;
    private float accuracy;
    private float precision;
    private float recall;
    private float f1Score;
}
