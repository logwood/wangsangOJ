package com.yupi.wangsangOJ.model.dto.question;

import lombok.Data;

@Data
public class JudgeConfig {
    private long timeLimit;
    private long memoryLimit;
    private long stackLimit;
}
