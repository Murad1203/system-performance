package com.itkvant.itkvant.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {
    private String name;
    private List<AnswerRequest> answers;
    private Double award;
}
